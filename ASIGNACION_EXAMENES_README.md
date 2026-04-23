# 📚 Módulo de Asignación de Exámenes

## 📋 Descripción

Este módulo permite asignar exámenes previamente configurados a clases/grupos específicos con fechas y configuraciones particulares. Complementa el módulo de exámenes existente, separando la **configuración del examen** de su **asignación a estudiantes**.

## 🏗️ Arquitectura

El módulo sigue la **Arquitectura Hexagonal** (Ports & Adapters) utilizada en el proyecto:

```
asignacion_examen/
├── domain/
│   ├── dto/                           # DTOs de transferencia
│   │   ├── AsignacionExamenDto.java
│   │   ├── CrearAsignacionExamenDto.java
│   │   ├── ActualizarAsignacionExamenDto.java
│   │   └── BuscarAsignacionesDto.java
│   └── exception/                     # Excepciones de dominio
│       ├── AsignacionExamenNotFoundException.java
│       ├── AsignacionDuplicadaException.java
│       └── FechasInvalidasException.java
│
├── application/
│   ├── input/                         # Use Cases (puertos de entrada)
│   │   ├── CrearAsignacionExamenUseCase.java
│   │   ├── ActualizarAsignacionExamenUseCase.java
│   │   ├── ConsultarAsignacionExamenUseCase.java
│   │   ├── BuscarAsignacionesExamenUseCase.java
│   │   ├── EliminarAsignacionExamenUseCase.java
│   │   └── CambiarEstadoAsignacionUseCase.java
│   │
│   ├── output/                        # Puertos de salida
│   │   └── AsignacionExamenRepositoryPort.java
│   │
│   └── services/                      # Servicios de aplicación
│       ├── AsignacionExamenService.java
│       ├── AsignacionExamenMapper.java
│       └── AsignacionExamenSpecifications.java
│
└── infrastructure/
    ├── inbound/
    │   └── rest/
    │       └── AsignacionExamenController.java
    │
    └── outbound/
        ├── persistence/
        │   └── mysql/
        │       ├── entity/
        │       │   └── AsignacionExamenEntity.java
        │       └── repository/
        │           └── AsignacionExamenRepository.java
        └── adapter/
            └── AsignacionExamenJpaAdapter.java
```

## 🎯 Funcionalidades

### Endpoints Disponibles

#### 1. **Crear Asignación**
```http
POST /control-academico/asignaciones-examenes
Content-Type: application/json

{
  "examenId": 1,
  "claseId": 5,
  "fechaAsignacion": "2024-05-01",
  "fechaInicio": "2024-05-10T08:00:00",
  "fechaFin": "2024-05-10T10:00:00",
  "duracionMinutos": 120,
  "intentosPermitidos": 2,
  "mostrarResultadosInmediatos": false,
  "permitirRevision": true
}
```

#### 2. **Actualizar Asignación**
```http
PUT /control-academico/asignaciones-examenes/{id}
Content-Type: application/json

{
  "fechaInicio": "2024-05-11T08:00:00",
  "fechaFin": "2024-05-11T10:00:00",
  "intentosPermitidos": 3,
  "estado": "ACTIVA"
}
```

#### 3. **Consultar Asignación**
```http
GET /control-academico/asignaciones-examenes/{id}
```

#### 4. **Listar Asignaciones (con filtros)**
```http
GET /control-academico/asignaciones-examenes?examenId=1&estado=ACTIVA&page=0&size=20
```

**Filtros disponibles:**
- `examenId` - ID del examen
- `claseId` - ID de la clase
- `grado` - Grado escolar
- `grupo` - Grupo/sección
- `estado` - Estado (PROGRAMADA, ACTIVA, FINALIZADA, CANCELADA)
- `fechaDesde` - Fecha desde
- `fechaHasta` - Fecha hasta
- `activas` - Solo asignaciones activas (default: true)
- `page` - Número de página (default: 0)
- `size` - Tamaño de página (default: 20)
- `sort` - Ordenamiento (ej: fechaInicio,asc)

#### 5. **Eliminar Asignación**
```http
DELETE /control-academico/asignaciones-examenes/{id}
```

#### 6. **Cambiar Estado**
```http
PATCH /control-academico/asignaciones-examenes/{id}/estado?estado=ACTIVA
```

