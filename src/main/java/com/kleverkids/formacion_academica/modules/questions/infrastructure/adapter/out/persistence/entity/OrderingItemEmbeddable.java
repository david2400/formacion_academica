package com.kleverkids.formacion_academica.modules.questions.infrastructure.adapter.out.persistence.entity;

import java.util.UUID;

public class OrderingItemEmbeddable {
    
    private UUID id;
    private String text;
    private int correctPosition;
    private MediaEmbeddable media;
    
    public OrderingItemEmbeddable() {}
    
    public OrderingItemEmbeddable(UUID id, String text, int correctPosition, MediaEmbeddable media) {
        this.id = id;
        this.text = text;
        this.correctPosition = correctPosition;
        this.media = media;
    }
    
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    
    public int getCorrectPosition() { return correctPosition; }
    public void setCorrectPosition(int correctPosition) { this.correctPosition = correctPosition; }
    
    public MediaEmbeddable getMedia() { return media; }
    public void setMedia(MediaEmbeddable media) { this.media = media; }
}
