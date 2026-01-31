package com.kleverkids.formacion_academica.modules.control_academico.domain.model.pregunta;

import java.util.List;
import java.util.UUID;

public record PreguntaBanco(UUID id,
                            UUID tematicaId,
                            String enunciado,
                            String tipo,
                            String nivelDificultad,
                            Integer puntaje,
                            List<RespuestaBanco> respuestas) {
}
