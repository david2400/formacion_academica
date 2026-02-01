package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.pregunta;

import java.util.UUID;

public class MatchingPairEmbeddable {
    
    private UUID id;
    private String leftItem;
    private String rightItem;
    private MediaEmbeddable leftMedia;
    private MediaEmbeddable rightMedia;
    
    public MatchingPairEmbeddable() {}
    
    public MatchingPairEmbeddable(UUID id, String leftItem, String rightItem, 
                                   MediaEmbeddable leftMedia, MediaEmbeddable rightMedia) {
        this.id = id;
        this.leftItem = leftItem;
        this.rightItem = rightItem;
        this.leftMedia = leftMedia;
        this.rightMedia = rightMedia;
    }
    
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public String getLeftItem() { return leftItem; }
    public void setLeftItem(String leftItem) { this.leftItem = leftItem; }
    
    public String getRightItem() { return rightItem; }
    public void setRightItem(String rightItem) { this.rightItem = rightItem; }
    
    public MediaEmbeddable getLeftMedia() { return leftMedia; }
    public void setLeftMedia(MediaEmbeddable leftMedia) { this.leftMedia = leftMedia; }
    
    public MediaEmbeddable getRightMedia() { return rightMedia; }
    public void setRightMedia(MediaEmbeddable rightMedia) { this.rightMedia = rightMedia; }
}
