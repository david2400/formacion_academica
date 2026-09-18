-- Vincula Acudiente y Estudiante con la identidad centralizada de access_control
-- (User, que ya trae embebido su Client) y su empresa (tenant), sin romper los
-- registros existentes.
--
-- IMPORTANTE: ejecutar sobre el esquema `academia` (ver spring.datasource.url
-- en application.properties). Igual que el resto de cambios de este proyecto,
-- se aplica manualmente: Flyway está en el classpath pero no configurado
-- (ver database/2026-09-02_clases_estado_observaciones.sql).
USE academia;

-- Ambas columnas nullable a propósito: los registros existentes no tienen
-- todavía un User asociado en access_control, y el DTO de creación permite
-- usuarioId opcional mientras el frontend no lo envíe siempre.
ALTER TABLE acudientes
    ADD COLUMN usuario_id BIGINT NULL AFTER es_principal,
    ADD COLUMN empresa_id BIGINT NULL AFTER usuario_id;

ALTER TABLE estudiantes
    ADD COLUMN usuario_id BIGINT NULL AFTER direccion,
    ADD COLUMN empresa_id BIGINT NULL AFTER usuario_id;

-- Sin FK física a access_control.User: viven en esquemas/servidores
-- potencialmente distintos según el entorno (igual que el motor de estados),
-- así que la integridad referencial la garantiza CuentaUsuarioJdbcAdapter al
-- validar contra las vistas vw_account_usuario / vw_account_usuario_empresa en
-- creación/actualización, no una constraint de base de datos.

-- Índices para las validaciones y filtros por empresa/usuario.
CREATE INDEX idx_acudientes_usuario_id ON acudientes (usuario_id);
CREATE INDEX idx_acudientes_empresa_id ON acudientes (empresa_id);
CREATE INDEX idx_estudiantes_usuario_id ON estudiantes (usuario_id);
CREATE INDEX idx_estudiantes_empresa_id ON estudiantes (empresa_id);
