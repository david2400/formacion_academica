package com.kleverkids.formacion_academica.modules.control_academico.domain.exception;

public class AsignacionDuplicadaException extends RuntimeException {
    public AsignacionDuplicadaException(Long examenId, Long claseId) {
        super(String.format("Ya existe una asignación activa del examen %d para la clase %d", examenId, claseId));
    }
}
