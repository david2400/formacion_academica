package com.kleverkids.formacion_academica.modules.questions.infrastructure.adapter.out.persistence.entity;

import java.util.UUID;

public class OptionEmbeddable {
    
    private UUID id;
    private String text;
    private MediaEmbeddable media;
    private boolean isCorrect;
    
    public OptionEmbeddable() {}
    
    public OptionEmbeddable(UUID id, String text, MediaEmbeddable media, boolean isCorrect) {
        this.id = id;
        this.text = text;
        this.media = media;
        this.isCorrect = isCorrect;
    }
    
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    
    public MediaEmbeddable getMedia() { return media; }
    public void setMedia(MediaEmbeddable media) { this.media = media; }
    
    public boolean isCorrect() { return isCorrect; }
    public void setCorrect(boolean correct) { isCorrect = correct; }
}
