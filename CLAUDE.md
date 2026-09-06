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
| `/estados/contextos` | Parametriza qué estados aplican a cada tipo de entidad (§6) |
| `/estructura-institucion/estudiantes-grupo` | Reactivado y conectado al catálogo central (§7) |

### Forma de las respuestas

- Listados: array JSON plano (`List<X>`), sin envoltorio de paginación.
  Excepción: `/gestion-alumnos/estudiantes/paged` devuelve un `Page` de Spring.
- Errores: `ProblemDetail` (RFC 7807) → `{ type, title, status, detail, ... }`.
  Los de validación añaden `fieldErrors: [{ object, field, rejectedValue, message }]`.
  El campo a leer para el mensaje es **`detail`**.

---

## 6. Estados centralizados — viven en access_control

> **El catálogo de estados NO está en este proyecto.** Se administra en el servicio
> `access_control` (`C:\Users\davi4\Documents\Proyectos\JAVA\access_control`,
> puerto 8000, BD `security`). Aquí solo se consume.

### El modelo, en una línea

```
Applications ──< estado_contextos ──< estado_contexto_estados >── estados
(dueño)          (qué contexto)       (parametrización)           (catálogo)
```

Un contexto no es un texto libre: es una fila registrada cuyo dueño es una
aplicación, identificada por la terna **aplicación · módulo · entidad**. Por eso
siempre se sabe de quién es cada estado, y un nombre mal escrito no crea un
contexto fantasma — lo rechaza la llave foránea.

El contexto de este proyecto para la asignación a grupos es:

```
formacion_academica.estructura_institucion.estudiante_grupo
```

Está declarado en `EstudianteGrupoJpaAdapter.CONTEXTO`.

### Cómo se consume

`EstadoContextoHttpAdapter` implementa `EstadoContextoRepositoryPort` llamando a
`GET /api/access_control/estados/contextos/{codigo}/estados`.

Configuración en `application.properties`:

```
access-control.base-url=http://localhost:8000
access-control.catalogo-ttl-segundos=300
```

**Caché con degradación.** El catálogo se cachea en memoria por TTL. Si
access_control no responde y hay copia previa —aunque esté vencida— se sirve esa:
es preferible un catálogo de hace unos minutos a una pantalla caída. Solo cuando
no hay ninguna copia se devuelve lista vacía, y entonces `requerirEstadoInicial`
falla con un mensaje que dice exactamente qué configurar.

### Consecuencia de tener dos bases de datos

`estudiantes_grupo.estado_id` vive en `academia` y el catálogo en `security`:
**no hay llave foránea real posible**. La integridad se sostiene validando contra
el puerto antes de persistir (`estaRegistrado`), no en la base de datos.

### Cómo enchufar otro módulo al catálogo

1. Registra el contexto en access_control:
   `POST /api/access_control/estados/contextos` con `{application_id, modulo, entidad}`.
2. Habilita sus estados: `POST /api/access_control/estados/contextos/{codigo}/estados`.
3. En la entidad, añade `estado_id BIGINT NOT NULL`.
4. En el adaptador, declara la constante `CONTEXTO` con la terna completa e inyecta
   `ConsultarEstadoContextoUseCase`:
   - al crear → `requerirEstadoInicial(CONTEXTO, idEmpresa)`
   - al cambiar estado → `estaRegistrado(CONTEXTO, nuevoEstadoId, idEmpresa)`

### Qué quedó como remanente en este proyecto

El módulo local `modules/estados` (`estados`, `entidad_estados`,
`estado_historial`, `estado_transiciones`) sigue existiendo pero **ya no es la
fuente de verdad del catálogo**. Su subsistema de historial nunca funcionó:

- `EntidadEstadoService.obtenerEstadoActual()` devuelve `Optional.empty()` fijo.
- `listarHistorialEstados()` devuelve `List.of()` fijo.
- `cambiarEstadoConValidacion()` lanza `UnsupportedOperationException`.
- En `EntidadEstadoEntity`, `EstadoHistorialEntity` y `EstadoTransicionEntity` las
  FKs están mapeadas `insertable = false, updatable = false` sin columna `Long`
  escribible, así que el INSERT falla contra columnas `NOT NULL`.

Decidir si se borra o se reimplementa como historial contra el catálogo central es
trabajo pendiente.

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

`estado_id` apunta al catálogo de access_control (sin FK, ver §6). El antiguo
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

El orden importa: sin el catálogo, este servicio no puede asignar estudiantes.

**1. access_control** (crea y siembra el catálogo):

```bash
mysql -u root -p security < ../access_control/database/migrations/V1__estados_contextos.sql
```

```bash
cd ../access_control && ./mvnw spring-boot:run
```

**2. Este servicio:**

```bash
./mvnw spring-boot:run
```

Crea `estudiantes_grupo` por `ddl-auto=update`. Si access_control no está arriba,
`POST /estudiantes-grupo` responde que el contexto no tiene estado inicial
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

El dominio `server/domains/formacion-academica/estados/` del frontend apunta
**directamente a access_control** (`env.accessControlBaseUrl`, por defecto
`http://localhost:8000`), no a este servicio. La pantalla de asignación construye
filtros, badges, métricas y selector a partir de esa lista: no hay ni un id de
estado escrito en el código del frontend.
