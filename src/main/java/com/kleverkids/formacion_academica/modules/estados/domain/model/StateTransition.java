package com.kleverkids.formacion_academica.modules.estados.domain.model;

import java.time.LocalDateTime;

/**
 * Domain model for State Transition
 * Represents valid transitions between states
 */
public record StateTransition(
    Long id,
    String uuid,
    State sourceState,
    State targetState,
    Long moduleId,
    Long companyId,
    String condition,
    String action,
    Boolean automatic,
    Integer order,
    boolean eliminado,
    Integer usrCrea,
    Integer usrMod,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    // Business methods
    public boolean isValid() {
        return sourceState() != null && targetState() != null && eliminado();
    }

    public boolean isAutomaticTransition() {
        return Boolean.TRUE.equals(automatic());
    }
}
