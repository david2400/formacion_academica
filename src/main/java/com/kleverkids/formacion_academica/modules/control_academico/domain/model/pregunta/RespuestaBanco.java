package com.kleverkids.formacion_academica.modules.control_academico.domain.model.pregunta;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import lombok.experimental.Accessors;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaBanco {
    private Long id;
    private Long preguntaId;
    private String texto;
    private boolean esCorrecta;

}
