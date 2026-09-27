-- Tabla de asignación de preguntas a un examen (examenes_preguntas).
--
-- IMPORTANTE: ejecutar sobre el esquema `academia` (ver spring.datasource.url
-- en application.properties), igual que las migraciones manuales anteriores.
-- No hay Flyway en este proyecto y ddl-auto está deshabilitado, así que esta
-- tabla debe crearse a mano -igual que examenes_criterios/examenes_tematicas,
-- que tampoco tienen script versionado.
--
-- Espeja la convención de ExamenCriterioEntity/AuditInfo: eliminado, usr_crea,
-- usr_mod, created_at, updated_at son obligatorias porque ExamenPreguntaEntity
-- extiende AuditInfo (shared/common/domain/entity/AuditInfo.java).
--
-- pregunta_id referencia la tabla `questions` (PreguntaEntity, el único banco
-- de preguntas realmente conectado a un endpoint REST -/control-academico/
-- preguntas-, NO `preguntas_banco`, que no tiene controller).
USE academia;

CREATE TABLE IF NOT EXISTS examenes_preguntas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    examen_id BIGINT NOT NULL,
    pregunta_id BIGINT NOT NULL,
    orden INT NOT NULL,
    puntos DECIMAL(10,2) NULL,
    eliminado BOOLEAN NOT NULL DEFAULT FALSE,
    usr_crea INT NOT NULL,
    usr_mod INT NULL,
    created_at DATETIME NULL,
    updated_at DATETIME NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Opcional: llaves foráneas, una vez que confirmes que examenes/questions
-- tienen datos consistentes (ver §5 de seed_catalogo_estados.sql para el
-- mismo razonamiento aplicado a otra tabla de este proyecto).
--
-- ALTER TABLE examenes_preguntas ADD CONSTRAINT fk_examen_pregunta_examen   FOREIGN KEY (examen_id)   REFERENCES examenes (id);
-- ALTER TABLE examenes_preguntas ADD CONSTRAINT fk_examen_pregunta_pregunta FOREIGN KEY (pregunta_id) REFERENCES questions (id);
