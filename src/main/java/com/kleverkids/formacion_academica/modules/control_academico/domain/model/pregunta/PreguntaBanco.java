package com.kleverkids.formacion_academica.modules.control_academico.domain.model.pregunta;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

import lombok.experimental.Accessors;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreguntaBanco {
    private Long id;
    private Long tematicaId;
    private String enunciado;
    private String tipo;
    private String nivelDificultad;
    private Integer puntaje;
    private List<RespuestaBanco> respuestas;

}
