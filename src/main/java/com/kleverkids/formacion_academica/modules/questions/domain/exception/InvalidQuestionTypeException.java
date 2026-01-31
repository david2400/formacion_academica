package com.kleverkids.formacion_academica.modules.questions.domain.exception;

public class InvalidQuestionTypeException extends RuntimeException {
    
    public InvalidQuestionTypeException(String type) {
        super("Invalid question type: " + type);
    }
}
