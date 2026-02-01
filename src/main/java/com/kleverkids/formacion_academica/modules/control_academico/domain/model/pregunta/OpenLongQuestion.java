package com.kleverkids.formacion_academica.modules.control_academico.domain.model.pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.Difficulty;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.Media;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.QuestionType;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.Rubric;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class OpenLongQuestion extends Question {
    
    private Rubric rubric;
    private Integer minWords;
    private Integer maxWords;
    private boolean allowAttachments;
    
    public OpenLongQuestion() {
        super();
        this.questionType = QuestionType.OPEN_LONG;
    }
    
    public OpenLongQuestion(UUID id, String questionText, Difficulty difficulty, int maxScore,
                             UUID themeId, List<Media> media, String hint, String explanation,
                             List<String> tags, Map<String, Object> metadata,
                             Rubric rubric, Integer minWords, Integer maxWords, boolean allowAttachments) {
        super(id, questionText, QuestionType.OPEN_LONG, difficulty, maxScore,
              themeId, media, hint, explanation, tags, metadata);
        this.rubric = rubric;
        this.minWords = minWords;
        this.maxWords = maxWords;
        this.allowAttachments = allowAttachments;
    }
    
    public Rubric getRubric() {
        return rubric;
    }
    
    public void setRubric(Rubric rubric) {
        this.rubric = rubric;
    }
    
    public Integer getMinWords() {
        return minWords;
    }
    
    public void setMinWords(Integer minWords) {
        this.minWords = minWords;
    }
    
    public Integer getMaxWords() {
        return maxWords;
    }
    
    public void setMaxWords(Integer maxWords) {
        this.maxWords = maxWords;
    }
    
    public boolean isAllowAttachments() {
        return allowAttachments;
    }
    
    public void setAllowAttachments(boolean allowAttachments) {
        this.allowAttachments = allowAttachments;
    }
    
    @Override
    public void validate() {
        // Open long questions require manual grading with rubric
    }
}
