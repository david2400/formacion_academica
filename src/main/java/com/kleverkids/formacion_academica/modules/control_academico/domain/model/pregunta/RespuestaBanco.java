package com.kleverkids.formacion_academica.modules.control_academico.domain.model.pregunta;

import java.util.UUID;

public record RespuestaBanco(UUID id,
                             UUID preguntaId,
                             String texto,
                             boolean esCorrecta) {
}
