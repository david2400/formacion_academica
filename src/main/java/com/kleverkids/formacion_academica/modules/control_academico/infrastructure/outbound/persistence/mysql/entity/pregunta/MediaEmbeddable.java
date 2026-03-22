package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta;



public class MediaEmbeddable {
    
    private Long id;
    private String type;
    private String url;
    private String altText;
    
    public MediaEmbeddable() {}
    
    public MediaEmbeddable(Long id, String type, String url, String altText) {
        this.id = id;
        this.type = type;
        this.url = url;
        this.altText = altText;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    
    public String getAltText() { return altText; }
    public void setAltText(String altText) { this.altText = altText; }
}
