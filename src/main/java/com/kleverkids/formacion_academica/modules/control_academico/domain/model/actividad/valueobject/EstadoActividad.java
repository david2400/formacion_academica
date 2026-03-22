package com.kleverkids.formacion_academica.modules.control_academico.domain.model.actividad.valueobject;

import java.util.Set;

public enum EstadoActividad {
    
    BORRADOR("Borrador"),
    ACTIVA("Activa"),
    PAUSADA("Pausada"),
    BLOQUEADA("Bloqueada"),
    FINALIZADA("Finalizada");
    
    private final String nombre;
    
    EstadoActividad(String nombre) {
        this.nombre = nombre;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    // Métodos de validación de transiciones
    public boolean puedeTransicionarA(EstadoActividad nuevoEstado) {
        return switch (this) {
            case BORRADOR -> nuevoEstado == ACTIVA;
            case ACTIVA -> nuevoEstado == PAUSADA || nuevoEstado == BLOQUEADA || nuevoEstado == FINALIZADA;
            case PAUSADA -> nuevoEstado == ACTIVA || nuevoEstado == FINALIZADA;
            case BLOQUEADA -> nuevoEstado == ACTIVA || nuevoEstado == FINALIZADA;
            case FINALIZADA -> false; // Estado final
        };
    }
    
    public boolean estaDisponible() {
        return this == ACTIVA;
    }
    
    public boolean estaFinalizada() {
        return this == FINALIZADA;
    }
    
    public boolean esEditable() {
        return this == BORRADOR || this == PAUSADA;
    }
}
