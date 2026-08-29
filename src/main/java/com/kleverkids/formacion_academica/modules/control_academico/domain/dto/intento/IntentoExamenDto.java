package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

import lombok.experimental.Accessors;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IntentoExamenDto {
    private Long id;
    private Long examenId;
    private Long estudianteId;
    private String estado;
    private LocalDateTime iniciadoEn;
    private LocalDateTime finalizadoEn;
    private Integer puntajeTotal;
    private List<RespuestaIntentoDto> respuestas;

}
