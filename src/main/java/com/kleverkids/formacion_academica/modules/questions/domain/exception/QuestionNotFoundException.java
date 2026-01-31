package com.kleverkids.formacion_academica.modules.questions.domain.exception;

import java.util.UUID;

public class QuestionNotFoundException extends RuntimeException {
    
    public QuestionNotFoundException(UUID id) {
        super("Question not found with id: " + id);
    }
    
    public QuestionNotFoundException(String message) {
        super(message);
    }
}
