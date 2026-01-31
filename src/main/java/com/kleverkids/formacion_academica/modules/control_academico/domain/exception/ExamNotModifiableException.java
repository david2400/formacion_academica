package com.kleverkids.formacion_academica.modules.control_academico.domain.exception;

import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.ExamStatus;

public class ExamNotModifiableException extends RuntimeException {
    
    public ExamNotModifiableException(ExamStatus status) {
        super("Exam cannot be modified in status: " + status.getValue());
    }
}
