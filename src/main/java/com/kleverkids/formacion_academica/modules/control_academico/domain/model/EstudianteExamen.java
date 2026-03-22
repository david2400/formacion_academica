package com.kleverkids.formacion_academica.modules.control_academico.domain.model;

import java.time.LocalDateTime;


public record EstudianteExamen(Long id,
                               Long examenId,
                               Long estudianteId,
                               LocalDateTime asignadoEn) {
}
