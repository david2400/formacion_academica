# formacion_academica — Contexto de arquitectura

Backend Spring Boot (Java) que sirve al frontend Next.js **draco**
(`C:\Users\davi4\Documents\Proyectos\JS\NEXT\kleverkid\apps\draco`).

> Generado el 2026-09-05 analizando el código. Si algo aquí contradice al código, gana el código.

---

## 1. Datos operativos

| Cosa | Valor |
|---|---|
| Puerto | `8081` (`server.port`) |
| Prefijo de API | `/api/kleverkids` — **inyectado en runtime**, no está en los `@RequestMapping` |
| BD | MySQL `academia` en `localhost:3306` |
| DDL | `spring.jpa.hibernate.ddl-auto=update` → **las tablas se crean desde las entidades JPA**, no desde SQL |
| Build | `./mvnw -DskipTests compile` — a 2026-09-05 compila limpio |
| `compile_errors.txt` | **Artefacto obsoleto en la raíz.** No refleja el estado actual. Ignorar. |

### El prefijo `/api/kleverkids`

`config/ApiPrefixConfiguration.java` implementa `WebMvcRegistrations` y antepone
`/api/kleverkids` a todo `@RequestMapping` de paquetes `com.kleverkids` que no lo
tenga ya. Por eso `@RequestMapping("/estructura-institucion/grupos")` se sirve en
`/api/kleverkids/estructura-institucion/grupos`.

---

## 2. ⚠️ El JSON es snake_case, NO camelCase

Este es el punto que más confusión causa entre backend y frontend.

Las clases Java usan camelCase (`estudianteId`, `fechaAsignacion`), pero Jackson
está configurado con `SNAKE_CASE` en **dos** sitios:

- `config/JacksonConfig.java` → `mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)`
- `application.properties` → `spring.jackson.property-naming-strategy=SNAKE_CASE`

**Lo que viaja por HTTP es snake_case:** `estudiante_id`, `fecha_asignacion`,
`primer_apellido`, `capacidad_maxima`.

Además: `accept-case-insensitive-properties=true` y
`fail-on-unknown-properties=false`, así que al deserializar acepta ambos estilos y
descarta campos desconocidos en silencio (ojo: un typo en un campo del request no
da error, simplemente llega `null`).

> **Los `*.json` de OpenAPI que están dentro de draco (`schema_swagger.json`,
> `estructura-institucion.json`, `gestion-alumnos.json`, `admisiones.json`) muestran
> camelCase porque springdoc los genera de los nombres Java, antes de aplicar la
> estrategia de naming. No son fiables para el naming.** Además `schema_swagger.json`
> está desactualizado: declara ids como `uuid` cuando en el código todos son `Long`.
> Los tipos snake_case del frontend son los correctos.

---

## 3. Ids y auditoría

- **Todos los ids son `Long`** (`@GeneratedValue(strategy = IDENTITY)`). No hay UUID
  en las entidades de estos módulos.
- Todas las entidades extienden `shared/common/domain/entity/AuditInfo`, que aporta
  `eliminado`, `usr_crea`, `usr_mod`, `created_at`, `updated_at`.

### Borrado lógico automático — consecuencia importante

`AuditInfo` lleva `@SoftDelete(columnName = "eliminado")` de Hibernate. Esto significa:

1. `deleteById(...)` hace **UPDATE ... SET eliminado = true**, no DELETE.
2. **Hibernate filtra automáticamente las filas borradas en TODA consulta.**
   `findAll()`, `findById()`, los derived queries — ninguno devuelve filas borradas.

> **Implicación para "reactivar" un registro borrado:** no se puede encontrar desde
> los repositorios JPA normales. Hace falta una query nativa (`@Query(nativeQuery = true)`)
> que ignore el filtro, o un método explícito que haga `UPDATE ... SET eliminado = false`.
> Un frontend **no puede** implementar reactivación por su cuenta, porque el registro
> borrado nunca aparece en ningún listado.

`prePersist()`/`prePersistUpdate()` hardcodean `usrCrea = 1` / `usrMod = 1`
(la integración con el usuario autenticado está pendiente).

---

## 4. Estructura de carpetas — arquitectura hexagonal por módulo

```
src/main/java/com/kleverkids/formacion_academica/
├── FormacionAcademicaApplication.java
├── config/          ApiPrefixConfiguration, JacksonConfig, GlobalExceptionHandler,
│                    OpenApiConfig, GraphQLConfig, RequestIdFilter
├── security/        SecurityConfig, SpringSecurityConfig
├── shared/
│   ├── common/domain/{entity,dto,mapper,model}   AuditInfo, AggregateRoot, DomainEvent
│   └── exceptions/  NotFoundException
└── modules/
    ├── admisiones/
    ├── control_academico/
    ├── estados/                 ← estados centralizados (ver §6)
    ├── estructura_institucion/
    └── gestion_alumnos/
```

