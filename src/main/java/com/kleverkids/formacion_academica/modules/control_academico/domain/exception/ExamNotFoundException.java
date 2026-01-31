package com.kleverkids.formacion_academica.modules.control_academico.domain.exception;

import java.util.UUID;

public class ExamNotFoundException extends RuntimeException {
    
    public ExamNotFoundException(UUID id) {
        super("Exam not found with id: " + id);
    }
    
    public ExamNotFoundException(String message) {
        super(message);
    }
}
