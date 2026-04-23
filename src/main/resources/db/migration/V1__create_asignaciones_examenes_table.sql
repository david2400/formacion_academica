-- Tabla para asignaciones de exámenes a clases/grupos
CREATE TABLE IF NOT EXISTS asignaciones_examenes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    examen_id BIGINT NOT NULL,
    clase_id BIGINT NOT NULL,
    grado VARCHAR(50),
    grupo VARCHAR(50),
    fecha_asignacion DATE NOT NULL,
    fecha_inicio DATETIME NOT NULL,
    fecha_fin DATETIME NOT NULL,
    duracion_minutos INT,
    intentos_permitidos INT NOT NULL DEFAULT 1,
    mostrar_resultados_inmediatos BOOLEAN NOT NULL DEFAULT FALSE,
    permitir_revision BOOLEAN NOT NULL DEFAULT TRUE,
    estado VARCHAR(20) NOT NULL DEFAULT 'PROGRAMADA',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    -- Índices para mejorar el rendimiento
    INDEX idx_examen_id (examen_id),
    INDEX idx_clase_id (clase_id),
    INDEX idx_estado (estado),
    INDEX idx_fecha_inicio (fecha_inicio),
    INDEX idx_examen_clase (examen_id, clase_id),
    
    -- Constraint para evitar asignaciones duplicadas activas
    UNIQUE KEY uk_examen_clase_activa (examen_id, clase_id, estado),
    
    -- Constraints de validación
    CONSTRAINT chk_estado CHECK (estado IN ('PROGRAMADA', 'ACTIVA', 'FINALIZADA', 'CANCELADA')),
    CONSTRAINT chk_intentos CHECK (intentos_permitidos > 0),
    CONSTRAINT chk_fechas CHECK (fecha_fin > fecha_inicio)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Comentarios para documentación
ALTER TABLE asignaciones_examenes 
    COMMENT = 'Tabla que gestiona la asignación de exámenes configurados a clases/grupos específicos con fechas y configuraciones';
