package com.kleverkids.formacion_academica.modules.control_academico.domain.exception;

public class AsignacionExamenNotFoundException extends RuntimeException {
    public AsignacionExamenNotFoundException(Long id) {
        super("Asignación de examen no encontrada con ID: " + id);
    }
}
