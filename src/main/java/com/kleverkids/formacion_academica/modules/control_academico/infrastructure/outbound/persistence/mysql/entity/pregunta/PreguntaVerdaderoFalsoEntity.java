package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("true_false")
public class PreguntaVerdaderoFalsoEntity extends PreguntaEntity {
    
    @Column(name = "correct_answer")
    private Boolean correctAnswer;
    
    public Boolean getCorrectAnswer() { return correctAnswer; }
    public void setCorrectAnswer(Boolean correctAnswer) { this.correctAnswer = correctAnswer; }
}
