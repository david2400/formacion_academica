package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta;

public class OrderingItemEmbeddable {
    
    private Long id;
    private String text;
    private int correctPosition;
    private MediaEmbeddable media;
    
    public OrderingItemEmbeddable() {}
    
    public OrderingItemEmbeddable(Long id, String text, int correctPosition, MediaEmbeddable media) {
        this.id = id;
        this.text = text;
        this.correctPosition = correctPosition;
        this.media = media;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    
    public int getCorrectPosition() { return correctPosition; }
    public void setCorrectPosition(int correctPosition) { this.correctPosition = correctPosition; }
    
    public MediaEmbeddable getMedia() { return media; }
    public void setMedia(MediaEmbeddable media) { this.media = media; }
}
