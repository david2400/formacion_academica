package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.estudiante_examen;

import java.time.LocalDateTime;

public record EstudianteExamenDto(Long id,
                                  Long examenId,
                                  Long estudianteId,
                                  LocalDateTime asignadoEn) {
}
