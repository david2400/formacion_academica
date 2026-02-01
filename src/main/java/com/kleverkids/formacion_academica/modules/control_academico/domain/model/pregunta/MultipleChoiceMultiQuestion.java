package com.kleverkids.formacion_academica.modules.control_academico.domain.model.pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.domain.exception.InvalidQuestionException;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.Difficulty;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.Media;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.Option;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.QuestionType;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class MultipleChoiceMultiQuestion extends Question {
    
    private List<Option> options;
    private List<UUID> correctOptionIds;
    private Integer minSelections;
    private Integer maxSelections;
    
    public MultipleChoiceMultiQuestion() {
        super();
        this.questionType = QuestionType.MULTIPLE_CHOICE_MULTI;
    }
    
    public MultipleChoiceMultiQuestion(UUID id, String questionText, Difficulty difficulty, int maxScore,
                                        UUID themeId, List<Media> media, String hint, String explanation,
                                        List<String> tags, Map<String, Object> metadata,
                                        List<Option> options, List<UUID> correctOptionIds,
                                        Integer minSelections, Integer maxSelections) {
        super(id, questionText, QuestionType.MULTIPLE_CHOICE_MULTI, difficulty, maxScore,
              themeId, media, hint, explanation, tags, metadata);
        this.options = options;
        this.correctOptionIds = correctOptionIds;
        this.minSelections = minSelections;
        this.maxSelections = maxSelections;
        validate();
    }
    
    public List<Option> getOptions() {
        return options;
    }
    
    public void setOptions(List<Option> options) {
        this.options = options;
    }
    
    public List<UUID> getCorrectOptionIds() {
        return correctOptionIds;
    }
    
    public void setCorrectOptionIds(List<UUID> correctOptionIds) {
        this.correctOptionIds = correctOptionIds;
    }
    
    public Integer getMinSelections() {
        return minSelections;
    }
    
    public void setMinSelections(Integer minSelections) {
        this.minSelections = minSelections;
    }
    
    public Integer getMaxSelections() {
        return maxSelections;
    }
    
    public void setMaxSelections(Integer maxSelections) {
        this.maxSelections = maxSelections;
    }
    
    @Override
    public void validate() {
        if (options == null || options.size() < 2) {
            throw new InvalidQuestionException("Multiple choice question must have at least 2 options");
        }
        if (correctOptionIds == null || correctOptionIds.isEmpty()) {
            throw new InvalidQuestionException("At least one correct option ID is required");
        }
        
        Set<UUID> optionIds = options.stream()
            .map(Option::getId)
            .collect(Collectors.toSet());
        
        for (UUID correctId : correctOptionIds) {
            if (!optionIds.contains(correctId)) {
                throw new InvalidQuestionException("All correct option IDs must exist in options");
            }
        }
    }
}