Cada módulo repite el mismo esqueleto:

```
<modulo>/
├── <Modulo>ModuleConfig.java     @EntityScan + @EnableJpaRepositories + @ComponentScan
├── application/
│   ├── input/<recurso>/          Interfaces de casos de uso (puertos de entrada)
│   │                             CrearXUseCase, ListarXUseCase, ...
│   ├── output/<recurso>/         XRepositoryPort (puerto de salida)
│   └── services/                 XService @Service — implementa TODOS los use cases
│                                 del recurso y delega en el port
├── domain/
│   ├── dto/<recurso>/            CrearXDto, ActualizarXDto (entrada HTTP)
│   └── model/                    X.java — modelo de dominio; ES LO QUE SE SERIALIZA
└── infrastructure/
    ├── inbound/rest/             XController @RestController
    └── outbound/
        ├── adapter/              XJpaAdapter @Component implements XRepositoryPort
        ├── mappers/              XMapper (MapStruct, componentModel = "spring")
        └── persistence/mysql/
            ├── entity/           XEntity @Entity extends AuditInfo
            └── repository/       XJpaRepository extends JpaRepository
```

**Flujo:** `Controller → UseCase (interfaz) → Service → RepositoryPort (interfaz) → JpaAdapter → JpaRepository → Entity`
con `Mapper` traduciendo `Entity ↔ modelo de dominio` y `Dto → Entity`.

**Lo que devuelve la API es el modelo de `domain/model/`, no la entidad ni un DTO.**
Si un campo falta en el modelo de dominio, o el mapper lo ignora, el frontend recibe
`null` aunque la columna exista en BD.

### Convenciones de nombres

- Rutas y carpetas en español: `estructura-institucion`, `estudiantes-grupo`, `grupos`.
- Casos de uso en infinitivo: `Asignar`, `Cambiar`, `Consultar`, `Eliminar`, `Listar`.
- Inconsistencia menor a tener presente: los paquetes mezclan `estudiante_grupo`
  (en `application/input` y `domain/dto`) con `estudiantegrupo` (en `application/output`).

---

## 5. Endpoints activos (a 2026-09-05)

Todos con prefijo `/api/kleverkids`.

| Módulo | Base |
|---|---|
| admisiones | `/admisiones/inscripciones`, `/admisiones/matriculas` |
| control_academico | `/control-academico/{examenes,preguntas,tematicas,clases,asistencias,criterios-evaluados,tipos-clase,asignaciones-examenes}` |
| estados | `/estados` |
| estructura_institucion | `/estructura-institucion/{sedes,salones,aulas,grados,grupos,niveles-educativos}` |
| gestion_alumnos | `/gestion-alumnos/{estudiantes,acudientes,parentescos,relaciones-estudiante-acudiente}` |

`ActividadController` es la excepción: usa `/api/v1/actividades`, fuera de la convención.

### Añadidos el 2026-09-05

| Ruta | Qué hace |
|---|---|
| `/catalogo-estados` | Catálogo de estados y su parametrización por contexto (§6) |
| `/estructura-institucion/estudiantes-grupo` | Reactivado y conectado al catálogo (§7) |

### Forma de las respuestas

- Listados: array JSON plano (`List<X>`), sin envoltorio de paginación.
  Excepción: `/gestion-alumnos/estudiantes/paged` devuelve un `Page` de Spring.
- Errores: `ProblemDetail` (RFC 7807) → `{ type, title, status, detail, ... }`.
  Los de validación añaden `fieldErrors: [{ object, field, rejectedValue, message }]`.
  El campo a leer para el mensaje es **`detail`**.

---

## 6. Catálogo de estados — local, sin dependencias externas

Es el mecanismo con el que **todos** los módulos resuelven sus estados. Vive
entero en esta aplicación: no hay servicios externos de por medio.

### El modelo

```
catalogo_contextos ──< catalogo_estado_contextos >── catalogo_estados
(qué contextos hay)    (parametrización)              (catálogo)
```

| Tabla | Responsabilidad |
|---|---|
| `catalogo_estados` | **Qué estados existen.** Catálogo reutilizable. |
| `catalogo_contextos` | **Qué contextos hay.** Pareja módulo + entidad, con código único. |
| `catalogo_estado_contextos` | **Qué estado aplica a qué contexto**, con `es_inicial`, `es_final`, `orden` e `id_empresa`. |

Un estado existe **una sola vez** y se comparte: `activo` es la misma fila para
matrícula, grupo, inscripción, relación estudiante-acudiente y asignación
estudiante-grupo. Eso es lo que un enum por módulo no permite.