## 📊 Modelo de Datos

### Estados de Asignación

- **PROGRAMADA**: Asignación creada pero aún no iniciada
- **ACTIVA**: Asignación en curso (entre fechaInicio y fechaFin)
- **FINALIZADA**: Asignación completada
- **CANCELADA**: Asignación cancelada

### Campos Principales

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `id` | Long | Identificador único |
| `examenId` | Long | ID del examen configurado |
| `claseId` | Long | ID de la clase/grupo |
| `grado` | String | Grado escolar |
| `grupo` | String | Grupo/sección |
| `fechaAsignacion` | LocalDate | Fecha de asignación |
| `fechaInicio` | LocalDateTime | Inicio de disponibilidad |
| `fechaFin` | LocalDateTime | Fin de disponibilidad |
| `duracionMinutos` | Integer | Duración máxima del examen |
| `intentosPermitidos` | Integer | Número de intentos permitidos |
| `mostrarResultadosInmediatos` | Boolean | Mostrar resultados al finalizar |
| `permitirRevision` | Boolean | Permitir revisar respuestas |
| `estado` | String | Estado actual |

## 🔒 Validaciones

1. **Fechas**: `fechaFin` debe ser posterior a `fechaInicio`
2. **Duplicados**: No se permite asignar el mismo examen a la misma clase si ya existe una asignación PROGRAMADA o ACTIVA
3. **Intentos**: Debe ser al menos 1
4. **Estados**: Solo valores válidos (PROGRAMADA, ACTIVA, FINALIZADA, CANCELADA)

## 🚀 Uso

### Ejemplo Completo

```java
// 1. Crear asignación
CrearAsignacionExamenDto dto = new CrearAsignacionExamenDto(
    1L,                                    // examenId
    5L,                                    // claseId
    LocalDate.of(2024, 5, 1),             // fechaAsignacion
    LocalDateTime.of(2024, 5, 10, 8, 0),  // fechaInicio
    LocalDateTime.of(2024, 5, 10, 10, 0), // fechaFin
    120,                                   // duracionMinutos
    2,                                     // intentosPermitidos
    false,                                 // mostrarResultadosInmediatos
    true                                   // permitirRevision
);

AsignacionExamenDto resultado = crearAsignacionExamenUseCase.crear(dto);

// 2. Buscar asignaciones activas
BuscarAsignacionesDto criterios = new BuscarAsignacionesDto(
    null,    // examenId
    null,    // claseId
    "5to",   // grado
    null,    // grupo
    "ACTIVA",// estado
    null,    // fechaDesde
    null,    // fechaHasta
    true     // activas
);

Page<AsignacionExamenDto> asignaciones = buscarAsignacionesExamenUseCase.buscar(
    criterios, 
    PageRequest.of(0, 20)
);

// 3. Cambiar estado
AsignacionExamenDto actualizada = cambiarEstadoAsignacionUseCase.cambiarEstado(
    1L, 
    "FINALIZADA"
);
```

## 📝 Notas Importantes

1. **Separación de Responsabilidades**: 
   - El módulo de **Exámenes** maneja la configuración (preguntas, criterios, etc.)
   - El módulo de **Asignaciones** maneja cuándo y a quién se aplica el examen

2. **Integración Pendiente**:
   - Obtener información de grado/grupo desde el servicio de clases
   - Obtener nombres de examen y clase para los DTOs de respuesta

3. **Índices de Base de Datos**: 
   - Optimizados para búsquedas por examen, clase, estado y fechas
   - Constraint único para evitar duplicados

4. **Paginación**: 
   - Todos los listados soportan paginación
   - Ordenamiento configurable

## 🔄 Próximos Pasos

- [ ] Integrar con servicio de clases para obtener grado/grupo
- [ ] Integrar con servicio de exámenes para validar existencia
- [ ] Agregar notificaciones cuando una asignación se activa
- [ ] Implementar job programado para cambiar estados automáticamente
- [ ] Agregar estadísticas de asignaciones por clase/examen

## 📚 Documentación API

La documentación completa de la API está disponible en Swagger UI:
```
http://localhost:8081/swagger-ui/index.html
```

Buscar el tag: **"Asignaciones de Exámenes"**
