package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta;

import java.util.UUID;

public record RespuestaBancoDto(UUID id,
                                UUID preguntaId,
                                String texto,
                                boolean esCorrecta) {
}
