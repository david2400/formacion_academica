package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.estudiante_examen;

import java.time.LocalDateTime;
import java.util.UUID;

public record EstudianteExamenDto(UUID id,
                                  UUID examenId,
                                  UUID estudianteId,
                                  LocalDateTime asignadoEn) {
}
