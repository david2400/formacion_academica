package com.kleverkids.formacion_academica.modules.control_academico.domain.exception;

public class ExcepcionPreguntaNoEncontrada extends RuntimeException {
    
    public ExcepcionPreguntaNoEncontrada(Long id) {
        super("Pregunta no encontrada con id: " + id);
    }
    
    public ExcepcionPreguntaNoEncontrada(String message) {
        super(message);
    }
}
