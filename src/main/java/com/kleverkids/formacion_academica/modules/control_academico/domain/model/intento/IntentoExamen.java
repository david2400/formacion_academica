package com.kleverkids.formacion_academica.modules.control_academico.domain.model.intento;

import java.time.LocalDateTime;
import java.util.List;


public record IntentoExamen(Long id,
                             Long examenId,
                             Long estudianteId,
                             String estado,
                             LocalDateTime iniciadoEn,
                             LocalDateTime finalizadoEn,
                             Integer puntajeTotal,
                             List<RespuestaIntento> respuestas) {
}
