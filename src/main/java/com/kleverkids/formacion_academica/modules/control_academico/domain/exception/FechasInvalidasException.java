package com.kleverkids.formacion_academica.modules.control_academico.domain.exception;

public class FechasInvalidasException extends RuntimeException {
    public FechasInvalidasException(String mensaje) {
        super(mensaje);
    }
    
    public FechasInvalidasException() {
        super("La fecha de fin debe ser posterior a la fecha de inicio");
    }
}