**Por qué `es_inicial` / `es_final` / `orden` viven en la parametrización y no en
el catálogo:** dependen del contexto. `activo` es inicial para una asignación a
grupo, pero puede no serlo en otro contexto.

Un contexto se identifica por la pareja módulo · entidad, aplanada en un `codigo`:

```
formacion_academica.estructura_institucion.estudiante_grupo
formacion_academica.admisiones.matricula
```

El prefijo de aplicación (`estados.aplicacion` en `application.properties`) se
conserva aunque hoy solo haya una: si el catálogo se comparte algún día, los
códigos ya encajan. Un contexto debe registrarse antes de poder parametrizarlo,
así que un nombre mal escrito no crea un contexto fantasma.

### Endpoints

Todos con prefijo `/api/kleverkids`.

| Método | Ruta | Uso |
|---|---|---|
| GET | `/catalogo-estados` | Catálogo completo |
| GET | `/catalogo-estados/codigo/{codigo}` | Consultar por código |
| POST/PUT | `/catalogo-estados`, `/catalogo-estados/{id}` | Administrar catálogo |
| DELETE | `/catalogo-estados/{id}` | Se rechaza si el estado sigue habilitado en algún contexto |
| GET/POST | `/catalogo-estados/contextos` | Listar / registrar contextos |
| **GET** | **`/catalogo-estados/contextos/{codigo}/estados`** | **El que consume el frontend** |
| GET | `/catalogo-estados/contextos/{codigo}/estados/inicial` | Estado inicial |
| POST | `/catalogo-estados/contextos/{codigo}/estados` | Habilitar un estado |
| PUT/DELETE | `/catalogo-estados/contextos/estados/{id}` | Ajustar / quitar |

> La ruta es `/catalogo-estados` y no `/estados` porque el módulo legado de este
> mismo proyecto todavía ocupa `/estados`. Ver más abajo.

### 🔑 Regla para los clientes: `codigo`, nunca `id`

`estado_id` lo genera la base de datos y **cambia entre entornos**. `codigo` es
estable. Cuando un cliente necesita semántica (contar activos, saber si algo es
terminal) debe mirar `codigo` o los flags `es_inicial` / `es_final`, jamás comparar
contra un id escrito en el código. El `estado_id` solo se guarda para devolverlo.

### Arquitectura interna

- **`EstadoContextoLocalAdapter`** implementa el puerto de **lectura**
  (`EstadoContextoRepositoryPort`). Es lo que consumen los demás módulos.
- **`CatalogoEstadosAdminJpaAdapter`** implementa el puerto de **escritura**
  (`CatalogoEstadosAdminPort`). Separados a propósito: un módulo que consume
  estados no puede modificarlos sin querer.

### Cómo enchufar un módulo nuevo

1. Registra el contexto: `POST /catalogo-estados/contextos` con `{modulo, entidad}`.
2. Habilita sus estados: `POST /catalogo-estados/contextos/{codigo}/estados`.
3. Añade `estado_id BIGINT NOT NULL` a la entidad.
4. En el adaptador, declara la constante `CONTEXTO` con el código completo e
   inyecta `ConsultarEstadoContextoUseCase`:
   - al crear → `requerirEstadoInicial(CONTEXTO, idEmpresa)`
   - al cambiar estado → `estaRegistrado(CONTEXTO, nuevoEstadoId, idEmpresa)`

### Contextos ya conectados

| Contexto | Entidad |
|---|---|
| `...estructura_institucion.estudiante_grupo` | `estudiantes_grupo` |
| `...estructura_institucion.grupo` | `grupos` |
| `...admisiones.inscripcion` | `inscripciones` |
| `...admisiones.matricula` | `matriculas` |
| `...gestion_alumnos.estudiante_acudiente` | `estudiante_acudiente` |

### Llaves foráneas

Al vivir el catálogo en esta misma base, `estado_id` **puede tener FK real**. Los
`ALTER TABLE` están comentados al final de `database/seed_catalogo_estados.sql`:
aplícalos cuando los datos sean consistentes.

### ⚠️ El módulo legado `estados` sigue ahí y estorba

Las clases `EstadoEntity`, `EntidadEstadoEntity`, `EstadoHistorialEntity`,
`EstadoTransicionEntity` y sus servicios son del diseño anterior (`id_modulo`
dentro del estado) y **nunca funcionaron**:

- `EntidadEstadoService.obtenerEstadoActual()` devuelve `Optional.empty()` fijo.
- `listarHistorialEstados()` devuelve `List.of()` fijo.
- `cambiarEstadoConValidacion()` lanza `UnsupportedOperationException`.
- Las FKs están mapeadas `insertable = false` sin columna escribible, así que el
  INSERT falla contra columnas `NOT NULL`.

