package com.kleverkids.formacion_academica.modules.control_academico.domain.model.pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.domain.exception.InvalidQuestionException;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.Difficulty;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.MatchingPair;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.Media;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.TipoPregunta;

import java.util.List;
import java.util.Map;

public class MatchingQuestion extends Pregunta {
    
    private List<MatchingPair> pairs;
    private boolean partialCredit;
    
    public MatchingQuestion() {
        super();
        this.tipoPregunta = TipoPregunta.EMPAREJAMIENTO;
    }
    
    public MatchingQuestion(Long id, String questionText, Difficulty difficulty, int maxScore,
                             Long themeId, List<Media> media, String hint, String explanation,
                             List<String> tags, Map<String, Object> metadata,
                             List<MatchingPair> pairs, boolean partialCredit) {
        super(id, questionText, TipoPregunta.EMPAREJAMIENTO, difficulty, maxScore,
              themeId, media, hint, explanation, tags, metadata);
        this.pairs = pairs;
        this.partialCredit = partialCredit;
        validar();
    }
    
    public List<MatchingPair> getPairs() {
        return pairs;
    }
    
    public void setPairs(List<MatchingPair> pairs) {
        this.pairs = pairs;
    }
    
    public boolean isPartialCredit() {
        return partialCredit;
    }
    
    public void setPartialCredit(boolean partialCredit) {
        this.partialCredit = partialCredit;
    }
    
    @Override
    public void validar() {
        if (pairs == null || pairs.size() < 2) {
            throw new InvalidQuestionException("Matching question must have at least 2 pairs");
        }
    }
}
