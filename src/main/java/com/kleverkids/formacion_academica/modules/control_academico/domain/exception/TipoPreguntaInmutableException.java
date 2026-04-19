package com.kleverkids.formacion_academica.modules.control_academico.domain.exception;

public class TipoPreguntaInmutableException extends RuntimeException {
    public TipoPreguntaInmutableException() {
        super("No se puede cambiar el tipo de pregunta en una actualización");
    }
}
