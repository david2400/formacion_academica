package com.kleverkids.formacion_academica.modules.control_academico.domain.exception;

public class QuestionNotFoundException extends RuntimeException {
    
    public QuestionNotFoundException(Long id) {
        super("Question not found with id: " + id);
    }
    
    public QuestionNotFoundException(String message) {
        super(message);
    }
}
