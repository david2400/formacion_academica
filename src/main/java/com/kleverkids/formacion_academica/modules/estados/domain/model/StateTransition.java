package com.kleverkids.formacion_academica.modules.estados.domain.model;

import java.time.LocalDateTime;

/**
 * Domain model for State Transition
 * Represents valid transitions between states
 */
public class StateTransition {
    private Long id;
    private String uuid;
    private State sourceState;
    private State targetState;
    private Long moduleId;
    private Long companyId;
    private String condition;
    private String action;
    private Boolean automatic;
    private Integer order;
    private Boolean active;
    private String metadata;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public StateTransition() {}

    public StateTransition(State sourceState, State targetState, Long moduleId) {
        this.sourceState = sourceState;
        this.targetState = targetState;
        this.moduleId = moduleId;
        this.active = true;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUuid() { return uuid; }
    public void setUuid(String uuid) { this.uuid = uuid; }

    public State getSourceState() { return sourceState; }
    public void setSourceState(State sourceState) { this.sourceState = sourceState; }

    public State getTargetState() { return targetState; }
    public void setTargetState(State targetState) { this.targetState = targetState; }

    public Long getModuleId() { return moduleId; }
    public void setModuleId(Long moduleId) { this.moduleId = moduleId; }

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public Boolean getAutomatic() { return automatic; }
    public void setAutomatic(Boolean automatic) { this.automatic = automatic; }

    public Integer getOrder() { return order; }
    public void setOrder(Integer order) { this.order = order; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    public String getMetadata() { return metadata; }
    public void setMetadata(String metadata) { this.metadata = metadata; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    // Business methods
    public boolean isValid() {
        return sourceState != null && 
               targetState != null && 
               moduleId != null && 
               Boolean.TRUE.equals(active);
    }

    public boolean isAutomaticTransition() {
        return Boolean.TRUE.equals(automatic);
    }
}
