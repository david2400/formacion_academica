package com.kleverkids.formacion_academica.modules.estados.domain.model;

import java.time.LocalDateTime;

/**
 * Domain model for State
 * Represents core business entity for state management
 */
public record State(
    Long id,
    String uuid,
    String code,
    String name,
    String description,
    String color,
    String icon,
    Long moduleId,
    Long companyId,
    Boolean isInitial,
    Boolean isFinal,
    Integer order,
    boolean activo,
    Integer usrCrea,
    Integer usrMod,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    // Business methods
    public boolean canTransitionTo(State targetState) {
        // Business logic for state transitions
        return this.activo() && targetState.activo();
    }

    public boolean isTerminalState() {
        return Boolean.TRUE.equals(isFinal());
    }

    public boolean isStartingState() {
        return Boolean.TRUE.equals(isInitial());
    }
}
