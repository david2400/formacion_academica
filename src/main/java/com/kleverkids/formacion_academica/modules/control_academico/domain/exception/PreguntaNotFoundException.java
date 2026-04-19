package com.kleverkids.formacion_academica.modules.control_academico.domain.exception;

public class PreguntaNotFoundException extends RuntimeException {
    public PreguntaNotFoundException(Long id) {
        super("Pregunta no encontrada con ID: " + id);
    }
}
