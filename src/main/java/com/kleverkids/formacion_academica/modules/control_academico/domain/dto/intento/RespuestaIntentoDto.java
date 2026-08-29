package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import lombok.experimental.Accessors;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaIntentoDto {
    private Long id;
    private Long intentoId;
    private Long preguntaId;
    private String respuesta;
    private boolean esCorrecta;
    private Integer puntajeObtenido;

}
