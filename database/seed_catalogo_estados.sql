-- ============================================================================
--  ⚠️ OBSOLETO — NO EJECUTAR
-- ============================================================================
--
--  Este seed poblaba el catálogo local de estados (catalogo_estados,
--  catalogo_contextos, catalogo_estado_contextos), que ya no se usa: el ciclo
--  de vida lo gobierna el motor de máquinas de estados de access_control.
--
--  Los grafos equivalentes se cargan con las migraciones V4 y V5 de
--  access_control. Ver §6 de CLAUDE.md.
--
--  Se conserva solo como referencia de qué estados existían antes. Las tablas
--  que crea están vacías en la base y ningún código las consulta.
-- ============================================================================

-- ============================================================================
--  Catálogo de estados + contextos + parametrización
--  Base de datos: academia   (formacion_academica)
-- ============================================================================
--
--  CUÁNDO EJECUTARLO
--  Después de levantar la aplicación al menos una vez: con
--  spring.jpa.hibernate.ddl-auto=update, Hibernate crea las tres tablas a partir
--  de las entidades.
--
--      mysql -u root -p academia < database/seed_catalogo_estados.sql
--
--  ES IDEMPOTENTE: se puede volver a ejecutar sin duplicar nada.
--
--  EL MODELO
--      catalogo_contextos ──< catalogo_estado_contextos >── catalogo_estados
--      (qué contextos hay)    (parametrización)              (catálogo)
--
--  Un estado existe UNA vez y se comparte. Fíjate en que `activo` termina
--  habilitado en cinco contextos apuntando a la misma fila: eso es justamente lo
--  que un enum por módulo no permite.
--
--  POR QUÉ NO HAY IDs FIJOS
--  Los ids los asigna la base de datos y cambian entre entornos. Este script usa
--  `codigo` como clave natural. La misma regla aplica al frontend: cuando necesite
--  semántica debe mirar `codigo`, nunca el id.
--
--  PARA AJUSTARLO SIN SQL
--      POST   /api/kleverkids/estados
--      POST   /api/kleverkids/estados/contextos
--      POST   /api/kleverkids/estados/contextos/{codigo}/estados
--      PUT    /api/kleverkids/estados/contextos/estados/{id}
--      DELETE /api/kleverkids/estados/contextos/estados/{id}
-- ============================================================================


-- ─── 1. Catálogo de estados ─────────────────────────────────────────────────

INSERT INTO catalogo_estados (codigo, nombre, descripcion, color, icono, orden)
SELECT 'retirado', 'Retirado', 'Salió antes de terminar', '#dc2626', 'user-minus', 10
WHERE NOT EXISTS (SELECT 1 FROM catalogo_estados e WHERE e.codigo = 'retirado');

INSERT INTO catalogo_estados (codigo, nombre, descripcion, color, icono, orden)
SELECT 'finalizo', 'Finalizó', 'Completó el periodo satisfactoriamente', '#16a34a', 'check-circle', 20
WHERE NOT EXISTS (SELECT 1 FROM catalogo_estados e WHERE e.codigo = 'finalizo');

INSERT INTO catalogo_estados (codigo, nombre, descripcion, color, icono, orden)
SELECT 'activo', 'Activo', 'Vinculado y en curso', '#2563eb', 'play-circle', 30
WHERE NOT EXISTS (SELECT 1 FROM catalogo_estados e WHERE e.codigo = 'activo');

INSERT INTO catalogo_estados (codigo, nombre, descripcion, color, icono, orden)
SELECT 'finalizo_perdiendo_anio', 'Finalizó perdiendo el año', 'Completó el periodo sin aprobarlo', '#f59e0b', 'alert-triangle', 40
WHERE NOT EXISTS (SELECT 1 FROM catalogo_estados e WHERE e.codigo = 'finalizo_perdiendo_anio');

INSERT INTO catalogo_estados (codigo, nombre, descripcion, color, icono, orden)
SELECT 'inactivo', 'Inactivo', 'Vinculado pero sin actividad', '#6b7280', 'pause-circle', 50
WHERE NOT EXISTS (SELECT 1 FROM catalogo_estados e WHERE e.codigo = 'inactivo');

INSERT INTO catalogo_estados (codigo, nombre, descripcion, color, icono, orden)
SELECT 'pendiente', 'Pendiente', 'A la espera de revisión', '#f59e0b', 'clock', 60
WHERE NOT EXISTS (SELECT 1 FROM catalogo_estados e WHERE e.codigo = 'pendiente');

