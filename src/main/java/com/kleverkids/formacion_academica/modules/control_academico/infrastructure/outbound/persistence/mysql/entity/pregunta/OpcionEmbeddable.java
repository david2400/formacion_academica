package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta;

public class OpcionEmbeddable {
    
    private Long id;
    private String texto;
    private MediaEmbeddable media;
    private boolean esCorrecta;
    
    public OpcionEmbeddable() {}
    
    public OpcionEmbeddable(Long id, String texto, MediaEmbeddable media, boolean esCorrecta) {
        this.id = id;
        this.texto = texto;
        this.media = media;
        this.esCorrecta = esCorrecta;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }
    
    public MediaEmbeddable getMedia() { return media; }
    public void setMedia(MediaEmbeddable media) { this.media = media; }
    
    public boolean esCorrecta() { return esCorrecta; }
    public void setCorrecta(boolean correcta) { esCorrecta = correcta; }
}
