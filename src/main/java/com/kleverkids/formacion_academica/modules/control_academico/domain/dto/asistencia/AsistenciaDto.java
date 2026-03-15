package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia;

import java.time.LocalDateTime;

public record AsistenciaDto(Long id,
                            Long claseId,
                            Long estudianteId,
                            LocalDateTime fechaRegistro,
                            boolean presente) {
}
