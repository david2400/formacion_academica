package com.kleverkids.formacion_academica.modules.control_academico.domain.model.pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.domain.exception.InvalidQuestionException;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.Difficulty;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.Media;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.Option;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.TipoPregunta;

import java.util.List;
import java.util.Map;

public class MultipleChoiceSingleQuestion extends Pregunta {
    
    private List<Option> options;
    private Long correctOptionId;
    
    public MultipleChoiceSingleQuestion() {
        super();
        this.tipoPregunta = TipoPregunta.OPCION_MULTIPLE_UNICA;
    }
    
    public MultipleChoiceSingleQuestion(Long id, String textoPregunta, Difficulty dificultad, int puntajeMaximo,
                                         Long themeId, List<Media> media, String hint, String explanation,
                                         List<String> tags, Map<String, Object> metadata,
                                         List<Option> options, Long correctOptionId) {
        super(id, textoPregunta, TipoPregunta.OPCION_MULTIPLE_UNICA, dificultad, puntajeMaximo,
              themeId, media, hint, explanation, tags, metadata);
        this.options = options;
        this.correctOptionId = correctOptionId;
        validar();
    }
    
    public List<Option> getOptions() {
        return options;
    }
    
    public void setOptions(List<Option> options) {
        this.options = options;
    }
    
    public Long getCorrectOptionId() {
        return correctOptionId;
    }
    
    public void setCorrectOptionId(Long correctOptionId) {
        this.correctOptionId = correctOptionId;
    }
    
    @Override
    public void validar() {
        if (options == null || options.size() < 2) {
            throw new InvalidQuestionException("Multiple choice question must have at least 2 options");
        }
        if (correctOptionId == null) {
            throw new InvalidQuestionException("Correct option ID is required");
        }
        boolean correctOptionExists = options.stream()
            .anyMatch(o -> o.getId().equals(correctOptionId));
        if (!correctOptionExists) {
            throw new InvalidQuestionException("Correct option ID must exist in options");
        }
    }
}
