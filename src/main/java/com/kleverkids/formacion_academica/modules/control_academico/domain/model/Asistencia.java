package com.kleverkids.formacion_academica.modules.control_academico.domain.model;

import java.time.LocalDateTime;

public record Asistencia(Long id,
                         Long claseId,
                         Long estudianteId,
                         LocalDateTime fechaRegistro,
                         boolean presente,
                         boolean activo,
                         Integer usrCrea,
                         Integer usrMod,
                         LocalDateTime createdAt,
                         LocalDateTime updatedAt) {
}
