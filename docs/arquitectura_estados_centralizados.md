# Arquitectura Centralizada de Estados - Guía Completa

## 🏗️ Visión General

Sistema escalable y reutilizable para gestión de estados centralizado que permite manejar múltiples módulos desde una única estructura de base de datos.

## 📋 Estructura de Base de Datos

### Tablas Principales

1. **estados** - Tabla central de estados
2. **estado_transiciones** - Definiciones de workflow/transiciones
3. **entidad_estados** - Relación polimórfica entre entidades y estados
4. **estado_historial** - Historial de cambios de estado

### Relaciones

```
estados (1) -----> (N) estado_transiciones
estados (1) -----> (N) entidad_estados
entidad_estados (1) -----> (N) estado_historial
```

## 🔥 Consultas SQL Esenciales

### 1. Obtener estados por módulo

```sql
-- Estados activos de un módulo específico
SELECT e.id, e.codigo, e.nombre, e.color, e.icono, e.orden
FROM estados e
WHERE e.id_modulo = 1 
  AND e.eliminado = TRUE
ORDER BY e.orden ASC;
```

```sql
-- Estados por módulo y empresa (multi-tenancy)
SELECT e.id, e.codigo, e.nombre, e.es_inicial, e.es_final
FROM estados e
WHERE e.id_modulo = 1 
  AND e.id_empresa = 100
  AND e.eliminado = TRUE
ORDER BY e.orden ASC;
```

### 2. Validar transiciones de estados

```sql
-- Verificar si una transición es válida
SELECT COUNT(*) > 0 as es_valida
FROM estado_transiciones t
WHERE t.id_estado_origen = 1
  AND t.id_estado_destino = 2
  AND t.id_modulo = 1
  AND t.activa = TRUE;
```

```sql
-- Obtener transiciones posibles desde un estado
SELECT 
    t.id,
    e_destino.codigo as codigo_destino,
    e_destino.nombre as nombre_destino,
    t.condicion,
    t.accion,
    t.automatica
FROM estado_transiciones t
INNER JOIN estados e_destino ON t.id_estado_destino = e_destino.id
WHERE t.id_estado_origen = 1
  AND t.id_modulo = 1
  AND t.activa = TRUE
ORDER BY t.orden ASC;
```

### 3. Obtener estado actual de una entidad

```sql
-- Estado actual de una entidad específica
SELECT 
    ee.id,
    ee.entidad_tipo,
    ee.entidad_id,
    e.codigo as estado_codigo,
    e.nombre as estado_nombre,
    e.color as estado_color,
    ee.motivo,
    ee.creado_en as fecha_cambio
FROM entidad_estados ee
INNER JOIN estados e ON ee.id_estado = e.id
WHERE ee.entidad_tipo = 'estudiante'
  AND ee.entidad_id = 123
  AND ee.eliminado = TRUE;
```

### 4. Asignar estado a una entidad

```sql
-- Desactivar estado anterior
UPDATE entidad_estados 
SET eliminado = FALSE, actualizado_en = NOW()
WHERE entidad_tipo = 'estudiante' 
  AND entidad_id = 123 
  AND eliminado = TRUE;

-- Insertar nuevo estado
INSERT INTO entidad_estados (
    uuid, entidad_tipo, entidad_id, id_estado, id_estado_anterior, 
    id_usuario_cambio, motivo, eliminado, creado_en
) VALUES (
    UUID(), 'estudiante', 123, 2, 1, 1000, 'Cambio de estado manual', TRUE, NOW()
);
```

### 5. Historial de cambios de estado

```sql
-- Historial completo de una entidad
SELECT 
    h.id,
    h.creado_en as fecha_cambio,
    e_anterior.codigo as estado_anterior,
    e_anterior.nombre as nombre_anterior,
    e_nuevo.codigo as estado_nuevo,
    e_nuevo.nombre as nombre_nuevo,
    h.motivo,
    h.id_usuario_cambio
FROM estado_historial h
LEFT JOIN estados e_anterior ON h.id_estado_anterior = e_anterior.id
INNER JOIN estados e_nuevo ON h.id_estado_nuevo = e_nuevo.id
WHERE h.entidad_tipo = 'estudiante'
  AND h.entidad_id = 123
ORDER BY h.creado_en DESC;
```

