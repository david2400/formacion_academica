package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_pregunta;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

import lombok.experimental.Accessors;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaPreguntaDto {
    private Long id;
    private Long estudianteExamenId;
    private Long examenId;
    private Long estudianteId;
    private Long preguntaId;
    private Long respuestaBancoId;
    private String respuestaTexto;
    private Boolean esCorrecta;
    private Integer puntajeObtenido;
    private LocalDateTime registradaEn;

}
