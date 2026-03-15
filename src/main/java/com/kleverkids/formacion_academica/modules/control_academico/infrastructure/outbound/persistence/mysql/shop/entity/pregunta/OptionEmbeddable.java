package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.pregunta;

public class OptionEmbeddable {
    
    private Long id;
    private String text;
    private MediaEmbeddable media;
    private boolean isCorrect;
    
    public OptionEmbeddable() {}
    
    public OptionEmbeddable(Long id, String text, MediaEmbeddable media, boolean isCorrect) {
        this.id = id;
        this.text = text;
        this.media = media;
        this.isCorrect = isCorrect;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    
    public MediaEmbeddable getMedia() { return media; }
    public void setMedia(MediaEmbeddable media) { this.media = media; }
    
    public boolean isCorrect() { return isCorrect; }
    public void setCorrect(boolean correct) { isCorrect = correct; }
}