### 6. Estadísticas y reportes

```sql
-- Contar entidades por estado
SELECT 
    e.codigo as estado_codigo,
    e.nombre as estado_nombre,
    COUNT(ee.id) as cantidad_entidades,
    ROUND(COUNT(ee.id) * 100.0 / (SELECT COUNT(*) FROM entidad_estados WHERE eliminado = TRUE), 2) as porcentaje
FROM estados e
LEFT JOIN entidad_estados ee ON e.id = ee.id_estado AND ee.eliminado = TRUE
WHERE e.id_modulo = 1
  AND e.eliminado = TRUE
GROUP BY e.id, e.codigo, e.nombre
ORDER BY e.orden ASC;
```

```sql
-- Workflow completo de un módulo
SELECT 
    e.codigo,
    e.nombre,
    e.es_inicial,
    e.es_final,
    e.orden,
    GROUP_CONCAT(e_destino.codigo) as transiciones_hacia
FROM estados e
LEFT JOIN estado_transiciones t ON e.id = t.id_estado_origen AND t.activa = TRUE
LEFT JOIN estados e_destino ON t.id_estado_destino = e_destino.id
WHERE e.id_modulo = 1
  AND e.eliminado = TRUE
GROUP BY e.id, e.codigo, e.nombre, e.es_inicial, e.es_final, e.orden
ORDER BY e.orden ASC;
```

## 🎯 Ejemplos de Uso en Java

### Asignar estado inicial a un estudiante

```java
// Asignar estado inicial "ACTIVO" a un nuevo estudiante
EntidadEstadoEntity estadoAsignado = estadoService.asignarEstadoInicial(
    "estudiante",      // entidadTipo
    123L,             // entidadId
    1L,               // idModulo (ESTUDIANTES)
    1000L             // idUsuarioCambio
);
```

### Cambiar estado con validación

```java
// Cambiar estado de "ACTIVO" a "SUSPENDIDO"
CambioEstadoRequest request = new CambioEstadoRequest(
    "estudiante",      // entidadTipo
    123L,             // entidadId
    3L,               // nuevoEstadoId (SUSPENDIDO)
    1L,               // idModulo
    1000L,            // idUsuarioCambio
    "Por falta de pago" // motivo
);

EntidadEstadoEntity nuevoEstado = estadoService.cambiarEstadoConValidacion(request);
```

### Consultar estado actual

```java
// Obtener estado actual de un estudiante
Optional<EntidadEstadoEntity> estadoActual = estadoService.obtenerEstadoActual(
    "estudiante", 123L
);

if (estadoActual.isPresent()) {
    EstadoEntity estado = estadoActual.get().getEstado();
    System.out.println("Estado actual: " + estado.getNombre());
}
```

### Listar workflow completo

```java
// Obtener todos los estados del módulo de estudiantes con sus transiciones
List<EstadoEntity> workflow = estadoService.obtenerWorkflowCompleto(1L);
workflow.forEach(estado -> {
    List<EstadoTransicionEntity> transiciones = 
        estadoService.listarTransicionesDesdeEstado(estado.getId(), 1L);
    System.out.println(estado.getNombre() + " -> " + 
        transiciones.stream()
            .map(t -> t.getEstadoDestino().getNombre())
            .collect(Collectors.joining(", ")));
});
```

## 🏆 Mejores Prácticas

### 1. Diseño de Estados

- **Códigos estandarizados**: Usar convenciones como `ACTIVO`, `INACTIVO`, `CANCELADO`
- **Colores consistentes**: Definir paleta de colores por tipo de estado
- **Orden lógico**: Establecer orden secuencial para workflow
- **Descripciones claras**: Explicar el significado de cada estado

