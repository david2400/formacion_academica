package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta;

import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Entity
@SuperBuilder
@Table(name = "questions")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "question_type", discriminatorType = DiscriminatorType.STRING)
public abstract class PreguntaBaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "question_text", nullable = false, length = 2000)
    private String textoPregunta;
    
    @Column(name = "question_type", insertable = false, updatable = false)
    private String tipoPregunta;
    
    @Column(name = "difficulty", nullable = false)
    private String dificultad;
    
    @Column(name = "max_score", nullable = false)
    private int puntajeMaximo;
    
    @Column(name = "theme_id")
    private Long temaId;
    
    @Column(name = "hint", length = 1000)
    private String pista;
    
    @Column(name = "explanation", length = 2000)
    private String explicacion;
    
    @ElementCollection
    @CollectionTable(name = "question_tags", joinColumns = @JoinColumn(name = "question_id"))
    @Column(name = "tag")
    private List<String> etiquetas;
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "metadata", columnDefinition = "json")
    private Map<String, Object> metadatos;
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "media", columnDefinition = "json")
    private List<MediaEmbeddable> medios;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;
    
    @Column(name = "updated_at")
    private Instant updatedAt;
    
    @Column(name = "version")
    private Integer version = 1;
    
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) createdAt = Instant.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTextoPregunta() { return textoPregunta; }
    public void setTextoPregunta(String textoPregunta) { this.textoPregunta = textoPregunta; }
    
    public String getTipoPregunta() { return tipoPregunta; }
    public void setTipoPregunta(String tipoPregunta) { this.tipoPregunta = tipoPregunta; }
    
    public String getDificultad() { return dificultad; }
    public void setDificultad(String dificultad) { this.dificultad = dificultad; }
    
    public int getPuntajeMaximo() { return puntajeMaximo; }
    public void setPuntajeMaximo(int puntajeMaximo) { this.puntajeMaximo = puntajeMaximo; }
    
    public Long getTemaId() { return temaId; }
    public void setTemaId(Long temaId) { this.temaId = temaId; }
    
    public String getPista() { return pista; }
    public void setPista(String pista) { this.pista = pista; }
    
    public String getExplicacion() { return explicacion; }
    public void setExplicacion(String explicacion) { this.explicacion = explicacion; }
    
    public List<String> getEtiquetas() { return etiquetas; }
    public void setEtiquetas(List<String> etiquetas) { this.etiquetas = etiquetas; }
    
    public Map<String, Object> getMetadatos() { return metadatos; }
    public void setMetadatos(Map<String, Object> metadatos) { this.metadatos = metadatos; }
    
    public List<MediaEmbeddable> getMedios() { return medios; }
    public void setMedios(List<MediaEmbeddable> medios) { this.medios = medios; }
    
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
    
    public Integer getVersion() { return version; }
    public void setVersion(Integer version) { this.version = version; }
}
