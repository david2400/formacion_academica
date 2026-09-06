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
| `/estados` | Catálogo de estados y su parametrización por contexto (§6) |
| `/estructura-institucion/estudiantes-grupo` | Reactivado y conectado al catálogo (§7) |

### Forma de las respuestas

- Listados: array JSON plano (`List<X>`), sin envoltorio de paginación.
  Excepción: `/gestion-alumnos/estudiantes/paged` devuelve un `Page` de Spring.
- Errores: `ProblemDetail` (RFC 7807) → `{ type, title, status, detail, ... }`.
  Los de validación añaden `fieldErrors: [{ object, field, rejectedValue, message }]`.
  El campo a leer para el mensaje es **`detail`**.

---

## 6. Estados — motor externo en access_control

El ciclo de vida de las entidades lo gobierna el **motor de máquinas de estados**
de access_control. Aquí no hay catálogo: el módulo `modules/estados` es solo un
cliente HTTP.

> Esto sustituye al catálogo local (`catalogo_estados`, `catalogo_contextos`,
> `catalogo_estado_contextos`) que vivía en esta aplicación. Aquellas tablas ya no
> se usan.

### Cómo está repartido

| Dónde | Qué guarda |
|---|---|
| access_control, base `security` | La definición (máquinas, estados, transiciones, reglas), las instancias vivas y **el historial completo** de cambios. |
| Esta aplicación, base `academia` | La columna `estado_id` de cada tabla de negocio, como **réplica** de lo que decide el motor. |

La réplica existe para poder listar y filtrar sin salir a la red. **Cuando ambos
discrepan, manda el motor.**

`estado_id` apunta a `security.state.id_state`, que está en otra base de datos:
**no hay ni puede haber llave foránea**. La integridad la sostiene el motor, no
el motor de base de datos.

### ⚠️ Dependencia en runtime

Crear o cambiar el estado de una inscripción, matrícula, grupo, asignación a
grupo o vínculo con acudiente **requiere que access_control responda**. Si está
caído, esas escrituras fallan.

Es deliberado: con una única fuente de verdad, seguir adelante sin registrar el
cambio dejaría las dos bases divergiendo en silencio, que es peor que una caída
visible. Las **lecturas** no dependen del motor, porque usan la réplica local.

Las definiciones se cachean 10 minutos (`motor-estados.cache-ttl`), así que el
caso más frecuente —consultar el estado inicial al crear— casi nunca sale a la
red. Una máquina publicada es inmutable, por eso se puede cachear.

### Máquinas conectadas

| Adaptador | `MAQUINA` | `TIPO_ENTIDAD` |
|---|---|---|
| `EstudianteGrupoJpaAdapter` | `ASIGNACION_GRUPO_LIFECYCLE` | `ESTUDIANTE_GRUPO` |
| `GrupoJpaAdapter` | `GRUPO_LIFECYCLE` | `GRUPO` |
| `InscripcionJpaAdapter` | `INSCRIPCION_LIFECYCLE` | `INSCRIPCION` |
| `MatriculaJpaAdapter` | `MATRICULA_LIFECYCLE` | `MATRICULA` |
| `EstudianteAcudienteJpaAdapter` | `VINCULO_ACUDIENTE_LIFECYCLE` | `ESTUDIANTE_ACUDIENTE` |

Los grafos se cargan con las migraciones `V4` y `V5` de access_control.

### El puerto

`MotorEstadosPort` es la única interfaz que ven los módulos de negocio:

| Método | Cuándo |
|---|---|
| `estadoInicial(maquina)` | Al crear, para poblar `estado_id` sin quemar un número. |
| `perteneceALaMaquina(maquina, estadoId)` | Validación barata contra el grafo. |
| `iniciarCiclo(maquina, tipo, id)` | **Después** de guardar: el motor necesita el id definitivo. Idempotente. |
| `moverAEstado(maquina, tipo, id, destino, motivo)` | Al cambiar de estado. Devuelve el estado resultante. |

### Cómo enchufar un módulo nuevo

1. Da de alta el tipo de entidad y la máquina en access_control (migración o
   `POST /state-machines`).
2. Añade `estado_id BIGINT` a la entidad. **Sin FK**: apunta a otra base.
3. En el adaptador declara `MAQUINA` y `TIPO_ENTIDAD` e inyecta
   `MotorEstadosPort`:
   - al crear → `estadoInicial(MAQUINA)`, guardar, y luego
     `iniciarCiclo(MAQUINA, TIPO_ENTIDAD, guardado.getId())`
   - al cambiar → `moverAEstado(...)` y escribir en `estado_id` **lo que devuelve
     el motor**, no lo que pidió el cliente

### Traducción destino → acción

El motor razona en **acciones** (`SUSPENDER`), no en estados destino. La API de
esta aplicación y el frontend hablan de `nuevo_estado_id`. `moverAEstado` traduce:
consulta las acciones disponibles y busca la que lleva a ese estado.

Si no hay ninguna, la operación se rechaza con los destinos que sí son
alcanzables. Si hay **dos**, también falla: el destino no identifica la acción, y
elegir al azar registraría una acción falsa en el historial. Ninguna máquina
actual tiene ese caso.

### Errores

`MotorEstadosException` distingue tres causas, y `GlobalExceptionHandler` las
mapea:

| Causa | HTTP | Significa |
|---|---|---|
| `RECHAZADA` | 422 | El cambio no procede: transición inválida, falta motivo. |
| `CONFIGURACION` | 500 | Falta configurar algo en el motor. Fallo nuestro. |
| `NO_DISPONIBLE` | 503 | El motor no responde. Reintentar tiene sentido. |

### Lo que sigue pendiente

**Permisos desactivados.** Las transiciones se configuraron con
`permission_code` (`GRUPO.SUSPENDER`, …), pero la migración `V5` los pone a NULL.
Motivo: esos permisos no existen en el catálogo de access_control, esta
aplicación **no tiene contexto de seguridad** y por tanto no puede propagar un
usuario, y el motor resuelve el usuario leyendo la cabecera `X-User-Id`, algo que
su propio controlador marca como inseguro. Con las tres cosas, exigir permiso no
protegía nada: solo hacía que **toda** transición se rechazara.

Para reactivarlos, en este orden: propagar el usuario autenticado → sustituir
`X-User-Id` por el token en el motor → dar de alta los permisos → repoblar
`permission_code` (el UPDATE está en `V5`).

**Sin usuario en el historial.** Por lo mismo, el motor registra los cambios con
usuario nulo. El historial dice qué pasó y cuándo, pero no quién.

**Filas anteriores a la integración.** Las creadas antes de esto no tienen
instancia en el motor. `moverAEstado` las arranca al vuelo en el estado inicial;
de su pasado no hay historia que reconstruir.

**Sin transacción distribuida.** Si el motor acepta la transición y después falla
el `save` local, el motor queda por delante de la réplica. La lectura siguiente
lo corrige, pero conviene tenerlo presente.

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
`/api/kleverkids/estados` de este servicio. La pantalla de asignación construye
filtros, badges, métricas y selector a partir de esa lista: no hay ni un id de
estado escrito en el código del frontend.
