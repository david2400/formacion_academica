package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia;

import java.time.LocalDateTime;
import java.util.UUID;

public record AsistenciaDto(UUID id,
                            UUID claseId,
                            UUID estudianteId,
                            LocalDateTime fechaRegistro,
                            boolean presente) {
}
