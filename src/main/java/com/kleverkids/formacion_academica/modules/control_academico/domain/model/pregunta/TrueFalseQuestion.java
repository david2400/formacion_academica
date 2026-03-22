package com.kleverkids.formacion_academica.modules.control_academico.domain.model.pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.Difficulty;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.Media;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.TipoPregunta;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.TipoPregunta;

import java.util.List;
import java.util.Map;

public class TrueFalseQuestion extends Pregunta {
    
    private boolean correctAnswer;
    
    public TrueFalseQuestion() {
        super();
        this.tipoPregunta = TipoPregunta.VERDADERO_FALSO;
    }
    
    public TrueFalseQuestion(Long id, String questionText, Difficulty difficulty, int maxScore,
                              Long themeId, List<Media> media, String hint, String explanation,
                              List<String> tags, Map<String, Object> metadata, boolean correctAnswer) {
        super(id, questionText, TipoPregunta.VERDADERO_FALSO, difficulty, maxScore,
              themeId, media, hint, explanation, tags, metadata);
        this.correctAnswer = correctAnswer;
    }
    
    public boolean isCorrectAnswer() {
        return correctAnswer;
    }
    
    public void setCorrectAnswer(boolean correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
    
    @Override
    public void validar() {
        // True/False questions are always valid if base validation passes
    }
}