INSERT INTO catalogo_estados (codigo, nombre, descripcion, color, icono, orden)
SELECT 'aprobada', 'Aprobada', 'Revisada y aceptada', '#16a34a', 'check-circle', 70
WHERE NOT EXISTS (SELECT 1 FROM catalogo_estados e WHERE e.codigo = 'aprobada');

INSERT INTO catalogo_estados (codigo, nombre, descripcion, color, icono, orden)
SELECT 'rechazada', 'Rechazada', 'Revisada y no aceptada', '#dc2626', 'x-circle', 80
WHERE NOT EXISTS (SELECT 1 FROM catalogo_estados e WHERE e.codigo = 'rechazada');


-- ─── 2. Registro de contextos ───────────────────────────────────────────────
--  El código es <aplicacion>.<modulo>.<entidad> y debe coincidir con la constante
--  CONTEXTO de cada adaptador.

INSERT INTO catalogo_contextos (modulo, entidad, codigo, nombre, descripcion)
SELECT 'estructura_institucion', 'estudiante_grupo',
       'formacion_academica.estructura_institucion.estudiante_grupo',
       'Asignación estudiante-grupo', 'Estado de la vinculación de un estudiante a un grupo'
WHERE NOT EXISTS (SELECT 1 FROM catalogo_contextos c WHERE c.codigo = 'formacion_academica.estructura_institucion.estudiante_grupo');

INSERT INTO catalogo_contextos (modulo, entidad, codigo, nombre, descripcion)
SELECT 'estructura_institucion', 'grupo',
       'formacion_academica.estructura_institucion.grupo',
       'Grupo', 'Estado del grupo académico'
WHERE NOT EXISTS (SELECT 1 FROM catalogo_contextos c WHERE c.codigo = 'formacion_academica.estructura_institucion.grupo');

INSERT INTO catalogo_contextos (modulo, entidad, codigo, nombre, descripcion)
SELECT 'admisiones', 'inscripcion',
       'formacion_academica.admisiones.inscripcion',
       'Inscripción', 'Estado de la solicitud de inscripción'
WHERE NOT EXISTS (SELECT 1 FROM catalogo_contextos c WHERE c.codigo = 'formacion_academica.admisiones.inscripcion');

INSERT INTO catalogo_contextos (modulo, entidad, codigo, nombre, descripcion)
SELECT 'admisiones', 'matricula',
       'formacion_academica.admisiones.matricula',
       'Matrícula', 'Estado de la matrícula del estudiante'
WHERE NOT EXISTS (SELECT 1 FROM catalogo_contextos c WHERE c.codigo = 'formacion_academica.admisiones.matricula');

INSERT INTO catalogo_contextos (modulo, entidad, codigo, nombre, descripcion)
SELECT 'gestion_alumnos', 'estudiante_acudiente',
       'formacion_academica.gestion_alumnos.estudiante_acudiente',
       'Relación estudiante-acudiente', 'Estado del vínculo entre estudiante y acudiente'
WHERE NOT EXISTS (SELECT 1 FROM catalogo_contextos c WHERE c.codigo = 'formacion_academica.gestion_alumnos.estudiante_acudiente');


-- ─── 3. Parametrización ─────────────────────────────────────────────────────
--  Un procedimiento auxiliar evita repetir el mismo INSERT veinte veces.

DROP PROCEDURE IF EXISTS habilitar_estado;
DELIMITER //
CREATE PROCEDURE habilitar_estado(
    IN p_contexto VARCHAR(200),
    IN p_estado   VARCHAR(60),
    IN p_inicial  TINYINT(1),
    IN p_final    TINYINT(1),
    IN p_orden    INT)
BEGIN
    INSERT INTO catalogo_estado_contextos (contexto_id, estado_id, es_inicial, es_final, orden, id_empresa)
    SELECT c.id_contexto, e.id_estado, p_inicial, p_final, p_orden, 0
    FROM catalogo_contextos c
    JOIN catalogo_estados e ON e.codigo = p_estado
    WHERE c.codigo = p_contexto
      AND NOT EXISTS (
          SELECT 1 FROM catalogo_estado_contextos x
          WHERE x.contexto_id = c.id_contexto AND x.estado_id = e.id_estado AND x.id_empresa = 0);
