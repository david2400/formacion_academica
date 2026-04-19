package com.kleverkids.formacion_academica.modules.control_academico.domain.exception;

public class TematicaNotFoundException extends RuntimeException {
    public TematicaNotFoundException(Long id) {
        super("Temática no encontrada con ID: " + id);
    }
}
