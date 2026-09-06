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

## 6. Estados — esquema compartido con access_control

El ciclo de vida lo **define** el motor de máquinas de estados de access_control,
pero este servicio accede **por SQL directo a su esquema**, no por su API.

> Sustituye a dos diseños anteriores: el catálogo local (`catalogo_estados` y
> compañía, tablas ya sin uso) y el cliente HTTP contra `/api/access_control`.

### Por qué SQL y no HTTP

`academia` y `security` están en el **mismo servidor MySQL**, así que la conexión
existente llega a los dos prefijando el nombre del esquema. No hace falta un
segundo `DataSource`.

| | Por HTTP | Por SQL (actual) |
|---|---|---|
| Requiere la app access_control arriba | Sí | **No**, solo MySQL |
| Transaccionalidad | Dos transacciones, podían descuadrar | **Una sola** |
| De qué depende | Contrato del API | Contrato del **esquema** |

El segundo punto no es menor: si falla el `save` local, el cambio de estado se
deshace con él. Por HTTP no había forma.

### El contrato son las vistas, no las tablas

La lectura va contra `security.vw_sm_estado` y `security.vw_sm_transicion`
(migración `V6` en access_control). **No leas las tablas directamente.** Un
`ALTER` sobre las tablas internas no rompe nada mientras las vistas devuelvan lo
mismo; leerlas a pelo convierte cualquier renombrado en un fallo en runtime, sin
aviso y sin que la compilación lo detecte.

Las vistas además aplican `deleted = 0`. Importa: el borrado lógico es
`@SoftDelete` de Hibernate y **solo filtra las consultas de Hibernate**; en SQL
nativo no existe ese filtro.

Las escrituras sí van contra las tablas —`state_machine_instance` y
`state_transition_history`—, porque una vista no sirve para eso.

### Permisos de base de datos

El usuario del datasource necesita, sobre el esquema `security`:

- `SELECT` en `vw_sm_estado` y `vw_sm_transicion`
- `INSERT`, `UPDATE` en `state_machine_instance`
- `INSERT` en `state_transition_history`

Nada más. En particular **no** necesita escribir en las tablas de configuración.

### Máquinas conectadas

| Adaptador | `MAQUINA` | `TIPO_ENTIDAD` |
|---|---|---|
| `EstudianteGrupoJpaAdapter` | `ASIGNACION_GRUPO_LIFECYCLE` | `ESTUDIANTE_GRUPO` |
| `GrupoJpaAdapter` | `GRUPO_LIFECYCLE` | `GRUPO` |
| `InscripcionJpaAdapter` | `INSCRIPCION_LIFECYCLE` | `INSCRIPCION` |
| `MatriculaJpaAdapter` | `MATRICULA_LIFECYCLE` | `MATRICULA` |
| `EstudianteAcudienteJpaAdapter` | `VINCULO_ACUDIENTE_LIFECYCLE` | `ESTUDIANTE_ACUDIENTE` |

Los grafos se cargan con las migraciones `V4`, `V5` y `V6` de access_control.

### El puerto

`MotorEstadosPort` es lo único que ven los módulos de negocio. El día que esto
vuelva a ser HTTP, o el motor se embeba, cambia la implementación y nada más.

| Método | Cuándo |
|---|---|
| `estadoInicial(maquina)` | Al crear, para poblar `estado_id` sin quemar un número. |
| `iniciarCiclo(maquina, tipo, id)` | **Después** de guardar: hace falta el id definitivo. Idempotente. |
| `moverAEstado(maquina, tipo, id, destino, motivo)` | Al cambiar de estado. Devuelve el estado resultante. |

`estado_id` es una **réplica** de `security.state.id_state`, en otra base: **no
hay ni puede haber FK**. Se escribe siempre con lo que devuelve el motor, nunca
con lo que pidió el cliente.

### ⚠️ Esto reimplementa parte del orquestador

`moverAEstado` repite en `MotorEstadosJdbcAdapter` los pasos de
`StateTransitionService.ejecutar` de access_control: instancia, estado no final,
transición válida, motivo obligatorio, bloqueo optimista, historial. **Un cambio
allí hay que replicarlo aquí.** Es el coste asumido de no depender de la app.

La defensa contra la divergencia es que esta implementación **se niega a operar
fuera de lo que sabe validar**:

- si la transición tiene **reglas** configuradas (`transition_rule`), falla: las
  reglas son `@Component` de access_control y aquí no se pueden evaluar;
- si exige **permiso** (`permission_code`), falla: este servicio no tiene usuario
  autenticado que propagar.

En ambos casos el mensaje dice que se ejecute por el API de access_control o se
quite la exigencia. Hoy ninguna máquina de formacion_academica tiene reglas ni
permisos (ver `V5`), así que el subconjunto implementado equivale al del motor.
En cuanto alguien configure una, esto avisa en vez de saltársela.

### Traducción destino → acción

El motor razona en **acciones** (`SUSPENDER`); esta API y draco hablan de
`nuevo_estado_id`. `moverAEstado` traduce: busca entre las transiciones que salen
del estado actual la que lleva a ese destino. Si no hay ninguna, rechaza e indica
los destinos alcanzables. Si hay **dos**, también falla: el destino no identifica
la acción y elegir al azar registraría una acción falsa en el historial.

### Errores

| Causa | HTTP | Significa |
|---|---|---|
| `RECHAZADA` | 422 | El cambio no procede: transición inválida, falta motivo, estado ajeno. |
| `CONCURRENTE` | 409 | Otro cambio ganó la carrera. Releer y reintentar. |
| `CONFIGURACION` | 500 | Falta configurar algo, o hay reglas/permisos que aquí no se pueden evaluar. |
| `NO_DISPONIBLE` | 503 | No se llega al esquema del motor. |

### Cómo enchufar un módulo nuevo

1. Da de alta el tipo de entidad y la máquina en access_control (migración).
2. Añade `estado_id BIGINT` a la entidad. **Sin FK**: apunta a otro esquema.
3. En el adaptador declara `MAQUINA` y `TIPO_ENTIDAD`, inyecta `MotorEstadosPort`:
   - al crear → `estadoInicial(MAQUINA)`, guardar, luego `iniciarCiclo(...)`
   - al cambiar → `moverAEstado(...)` y escribir **lo que devuelve**

### Lo que sigue pendiente

**Sin usuario en el historial.** `user_id` va nulo: este servicio no tiene
contexto de seguridad. El historial dice qué pasó y cuándo, no quién. Es también
el motivo de que los permisos estén desactivados (`V5`): un permiso que no se
puede conceder no protege nada, solo impide que la función exista.

**Filas anteriores a la integración.** Las creadas antes no tienen instancia.
`moverAEstado` las arranca al vuelo en el estado inicial; de su pasado no hay
historia que reconstruir.

**Dos escritores sobre las mismas tablas.** access_control y este servicio pueden
escribir instancias a la vez. Ambos usan la misma columna `version` para bloqueo
optimista, así que interoperan; pero si el orquestador de allí cambia, aquí no se
entera nadie.

---

## 7. `estudiantes-grupo` — activo

Ruta: `/api/kleverkids/estructura-institucion/estudiantes-grupo`
Máquina: `ASIGNACION_GRUPO_LIFECYCLE` · tipo de entidad: `ESTUDIANTE_GRUPO`

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
