package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento;

import java.time.LocalDateTime;
import java.util.List;


public record IntentoExamenDto(Long id,
                               Long examenId,
                               Long estudianteId,
                               String estado,
                               LocalDateTime iniciadoEn,
                               LocalDateTime finalizadoEn,
                               Integer puntajeTotal,
                               List<RespuestaIntentoDto> respuestas) {
}
