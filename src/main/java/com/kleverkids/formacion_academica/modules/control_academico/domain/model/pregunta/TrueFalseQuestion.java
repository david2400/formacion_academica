package com.kleverkids.formacion_academica.modules.control_academico.domain.model.pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.Difficulty;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.Media;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.QuestionType;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TrueFalseQuestion extends Question {
    
    private boolean correctAnswer;
    
    public TrueFalseQuestion() {
        super();
        this.questionType = QuestionType.TRUE_FALSE;
    }
    
    public TrueFalseQuestion(UUID id, String questionText, Difficulty difficulty, int maxScore,
                              UUID themeId, List<Media> media, String hint, String explanation,
                              List<String> tags, Map<String, Object> metadata, boolean correctAnswer) {
        super(id, questionText, QuestionType.TRUE_FALSE, difficulty, maxScore,
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
    public void validate() {
        // True/False questions are always valid if base validation passes
    }
}
