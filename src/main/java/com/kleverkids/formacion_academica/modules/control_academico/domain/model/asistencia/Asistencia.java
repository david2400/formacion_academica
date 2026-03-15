package com.kleverkids.formacion_academica.modules.control_academico.domain.model.asistencia;

import java.time.LocalDateTime;


public record Asistencia(Long id,
                         Long claseId,
                         Long estudianteId,
                         LocalDateTime fechaRegistro,
                         boolean presente) {
}
