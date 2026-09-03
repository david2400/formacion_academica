-- Seguimiento de clases: estado (dictada / programada / cancelada) y observaciones.
-- Requerido por el calendario de clases del frontend.
--
-- El proyecto no usa Flyway/Liquibase (spring.jpa.hibernate.ddl-auto está comentado),
-- así que este script se ejecuta manualmente.

ALTER TABLE clases
    ADD COLUMN estado VARCHAR(20) NOT NULL DEFAULT 'PROGRAMADA' AFTER fecha_fin,
    ADD COLUMN observaciones TEXT NULL AFTER estado;

-- Las clases que ya existían quedan como programadas.
UPDATE clases SET estado = 'PROGRAMADA' WHERE estado IS NULL OR estado = '';

-- Nota: la columna `codigo` es NOT NULL UNIQUE pero ningún DTO la enviaba y el
-- adapter no la generaba, por lo que crear una clase fallaba. Ahora el backend
-- genera un código (CLS-XXXXXXXX) cuando no viene informado. Si en la base hay
-- filas con codigo NULL de intentos previos, corregirlas antes de usar el módulo:
--
--   UPDATE clases SET codigo = CONCAT('CLS-', UPPER(SUBSTRING(UUID(), 1, 8)))
--   WHERE codigo IS NULL OR codigo = '';
