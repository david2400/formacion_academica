package com.kleverkids.formacion_academica.modules.questions.infrastructure.adapter.out.persistence.entity;

import java.util.UUID;

public class MediaEmbeddable {
    
    private UUID id;
    private String type;
    private String url;
    private String altText;
    
    public MediaEmbeddable() {}
    
    public MediaEmbeddable(UUID id, String type, String url, String altText) {
        this.id = id;
        this.type = type;
        this.url = url;
        this.altText = altText;
    }
    
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    
    public String getAltText() { return altText; }
    public void setAltText(String altText) { this.altText = altText; }
}
