package com.kleverkids.formacion_academica.modules.control_academico.domain.model.pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.domain.exception.InvalidQuestionException;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.Difficulty;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.Media;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.Option;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.QuestionType;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MultipleChoiceSingleQuestion extends Question {
    
    private List<Option> options;
    private UUID correctOptionId;
    
    public MultipleChoiceSingleQuestion() {
        super();
        this.questionType = QuestionType.MULTIPLE_CHOICE_SINGLE;
    }
    
    public MultipleChoiceSingleQuestion(UUID id, String questionText, Difficulty difficulty, int maxScore,
                                         UUID themeId, List<Media> media, String hint, String explanation,
                                         List<String> tags, Map<String, Object> metadata,
                                         List<Option> options, UUID correctOptionId) {
        super(id, questionText, QuestionType.MULTIPLE_CHOICE_SINGLE, difficulty, maxScore,
              themeId, media, hint, explanation, tags, metadata);
        this.options = options;
        this.correctOptionId = correctOptionId;
        validate();
    }
    
    public List<Option> getOptions() {
        return options;
    }
    
    public void setOptions(List<Option> options) {
        this.options = options;
    }
    
    public UUID getCorrectOptionId() {
        return correctOptionId;
    }
    
    public void setCorrectOptionId(UUID correctOptionId) {
        this.correctOptionId = correctOptionId;
    }
    
    @Override
    public void validate() {
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
