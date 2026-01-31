package com.kleverkids.formacion_academica.modules.control_academico.domain.model.estudiante_examen;

import java.time.LocalDateTime;
import java.util.UUID;

public record EstudianteExamen(UUID id,
                               UUID examenId,
                               UUID estudianteId,
                               LocalDateTime asignadoEn) {
}
