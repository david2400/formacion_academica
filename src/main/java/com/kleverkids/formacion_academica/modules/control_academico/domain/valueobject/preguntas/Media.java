package com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas;

import com.kleverkids.formacion_academica.shared.common.domain.ValueObject;

import java.util.Objects;
import java.util.UUID;

public final class Media extends ValueObject {
    
    private final UUID id;
    private final String type;
    private final String url;
    private final String altText;
    
    private Media(UUID id, String type, String url, String altText) {
        this.id = id != null ? id : UUID.randomUUID();
        this.type = Objects.requireNonNull(type, "Media type cannot be null");
        this.url = Objects.requireNonNull(url, "Media URL cannot be null");
        this.altText = altText;
    }
    
    public static Media create(String type, String url, String altText) {
        return new Media(null, type, url, altText);
    }
    
    public static Media create(UUID id, String type, String url, String altText) {
        return new Media(id, type, url, altText);
    }
    
    public UUID getId() {
        return id;
    }
    
    public String getType() {
        return type;
    }
    
    public String getUrl() {
        return url;
    }
    
    public String getAltText() {
        return altText;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Media media = (Media) o;
        return Objects.equals(id, media.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