END //
DELIMITER ;

-- Asignación estudiante-grupo: activo (inicial) -> finalizó / perdió / retirado
CALL habilitar_estado('formacion_academica.estructura_institucion.estudiante_grupo', 'activo',                  1, 0, 1);
CALL habilitar_estado('formacion_academica.estructura_institucion.estudiante_grupo', 'finalizo',                0, 1, 2);
CALL habilitar_estado('formacion_academica.estructura_institucion.estudiante_grupo', 'finalizo_perdiendo_anio', 0, 1, 3);
CALL habilitar_estado('formacion_academica.estructura_institucion.estudiante_grupo', 'retirado',                0, 1, 4);

-- Grupo: activo (inicial) -> inactivo / finalizó
CALL habilitar_estado('formacion_academica.estructura_institucion.grupo', 'activo',   1, 0, 1);
CALL habilitar_estado('formacion_academica.estructura_institucion.grupo', 'inactivo', 0, 0, 2);
CALL habilitar_estado('formacion_academica.estructura_institucion.grupo', 'finalizo', 0, 1, 3);

-- Inscripción: pendiente (inicial) -> aprobada / rechazada
CALL habilitar_estado('formacion_academica.admisiones.inscripcion', 'pendiente', 1, 0, 1);
CALL habilitar_estado('formacion_academica.admisiones.inscripcion', 'aprobada',  0, 1, 2);
CALL habilitar_estado('formacion_academica.admisiones.inscripcion', 'rechazada', 0, 1, 3);

-- Matrícula: activo (inicial) -> inactivo -> finalizó / retirado
CALL habilitar_estado('formacion_academica.admisiones.matricula', 'activo',   1, 0, 1);
CALL habilitar_estado('formacion_academica.admisiones.matricula', 'inactivo', 0, 0, 2);
CALL habilitar_estado('formacion_academica.admisiones.matricula', 'finalizo', 0, 1, 3);
CALL habilitar_estado('formacion_academica.admisiones.matricula', 'retirado', 0, 1, 4);

-- Relación estudiante-acudiente: activo (inicial) -> inactivo
CALL habilitar_estado('formacion_academica.gestion_alumnos.estudiante_acudiente', 'activo',   1, 0, 1);
CALL habilitar_estado('formacion_academica.gestion_alumnos.estudiante_acudiente', 'inactivo', 0, 1, 2);

DROP PROCEDURE IF EXISTS habilitar_estado;


-- ─── 4. Verificación ────────────────────────────────────────────────────────
--  `activo` debe salir con el MISMO id_estado en cinco contextos distintos.
--
--  SELECT c.modulo, c.entidad, e.id_estado, e.codigo, p.es_inicial, p.es_final, p.orden
--  FROM catalogo_estado_contextos p
--  JOIN catalogo_contextos c ON c.id_contexto = p.contexto_id
--  JOIN catalogo_estados   e ON e.id_estado   = p.estado_id
--  ORDER BY c.modulo, c.entidad, p.orden;


-- ─── 5. Opcional: llaves foráneas ───────────────────────────────────────────
--  Ahora que el catálogo vive en esta misma base, `estado_id` puede tener FK real.
--  Aplícalas cuando los datos existentes sean consistentes; si alguna fila apunta
--  a un estado inexistente, el ALTER falla (y eso es justamente lo que quieres saber).
--
--  ALTER TABLE estudiantes_grupo   ADD CONSTRAINT fk_estudiante_grupo_estado   FOREIGN KEY (estado_id) REFERENCES catalogo_estados (id_estado);
--  ALTER TABLE grupos              ADD CONSTRAINT fk_grupo_estado              FOREIGN KEY (estado_id) REFERENCES catalogo_estados (id_estado);
--  ALTER TABLE inscripciones       ADD CONSTRAINT fk_inscripcion_estado        FOREIGN KEY (estado_id) REFERENCES catalogo_estados (id_estado);
--  ALTER TABLE matriculas          ADD CONSTRAINT fk_matricula_estado          FOREIGN KEY (estado_id) REFERENCES catalogo_estados (id_estado);
--  ALTER TABLE estudiante_acudiente ADD CONSTRAINT fk_estudiante_acudiente_estado FOREIGN KEY (estado_id) REFERENCES catalogo_estados (id_estado);
