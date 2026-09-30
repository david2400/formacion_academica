-- Pais, departamento y ciudad de residencia y de nacimiento para Estudiante
-- y Acudiente, siguiendo el mismo patron que Sede: columnas VARCHAR sueltas
-- que guardan el id (como texto) del catalogo de georeferenciacion que vive
-- en el microservicio `parametros` (TerritoryLookupController) — no hay FK
-- ni tabla propia aqui, formacion_academica solo guarda el id resuelto.
--
-- Estas columnas quedan NULLABLE a nivel de base de datos (los registros
-- existentes no tienen este dato y no se puede backfillear automaticamente
-- desde aqui). El caracter obligatorio pedido para los formularios nuevos
-- se aplica en la capa de aplicacion: CrearEstudianteDto/CrearAcudienteDto
-- (@NotBlank) y la validacion zod en el frontend — igual que en Sede.
--
-- IMPORTANTE: ejecutar sobre el esquema `academia` (ver spring.datasource.url
-- en application.properties). Igual que el resto de cambios de este proyecto,
-- se aplica manualmente: Flyway esta en el classpath pero no configurado
-- (ver database/2026-09-02_clases_estado_observaciones.sql).
USE academia;

ALTER TABLE estudiantes
    ADD COLUMN pais_residencia_id VARCHAR(50) NULL AFTER direccion,
    ADD COLUMN departamento_residencia_id VARCHAR(50) NULL AFTER pais_residencia_id,
    ADD COLUMN ciudad_residencia_id VARCHAR(50) NULL AFTER departamento_residencia_id,
    ADD COLUMN pais_nacimiento_id VARCHAR(50) NULL AFTER ciudad_residencia_id,
    ADD COLUMN departamento_nacimiento_id VARCHAR(50) NULL AFTER pais_nacimiento_id,
    ADD COLUMN ciudad_nacimiento_id VARCHAR(50) NULL AFTER departamento_nacimiento_id;

ALTER TABLE acudientes
    ADD COLUMN pais_residencia_id VARCHAR(50) NULL AFTER correo,
    ADD COLUMN departamento_residencia_id VARCHAR(50) NULL AFTER pais_residencia_id,
    ADD COLUMN ciudad_residencia_id VARCHAR(50) NULL AFTER departamento_residencia_id,
    ADD COLUMN pais_nacimiento_id VARCHAR(50) NULL AFTER ciudad_residencia_id,
    ADD COLUMN departamento_nacimiento_id VARCHAR(50) NULL AFTER pais_nacimiento_id,
    ADD COLUMN ciudad_nacimiento_id VARCHAR(50) NULL AFTER departamento_nacimiento_id;
