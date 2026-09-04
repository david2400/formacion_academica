-- Seguimiento de clases: estado (programada / dictada / cancelada) y bitácora de
-- observaciones. Requerido por el calendario de clases del frontend.
--
-- IMPORTANTE: ejecutar sobre el esquema `academia`, que es al que apunta la app
-- (ver spring.datasource.url en application.properties). Correr esto en otro
-- esquema es lo que provoca los errores "Unknown column 'estado'" al listar
-- clases y "Failed to open the referenced table 'clases'" al crear la FK.
USE academia;

-- Sobre Flyway: la dependencia está en el classpath y hay migraciones en
-- src/main/resources/db/migration, pero V3 usa sintaxis de Postgres
-- (TIMESTAMP WITH TIME ZONE, tipo `Long`) que no es válida en MySQL, y no hay
-- configuración de flyway en application.properties. Mientras no se aclare ese
-- estado, este cambio se aplica manualmente en vez de como migración V4.

-- 1) Estado de la clase.
ALTER TABLE clases
    ADD COLUMN estado VARCHAR(20) NOT NULL DEFAULT 'PROGRAMADA' AFTER fecha_fin;

-- 1b) Las clases tienen duración (ej. martes de 17:00 a 18:00), así que las
--     fechas pasan de DATE a DATETIME. La conversión es segura: las filas
--     existentes quedan con hora 00:00:00.
ALTER TABLE clases
    MODIFY COLUMN fecha_inicio DATETIME NOT NULL,
    MODIFY COLUMN fecha_fin    DATETIME NOT NULL;

-- Las clases que ya existían quedan como programadas.
UPDATE clases SET estado = 'PROGRAMADA' WHERE estado IS NULL OR estado = '';

-- 2) Bitácora de observaciones.
--    Las observaciones son acumulativas: cada anotación conserva su fecha y autor
--    y no reemplaza a las anteriores, por eso van en su propia tabla y no como
--    una columna de `clases`.
--    Se crea SIN llave foránea a propósito: en este esquema el FK falla con
--    "Failed to open the referenced table 'clases'" (normalmente por diferencia
--    de motor o de tipo en clases.id). Hibernate no la necesita: la relación se
--    resuelve por @JoinColumn y las entidades usan borrado lógico, así que el
--    ON DELETE CASCADE casi nunca aplicaría. Si más adelante se quiere agregar,
--    ver la nota del final.
CREATE TABLE clase_observaciones (
    id           BIGINT       NOT NULL AUTO_INCREMENT,
    clase_id     BIGINT       NOT NULL,
    observacion  TEXT         NOT NULL,
    eliminado    TINYINT(1)   NOT NULL DEFAULT 0,
    usr_crea     INT          NOT NULL,
    usr_mod      INT          NULL,
    created_at   DATETIME(6)  NULL,
    updated_at   DATETIME(6)  NULL,
    PRIMARY KEY (id),
    KEY idx_clase_observaciones_clase (clase_id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- 3) Catálogo de tipos de clase y su vínculo con `clases`.
CREATE TABLE tipos_clase (
    id           BIGINT       NOT NULL AUTO_INCREMENT,
    nombre       VARCHAR(100) NOT NULL,
    descripcion  TEXT         NULL,
    color        VARCHAR(20)  NULL,
    eliminado    TINYINT(1)   NOT NULL DEFAULT 0,
    usr_crea     INT          NOT NULL,
    usr_mod      INT          NULL,
    created_at   DATETIME(6)  NULL,
    updated_at   DATETIME(6)  NULL,
    PRIMARY KEY (id),
    KEY idx_tipos_clase_nombre (nombre)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

ALTER TABLE clases
    ADD COLUMN tipo_clase_id BIGINT NULL AFTER nombre,
    ADD KEY idx_clases_tipo (tipo_clase_id);

-- Algunos tipos para arrancar. Ajustar o borrar según se necesite.
INSERT INTO tipos_clase (nombre, descripcion, color, usr_crea) VALUES
    ('Magistral',  'Clase teórica dictada por el docente', '#2563eb', 1),
    ('Taller',     'Sesión práctica de trabajo',            '#16a34a', 1),
    ('Refuerzo',   'Sesión de apoyo o nivelación',          '#f59e0b', 1),
    ('Evaluación', 'Sesión destinada a evaluar',            '#dc2626', 1);

-- Nota: para agregar la llave foránea después, primero hay que averiguar por qué
-- falla, con:
--
--   SELECT DATABASE(); SHOW CREATE TABLE clases;
--
-- Lo habitual es que `clases` no sea InnoDB o que `clases.id` no sea BIGINT. Una
-- vez igualados motor y tipo:
--
--   ALTER TABLE clase_observaciones
--     ADD CONSTRAINT fk_clase_observaciones_clase
--     FOREIGN KEY (clase_id) REFERENCES clases (id) ON DELETE CASCADE;

-- Nota: la columna `codigo` de `clases` es NOT NULL UNIQUE pero ningún DTO la
-- enviaba y el adapter no la generaba, por lo que crear una clase fallaba. Ahora
-- el backend genera un código (CLS-XXXXXXXX) cuando no viene informado. Si en la
-- base hay filas con codigo NULL de intentos previos, corregirlas antes de usar
-- el módulo:
--
--   UPDATE clases SET codigo = CONCAT('CLS-', UPPER(SUBSTRING(UUID(), 1, 8)))
--   WHERE codigo IS NULL OR codigo = '';
