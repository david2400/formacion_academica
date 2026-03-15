package com.kleverkids.formacion_academica.modules.control_academico.domain.model.pregunta;



public record RespuestaBanco(Long id,
                             Long preguntaId,
                             String texto,
                             boolean esCorrecta) {
}