Además ocupa la ruta `/estados`, que es la que debería usar el catálogo nuevo.
**Debería borrarse**; al hacerlo, mover `CatalogoEstadosController` a `/estados`.

### Lo que sigue pendiente

**Historial de cambios de estado**: quién cambió qué, cuándo y por qué. Es el
hueco funcional real. Hay que decidir dónde vive antes de construirlo.

**Transiciones**: hoy cualquier estado puede ir a cualquier otro. Nada impide
pasar de `retirado` a `activo` saltándose el proceso.

---

## 7. `estudiantes-grupo` — activo

Ruta: `/api/kleverkids/estructura-institucion/estudiantes-grupo`
Contexto: `formacion_academica.estructura_institucion.estudiante_grupo`

Estaba entero comentado (entidad, repositorio, adaptador, servicio y controlador)
desde el commit `cfdbee7`. Se reactivó y se reescribió contra el catálogo central.

### Modelo

```
estudiantes_grupo
  id, estudiante_id, grupo_id, fecha_asignacion, estado_id
  + AuditInfo (eliminado, usr_crea, usr_mod, created_at, updated_at)
  UNIQUE (estudiante_id, grupo_id)
```

`estado_id` apunta a `catalogo_estados` de esta misma base (§6). El antiguo
`estado_legacy` (String) desapareció. La entidad **no** declara relaciones a
`EstudianteEntity` ni `GrupoEntity`: los nombres los resuelve el cliente con sus
propios catálogos. Evita acoplar módulos y traer dos entidades por fila.

### Reglas de negocio en `EstudianteGrupoJpaAdapter`

- **El estado no lo manda el cliente al asignar.** Se toma el `es_inicial` del
  contexto. Si no hay ninguno parametrizado, falla diciendo qué configurar.
- **`asignar` es idempotente por `(estudiante_id, grupo_id)`:**
  - si hay asignación activa → la devuelve tal cual;
  - si hay una borrada lógicamente → **la revive**;
  - si no hay ninguna → inserta.

  Es obligatorio: la restricción única sobrevive al borrado lógico, así que un
  INSERT fallaría. La fila borrada solo la ve `buscarIncluyendoEliminados`, una
  query nativa.
- **`cambiarEstado` valida contra el contexto:** si el `estado_id` no está
  habilitado, se rechaza con 400.
- **`eliminar` es borrado lógico**, para que la reactivación sea posible.

### Endpoints

| Método | Ruta | Notas |
|---|---|---|
| POST | `/estudiantes-grupo` | `{estudiante_id, grupo_id, fecha_asignacion?, id_empresa?}` |
| GET | `/estudiantes-grupo` | Todas |
| GET | `/estudiantes-grupo/grupo/{grupoId}` | Por grupo |
| GET | `/estudiantes-grupo/{id}` | Una |
| PUT | `/estudiantes-grupo/estado` | `{asignacion_id, nuevo_estado_id, id_empresa?}` |
| DELETE | `/estudiantes-grupo/{id}` | Borrado lógico |

**No hay endpoint de transferencia.** El frontend la compone con DELETE + POST,
apoyándose en la reactivación.

---

## 8. Puesta en marcha

```bash
./mvnw spring-boot:run
```

Crea todas las tablas por `ddl-auto=update`. Después, **una sola vez**, siembra el
catálogo:

```bash
mysql -u root -p academia < database/seed_catalogo_estados.sql
```

Es idempotente y no usa ids fijos (resuelve por `codigo`). Sin este paso, crear
una matrícula o una asignación responde que el contexto no tiene estado inicial
parametrizado.

---

## 9. Contrato consumido por draco

El frontend (`apps/draco/server/domains/formacion-academica/`) espeja este backend:
un directorio por recurso con `types.ts` / `repository.ts` / `queries.ts` / `actions.ts`.

Modelos verificados campo a campo (serialización snake_case):

- **`Estudiante`** → `id, tipo_documento, numero_documento, nombres, primer_apellido,
  segundo_apellido, fecha_nacimiento, genero, correo, telefono, direccion, eliminado, ...`
- **`Grupo`** → `id, codigo, nombre, grado_id, capacidad_maxima, periodo_academico,
  fecha_inicio, fecha_fin, tutor_id, salon_id, eliminado, ...`
- **`EstudianteGrupo`** → `id, estudiante_id, grupo_id, fecha_asignacion, estado_id, ...`

El dominio `server/domains/formacion-academica/estados/` del frontend apunta a
`/api/kleverkids/catalogo-estados` de este servicio. La pantalla de asignación construye
filtros, badges, métricas y selector a partir de esa lista: no hay ni un id de
estado escrito en el código del frontend.
