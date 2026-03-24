package com.kleverkids.formacion_academica.modules.control_academico.domain.model;

import java.time.LocalDateTime;

public record EstudianteExamen(Long id,
                               Long examenId,
                               Long estudianteId,
                               LocalDateTime asignadoEn,
                               boolean activo,
                               Integer usrCrea,
                               Integer usrMod,
                               LocalDateTime createdAt,
                               LocalDateTime updatedAt) {
}
