package com.kleverkids.formacion_academica.modules.control_academico.domain.model.pregunta;

import java.util.List;


public record PreguntaBanco(Long id,
                            Long tematicaId,
                            String enunciado,
                            String tipo,
                            String nivelDificultad,
                            Integer puntaje,
                            List<RespuestaBanco> respuestas) {
}
