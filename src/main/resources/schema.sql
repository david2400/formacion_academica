CREATE TABLE IF NOT EXISTS estudiantes (
    id CHAR(36) PRIMARY KEY,
    tipo_documento VARCHAR(20) NOT NULL,
    numero_documento VARCHAR(30) NOT NULL UNIQUE,
    nombres VARCHAR(120) NOT NULL,
    apellidos VARCHAR(120) NOT NULL,
    fecha_nacimiento DATE,
    genero VARCHAR(20),
    correo VARCHAR(120),
    telefono VARCHAR(40),
    direccion VARCHAR(200),
    activo BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS acudientes (
    id CHAR(36) PRIMARY KEY,
    estudiante_id CHAR(36) NOT NULL,
    tipo_documento VARCHAR(20) NOT NULL,
    numero_documento VARCHAR(30) NOT NULL,
    nombres VARCHAR(120) NOT NULL,
    apellidos VARCHAR(120) NOT NULL,
    parentesco VARCHAR(60) NOT NULL,
    telefono VARCHAR(40),
    correo VARCHAR(120),
    es_principal BOOLEAN NOT NULL,
    activo BOOLEAN NOT NULL,
    CONSTRAINT fk_acudiente_estudiante FOREIGN KEY (estudiante_id) REFERENCES estudiantes(id)
);
