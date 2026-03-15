package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta;

public record RespuestaBancoDto(Long id,
                                Long preguntaId,
                                String texto,
                                boolean esCorrecta) {
}
