package com.kleverkids.formacion_academica.modules.control_academico.domain.exception;

public class InvalidQuestionTypeException extends RuntimeException {
    
    public InvalidQuestionTypeException(String type) {
        super("Invalid question type: " + type);
    }
}
