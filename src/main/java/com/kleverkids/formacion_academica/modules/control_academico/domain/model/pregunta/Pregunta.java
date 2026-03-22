package com.kleverkids.formacion_academica.modules.control_academico.domain.model.pregunta;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.Difficulty;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.Media;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.TipoPregunta;
import com.kleverkids.formacion_academica.shared.common.domain.AggregateRoot;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    property = "questionType",
    visible = true
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = MultipleChoiceSingleQuestion.class, name = "multiple_choice_single"),
    @JsonSubTypes.Type(value = MultipleChoiceMultiQuestion.class, name = "multiple_choice_multi"),
    @JsonSubTypes.Type(value = TrueFalseQuestion.class, name = "true_false"),
    @JsonSubTypes.Type(value = OpenShortQuestion.class, name = "open_short"),
    @JsonSubTypes.Type(value = OpenLongQuestion.class, name = "open_long"),
    @JsonSubTypes.Type(value = NumericQuestion.class, name = "numeric"),
    @JsonSubTypes.Type(value = ScaleQuestion.class, name = "scale"),
    @JsonSubTypes.Type(value = OrderingQuestion.class, name = "ordering"),
    @JsonSubTypes.Type(value = MatchingQuestion.class, name = "matching")
})
public abstract class Pregunta extends AggregateRoot<Long> {
    
    protected Long id;
    protected String textoPregunta;
    protected TipoPregunta tipoPregunta;
    protected Difficulty dificultad;
    protected int puntajeMaximo;
    protected Long temaId;
    protected List<Media> medios;
    protected String pista;
    protected String explicacion;
    protected List<String> etiquetas;
    protected Map<String, Object> metadatos;
    protected Instant createdAt;
    protected Instant updatedAt;
    protected boolean eliminado;
    protected Integer version;
    
    protected Pregunta() {}
    
    protected Pregunta(Long id, String textoPregunta, TipoPregunta tipoPregunta, Difficulty dificultad,
                       int puntajeMaximo, Long temaId, List<Media> medios, String pista, String explicacion,
                       List<String> etiquetas, Map<String, Object> metadatos) {
        if (textoPregunta == null || textoPregunta.isBlank()) {
            throw new IllegalArgumentException("El texto de la pregunta no puede ser nulo o vacío");
        }
        if (puntajeMaximo <= 0) {
            throw new IllegalArgumentException("El puntaje máximo debe ser mayor a 0");
        }
        
        this.id = id;
        this.textoPregunta = textoPregunta;
        this.tipoPregunta = tipoPregunta;
        this.dificultad = dificultad != null ? dificultad : Difficulty.BASIC;
        this.puntajeMaximo = puntajeMaximo;
        this.temaId = temaId;
        this.medios = medios;
        this.pista = pista;
        this.explicacion = explicacion;
        this.etiquetas = etiquetas;
        this.metadatos = metadatos;
        this.createdAt = Instant.now();
        this.eliminado = false;
        this.version = 1;
    }
    
    @Override
    public Long getId() {
        return id;
    }
    
    public String getTextoPregunta() {
        return textoPregunta;
    }
    
    public TipoPregunta getTipoPregunta() {
        return tipoPregunta;
    }
    
    public Difficulty getDificultad() {
        return dificultad;
    }
    
    public int getPuntajeMaximo() {
        return puntajeMaximo;
    }
    
    public Long getTemaId() {
        return temaId;
    }
    
    public List<Media> getMedios() {
        return medios;
    }
    
    public String getPista() {
        return pista;
    }
    
    public String getExplicacion() {
        return explicacion;
    }
    
    public List<String> getEtiquetas() {
        return etiquetas;
    }
    
    public Map<String, Object> getMetadatos() {
        return metadatos;
    }
    
    public Instant getCreatedAt() {
        return createdAt;
    }
    
    public Instant getUpdatedAt() {
        return updatedAt;
    }
    
    public boolean isEliminado() {
        return eliminado;
    }
    
    public Integer getVersion() {
        return version;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setTextoPregunta(String textoPregunta) {
        this.textoPregunta = textoPregunta;
        this.updatedAt = Instant.now();
    }
    
    public void setDificultad(Difficulty dificultad) {
        this.dificultad = dificultad;
        this.updatedAt = Instant.now();
    }
    
    public void setPuntajeMaximo(int puntajeMaximo) {
        this.puntajeMaximo = puntajeMaximo;
        this.updatedAt = Instant.now();
    }
    
    public void setTemaId(Long temaId) {
        this.temaId = temaId;
        this.updatedAt = Instant.now();
    }
    
    public void setMedios(List<Media> medios) {
        this.medios = medios;
        this.updatedAt = Instant.now();
    }
    
    public void setPista(String pista) {
        this.pista = pista;
        this.updatedAt = Instant.now();
    }
    
    public void setExplicacion(String explicacion) {
        this.explicacion = explicacion;
        this.updatedAt = Instant.now();
    }
    
    public void setEtiquetas(List<String> etiquetas) {
        this.etiquetas = etiquetas;
        this.updatedAt = Instant.now();
    }
    
    public void setMetadatos(Map<String, Object> metadatos) {
        this.metadatos = metadatos;
        this.updatedAt = Instant.now();
    }
    
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    
    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }
    
    public void setVersion(Integer version) {
        this.version = version;
    }
    
    public void marcarComoEliminado() {
        this.eliminado = true;
        this.updatedAt = Instant.now();
    }
    
    public void incrementarVersion() {
        this.version = (this.version != null ? this.version : 0) + 1;
        this.updatedAt = Instant.now();
    }
    
    public abstract void validar();
}
