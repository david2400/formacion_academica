package com.kleverkids.formacion_academica.modules.control_academico.domain.exception;

import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.examenes.ExamStatus;

public class ExamNotModifiableException extends RuntimeException {
    
    public ExamNotModifiableException(ExamStatus status) {
        super("El examen no puede ser modificado en estado: " + status.getValue());
    }
}
