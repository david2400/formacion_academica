# File Tree: formacion_academica

**Generated:** 4/3/2026, 1:56:10 PM
**Root Path:** `c:\Users\davi4\Documents\Proyectos\JAVA\formacion_academica`

```
├── 📁 .mvn
│   └── 📁 wrapper
│       └── 📄 maven-wrapper.properties
├── 📁 database
│   └── 📁 migrations
│       └── 📁 actividades
├── 📁 docs
│   └── 📝 arquitectura_estados_centralizados.md
├── 📁 ejemplos
├── 📁 src
│   ├── 📁 main
│   │   ├── 📁 java
│   │   │   └── 📁 com
│   │   │       └── 📁 kleverkids
│   │   │           └── 📁 formacion_academica
│   │   │               ├── 📁 config
│   │   │               │   ├── ☕ ApiPrefixConfiguration.java
│   │   │               │   ├── ☕ GlobalExceptionHandler.java
│   │   │               │   ├── ☕ GraphQLConfig.java
│   │   │               │   ├── ☕ JacksonConfig.java
│   │   │               │   ├── ☕ OpenApiConfig.java
│   │   │               │   ├── ☕ RequestIdFilter.java
│   │   │               │   └── ☕ SwaggerUiIndexHtmlRedirectFilter.java
│   │   │               ├── 📁 modules
│   │   │               │   ├── 📁 admisiones
│   │   │               │   │   ├── 📁 application
│   │   │               │   │   │   ├── 📁 input
│   │   │               │   │   │   ├── 📁 output
│   │   │               │   │   │   └── 📁 services
│   │   │               │   │   ├── 📁 domain
│   │   │               │   │   │   ├── 📁 dto
│   │   │               │   │   │   └── 📁 model
│   │   │               │   │   ├── 📁 infrastructure
│   │   │               │   │   │   ├── 📁 inbound
│   │   │               │   │   │   └── 📁 outbound
│   │   │               │   │   └── ☕ AdmisionesModuleConfig.java
│   │   │               │   ├── 📁 control_academico
│   │   │               │   │   ├── 📁 application
│   │   │               │   │   │   ├── 📁 input
│   │   │               │   │   │   ├── 📁 output
│   │   │               │   │   │   └── 📁 services
│   │   │               │   │   ├── 📁 domain
│   │   │               │   │   │   ├── 📁 dto
│   │   │               │   │   │   ├── 📁 events
│   │   │               │   │   │   ├── 📁 exception
│   │   │               │   │   │   ├── 📁 model
│   │   │               │   │   │   └── 📁 valueobject
│   │   │               │   │   ├── 📁 infrastructure
│   │   │               │   │   │   ├── 📁 inbound
│   │   │               │   │   │   └── 📁 outbound
│   │   │               │   │   └── ☕ ControlAcademicoModuleConfig.java
│   │   │               │   ├── 📁 estados
│   │   │               │   │   ├── 📁 application
│   │   │               │   │   │   ├── 📁 input
│   │   │               │   │   │   ├── 📁 output
│   │   │               │   │   │   └── 📁 services
│   │   │               │   │   ├── 📁 domain
│   │   │               │   │   │   ├── 📁 dto
│   │   │               │   │   │   └── 📁 model
│   │   │               │   │   ├── 📁 infrastructure
│   │   │               │   │   │   ├── 📁 inbound
│   │   │               │   │   │   └── 📁 outbound
│   │   │               │   │   └── ☕ EstadosModuleConfig.java
│   │   │               │   ├── 📁 estructura_institucion
│   │   │               │   │   ├── 📁 application
│   │   │               │   │   │   ├── 📁 input
│   │   │               │   │   │   ├── 📁 output
│   │   │               │   │   │   └── 📁 services
│   │   │               │   │   ├── 📁 domain
│   │   │               │   │   │   ├── 📁 dto
│   │   │               │   │   │   └── 📁 model
│   │   │               │   │   ├── 📁 infrastructure
│   │   │               │   │   │   ├── 📁 config
│   │   │               │   │   │   ├── 📁 inbound
│   │   │               │   │   │   └── 📁 outbound
│   │   │               │   │   └── ☕ EstructuraInstitucionModuleConfig.java
│   │   │               │   └── 📁 gestion_alumnos
│   │   │               │       ├── 📁 application
│   │   │               │       │   ├── 📁 input
│   │   │               │       │   ├── 📁 output
│   │   │               │       │   └── 📁 services
│   │   │               │       ├── 📁 domain
│   │   │               │       │   ├── 📁 dto
│   │   │               │       │   └── 📁 model
│   │   │               │       ├── 📁 infrastructure
│   │   │               │       │   ├── 📁 inbound
│   │   │               │       │   └── 📁 outbound
│   │   │               │       └── ☕ GestionAlumnosModuleConfig.java
│   │   │               ├── 📁 security
│   │   │               │   ├── ☕ SecurityConfig.java
│   │   │               │   └── ☕ SpringSecurityConfig.java
│   │   │               ├── 📁 shared
│   │   │               │   ├── 📁 common
│   │   │               │   │   └── 📁 domain
│   │   │               │   │       ├── 📁 dto
│   │   │               │   │       ├── 📁 entity
│   │   │               │   │       ├── 📁 mapper
│   │   │               │   │       ├── 📁 model
│   │   │               │   │       ├── ☕ AggregateRoot.java
│   │   │               │   │       ├── ☕ DomainEvent.java
│   │   │               │   │       └── ☕ ValueObject.java
│   │   │               │   └── 📁 exceptions
│   │   │               │       └── ☕ NotFoundException.java
│   │   │               └── ☕ FormacionAcademicaApplication.java
│   │   └── 📁 resources
│   │       ├── 📁 db
│   │       │   └── 📁 migration
│   │       │       ├── 📄 V2__create_questions_tables.sql
│   │       │       └── 📄 V3__create_exams_tables.sql
│   │       ├── 📄 application.properties
│   │       └── 📄 schema.sql
│   └── 📁 test
│       └── 📁 java
│           └── 📁 com
│               └── 📁 kleverkids
│                   └── 📁 formacion_academica
│                       ├── 📁 modules
│                       │   ├── 📁 control_academico
│                       │   │   └── 📁 exams
│                       │   │       └── 📁 domain
│                       │   ├── 📁 estructura_institucion
│                       │   │   ├── 📁 application
│                       │   │   │   └── 📁 services
│                       │   │   └── 📁 infrastructure
│                       │   │       └── 📁 outbound
│                       │   └── 📁 questions
│                       │       └── 📁 application
│                       │           └── 📁 service
│                       └── ☕ FormacionAcademicaApplicationTests.java
├── ⚙️ .gitattributes
├── ⚙️ .gitignore
├── 📕 MJ KIDS 420PM T. Gabriela - Mateo - Exámen Diagnóstico C1S4.pdf
├── 📄 mvnw
├── 📄 mvnw.cmd
└── ⚙️ pom.xml
```

---
*Generated by FileTree Pro Extension*