package com.kleverkids.formacion_academica.modules.control_academico.domain.model.pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.Difficulty;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.Media;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.Rubrica;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.TipoPregunta;

import java.util.List;
import java.util.Map;

public class OpenLongQuestion extends Pregunta {
    
    private Rubrica rubric;
    private Integer minWords;
    private Integer maxWords;
    private boolean allowAttachments;
    
    public OpenLongQuestion() {
        super();
        this.tipoPregunta = TipoPregunta.ABIERTA_LARGA;
    }
    
    public OpenLongQuestion(Long id, String questionText, Difficulty difficulty, int maxScore,
                             Long themeId, List<Media> media, String hint, String explanation,
                             List<String> tags, Map<String, Object> metadata,
                             Rubrica rubric, Integer minWords, Integer maxWords, boolean allowAttachments) {
        super(id, questionText, TipoPregunta.ABIERTA_LARGA, difficulty, maxScore,
              themeId, media, hint, explanation, tags, metadata);
        this.rubric = rubric;
        this.minWords = minWords;
        this.maxWords = maxWords;
        this.allowAttachments = allowAttachments;
    }
    
    public Rubrica getRubric() {
        return rubric;
    }
    
    public void setRubric(Rubrica rubric) {
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
    public void validar() {
        // Open long questions require manual grading with rubric
    }
}