### 2. Gestión de Transiciones

- **Validación estricta**: Siempre validar transiciones antes de ejecutar
- **Condiciones de negocio**: Definir reglas claras para cada transición
- **Acciones automáticas**: Implementar acciones automáticas cuando sea posible
- **Rollback**: Tener capacidad de revertir cambios si es necesario

### 3. Performance

- **Índices optimizados**: Crear índices en campos de consulta frecuente
- **Vistas materializadas**: Para reportes complejos
- **Caching**: Cachear estados y transiciones en memoria
- **Consultas paginadas**: Para listados grandes

### 4. Seguridad

- **Validación de permisos**: Verificar que el usuario puede cambiar estados
- **Auditoría completa**: Registrar quién, cuándo y por qué cambió un estado
- **Validación de negocio**: No permitir cambios que violen reglas de negocio

### 5. Escalabilidad

- **Multi-tenancy**: Soportar múltiples empresas con estados personalizados
- **Modularidad**: Fácil agregar nuevos módulos sin modificar estructura existente
- **Extensibilidad**: Permitir metadata personalizada en estados y transiciones

## 📊 Métricas y Monitoreo

### KPIs Recomendados

1. **Tiempo promedio en cada estado**
2. **Frecuencia de cambios de estado**
3. **Estados más comunes por módulo**
4. **Tasa de transiciones exitosas vs fallidas**
5. **Tiempo de respuesta en cambios de estado**

### Alertas Sugeridas

- Cambios de estado masivos inesperados
- Transiciones fallidas repetitivas
- Entidades atascadas en estados no finales
- Cambios fuera de horario laboral

## 🔄 Integración con Otros Módulos

### 1. Eventos de Cambio de Estado

```java
// Publicar evento cuando cambia un estado
@EventListener
public void handleEstadoCambiado(EstadoCambiadoEvent event) {
    // Enviar notificaciones
    // Actualizar cachés
    // Sincronizar con otros sistemas
    // Ejecutar acciones de negocio
}
```

### 2. Validaciones Cruzadas

```java
// Validar que un estado sea consistente con otros módulos
public boolean validarEstadoConsistente(String entidadTipo, Long entidadId, Integer estadoId) {
    // Validar con reglas de otros módulos
    // Verificar dependencias
    // Confirmar integridad de datos
}
```

## 🚀 Consideraciones de Producción

### 1. Base de Datos

- **Connection pooling**: Configurar pool adecuado para alta concurrencia
- **Replicación**: Considerar replicación para lecturas de estados
- **Backup**: Backup regular de tablas de historial

### 2. Aplicación

- **Transacciones**: Usar transacciones ACID para cambios de estado
- **Retry**: Implementar retry para cambios fallidos
- **Deadlock detection**: Manejar deadlocks en cambios concurrentes

### 3. Monitoreo

- **Health checks**: Verificar disponibilidad del servicio de estados
- **Metrics**: Exponer métricas de uso y performance
- **Logging**: Logging estructurado para troubleshooting

## 📈 Escalabilidad Futura

### 1. Microservicios

- **Servicio de estados dedicado**: Extraer como microservicio independiente
- **API Gateway**: Centralizar acceso a través de gateway
- **Event sourcing**: Considerar event sourcing para historial

### 2. Caching Avanzado

- **Redis**: Caché distribuido para estados y transiciones
- **CDN**: Para recursos estáticos de UI (iconos, colores)
- **Edge caching**: Caché en edge para consultas frecuentes

### 3. Analytics

- **Data warehouse**: Exportar datos a warehouse para análisis
- **Real-time analytics**: Procesamiento en tiempo real de cambios
- **ML models**: Predecir transiciones y detectar anomalías

---

Esta arquitectura proporciona una base sólida y escalable para gestionar estados en sistemas complejos, manteniendo la flexibilidad para crecer y adaptarse a nuevos requisitos.
