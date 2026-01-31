package com.kleverkids.formacion_academica.modules.control_academico.domain.model.intento;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record IntentoExamen(UUID id,
                             UUID examenId,
                             UUID estudianteId,
                             String estado,
                             LocalDateTime iniciadoEn,
                             LocalDateTime finalizadoEn,
                             Integer puntajeTotal,
                             List<RespuestaIntento> respuestas) {
}
