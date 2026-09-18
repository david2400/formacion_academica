-- Contraseña propia de Acudiente y Estudiante: permite loguearse con los
-- datos que ya conserva formacion_academica (documento + contraseña), sin
-- depender de un User en access_control.
--
-- IMPORTANTE: ejecutar sobre el esquema `academia` (ver spring.datasource.url
-- en application.properties), igual que las migraciones manuales anteriores.
USE academia;

-- El valor guardado es siempre un hash BCrypt (AcudienteService/EstudianteService
-- lo generan con PasswordEncoder antes de persistir) — nunca texto plano.
-- Nullable: un acudiente/estudiante puede seguir sin contraseña propia.
ALTER TABLE acudientes
    ADD COLUMN password VARCHAR(100) NULL AFTER empresa_id;

ALTER TABLE estudiantes
    ADD COLUMN password VARCHAR(100) NULL AFTER empresa_id;
