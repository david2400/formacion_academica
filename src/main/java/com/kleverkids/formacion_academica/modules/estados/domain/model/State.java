package com.kleverkids.formacion_academica.modules.estados.domain.model;

import java.time.LocalDateTime;

/**
 * Domain model for State
 * Represents the core business entity for state management
 */
public class State {
    private Long id;
    private String uuid;
    private String code;
    private String name;
    private String description;
    private String color;
    private String icon;
    private Long moduleId;
    private Long companyId;
    private Boolean isInitial;
    private Boolean isFinal;
    private Integer order;
    private Boolean active;
    private String metadata;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public State() {}

    public State(String code, String name, Long moduleId) {
        this.code = code;
        this.name = name;
        this.moduleId = moduleId;
        this.active = true;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUuid() { return uuid; }
    public void setUuid(String uuid) { this.uuid = uuid; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }

    public Long getModuleId() { return moduleId; }
    public void setModuleId(Long moduleId) { this.moduleId = moduleId; }

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public Boolean getIsInitial() { return isInitial; }
    public void setIsInitial(Boolean isInitial) { this.isInitial = isInitial; }

    public Boolean getIsFinal() { return isFinal; }
    public void setIsFinal(Boolean isFinal) { this.isFinal = isFinal; }

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
    public boolean canTransitionTo(State targetState) {
        // Business logic for state transitions
        return this.active && targetState.active;
    }

    public boolean isTerminalState() {
        return Boolean.TRUE.equals(isFinal);
    }

    public boolean isStartingState() {
        return Boolean.TRUE.equals(isInitial);
    }
}
