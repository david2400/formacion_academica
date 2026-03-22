package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta;

public class ParEmparejamientoEmbeddable {
    
    private Long id;
    private String itemIzquierdo;
    private String itemDerecho;
    private MediaEmbeddable mediaIzquierda;
    private MediaEmbeddable mediaDerecha;
    
    public ParEmparejamientoEmbeddable() {}
    
    public ParEmparejamientoEmbeddable(Long id, String itemIzquierdo, String itemDerecho, 
                                   MediaEmbeddable mediaIzquierda, MediaEmbeddable mediaDerecha) {
        this.id = id;
        this.itemIzquierdo = itemIzquierdo;
        this.itemDerecho = itemDerecho;
        this.mediaIzquierda = mediaIzquierda;
        this.mediaDerecha = mediaDerecha;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getItemIzquierdo() { return itemIzquierdo; }
    public void setItemIzquierdo(String itemIzquierdo) { this.itemIzquierdo = itemIzquierdo; }
    
    public String getItemDerecho() { return itemDerecho; }
    public void setItemDerecho(String itemDerecho) { this.itemDerecho = itemDerecho; }
    
    public MediaEmbeddable getMediaIzquierda() { return mediaIzquierda; }
    public void setMediaIzquierda(MediaEmbeddable mediaIzquierda) { this.mediaIzquierda = mediaIzquierda; }
    
    public MediaEmbeddable getMediaDerecha() { return mediaDerecha; }
    public void setMediaDerecha(MediaEmbeddable mediaDerecha) { this.mediaDerecha = mediaDerecha; }
}
