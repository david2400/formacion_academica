package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.pregunta;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("true_false")
public class TrueFalseQuestionEntity extends QuestionEntity {
    
    @Column(name = "correct_answer")
    private boolean correctAnswer;
    
    public boolean isCorrectAnswer() { return correctAnswer; }
    public void setCorrectAnswer(boolean correctAnswer) { this.correctAnswer = correctAnswer; }
}
