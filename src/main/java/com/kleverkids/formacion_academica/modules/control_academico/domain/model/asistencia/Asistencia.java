package com.kleverkids.formacion_academica.modules.control_academico.domain.model.asistencia;

import java.time.LocalDateTime;
import java.util.UUID;

public record Asistencia(UUID id,
                         UUID claseId,
                         UUID estudianteId,
                         LocalDateTime fechaRegistro,
                         boolean presente) {
}
