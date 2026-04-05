package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "questions")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "question_type", discriminatorType = DiscriminatorType.STRING)
public abstract class PreguntaEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "question_text", nullable = false, length = 2000)
    private String textoPregunta;
    
    @Column(name = "difficulty", length = 20)
    private String dificultad;
    
    @Column(name = "theme_id")
    private Long temaId;
    
    @Column(name = "max_score")
    private Integer puntajeMaximo;
    
    @Column(name = "time_limit_seconds")
    private Integer tiempoLimiteSegundos;
    
    @Column(name = "hint")
    private String hint;
    
    @Column(name = "explanation")
    private String explanation;
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "tags", columnDefinition = "json")
    private List<String> tags;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();
    
    @Column(name = "updated_at")
    private Instant updatedAt;
    
    @Column(name = "created_by")
    private Long createdBy;
    
    @Column(name = "updated_by")
    private Long updatedBy;
    
    @Column(name = "version")
    private Integer version = 1;
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "metadata", columnDefinition = "json")
    private Map<String, Object> metadata;
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "rubric", columnDefinition = "json")
    private RbricaEmbeddable rubric;
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "media", columnDefinition = "json")
    private List<MediaEmbeddable> media;
    
    @OneToMany(mappedBy = "pregunta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RespuestaPreguntaEntity> respuestas;
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTextoPregunta() {
        return textoPregunta;
    }
    
    public void setTextoPregunta(String textoPregunta) {
        this.textoPregunta = textoPregunta;
    }
    
    public String getDificultad() {
        return dificultad;
    }
    
    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }
    
    public Long getTemaId() {
        return temaId;
    }
    
    public void setTemaId(Long temaId) {
        this.temaId = temaId;
    }
    
    public Integer getPuntajeMaximo() {
        return puntajeMaximo;
    }
    
    public void setPuntajeMaximo(Integer puntajeMaximo) {
        this.puntajeMaximo = puntajeMaximo;
    }
    
    public Integer getTiempoLimiteSegundos() {
        return tiempoLimiteSegundos;
    }
    
    public void setTiempoLimiteSegundos(Integer tiempoLimiteSegundos) {
        this.tiempoLimiteSegundos = tiempoLimiteSegundos;
    }
    
    public String getHint() {
        return hint;
    }
    
    public void setHint(String hint) {
        this.hint = hint;
    }
    
    public String getExplanation() {
        return explanation;
    }
    
    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
    
    public List<String> getTags() {
        return tags;
    }
    
    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    
    public Instant getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public Long getCreatedBy() {
        return createdBy;
    }
    
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }
    
    public Long getUpdatedBy() {
        return updatedBy;
    }
    
    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }
    
    public Integer getVersion() {
        return version;
    }
    
    public void setVersion(Integer version) {
        this.version = version;
    }
    
    public Map<String, Object> getMetadata() {
        return metadata;
    }
    
    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }
    
    public RbricaEmbeddable getRubric() {
        return rubric;
    }
    
    public void setRubric(RbricaEmbeddable rubric) {
        this.rubric = rubric;
    }
    
    public List<MediaEmbeddable> getMedia() {
        return media;
    }
    
    public void setMedia(List<MediaEmbeddable> media) {
        this.media = media;
    }
    
    public List<RespuestaPreguntaEntity> getRespuestas() {
        return respuestas;
    }
    
    public void setRespuestas(List<RespuestaPreguntaEntity> respuestas) {
        this.respuestas = respuestas;
    }
    
    // Getters adicionales para compatibilidad con nombres en español
    public Instant getFechaCreacion() { return createdAt; }
    public void setFechaCreacion(Instant fechaCreacion) { this.createdAt = fechaCreacion; }
    
    public Instant getFechaActualizacion() { return updatedAt; }
    public void setFechaActualizacion(Instant fechaActualizacion) { this.updatedAt = fechaActualizacion; }
    
    public Long getCreadoPor() { return createdBy; }
    public void setCreadoPor(Long creadoPor) { this.createdBy = creadoPor; }
    
    public Long getActualizadoPor() { return updatedBy; }
    public void setActualizadoPor(Long actualizadoPor) { this.updatedBy = actualizadoPor; }
    
    public Map<String, Object> getMetadatos() { return metadata; }
    public void setMetadatos(Map<String, Object> metadatos) { this.metadata = metadatos; }
    
    public RbricaEmbeddable getRúbrica() { return rubric; }
    public void setRúbrica(RbricaEmbeddable rúbrica) { this.rubric = rúbrica; }
    
    public List<MediaEmbeddable> getMedios() { return media; }
    public void setMedios(List<MediaEmbeddable> medios) { this.media = medios; }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }
}
