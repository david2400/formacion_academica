package com.kleverkids.formacion_academica.modules.control_academico.domain.exception;



public class ExamNotFoundException extends RuntimeException {
    
    public ExamNotFoundException(Long id) {
        super("Examen no encontrado con id: " + id);
    }
    
    public ExamNotFoundException(String message) {
        super(message);
    }
}
