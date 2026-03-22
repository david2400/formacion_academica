package com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.respuesta_pregunta.RespuestaPregunta;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


public class EnvioExamen {
    
    private Long id;
    private Long examenId;
    private Long estudianteId;
    private Instant iniciadoEn;
    private Instant enviadoEn;
    private List<RespuestaPregunta> respuestas;
    private EstadoEnvio estado;
    private BigDecimal puntajeTotal;
    private boolean calificado;
    
    public EnvioExamen() {
        this.respuestas = new ArrayList<>();
        this.estado = EstadoEnvio.EN_PROGRESO;
    }
    
    public EnvioExamen(Long id, Long examenId, Long estudianteId) {
        this.id = id ;
        this.examenId = examenId;
        this.estudianteId = estudianteId;
        this.iniciadoEn = Instant.now();
        this.respuestas = new ArrayList<>();
        this.estado = EstadoEnvio.EN_PROGRESO;
        this.calificado = false;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getExamenId() { return examenId; }
    public void setExamenId(Long examenId) { this.examenId = examenId; }
    
    public Long getEstudianteId() { return estudianteId; }
    public void setEstudianteId(Long estudianteId) { this.estudianteId = estudianteId; }
    
    public Instant getIniciadoEn() { return iniciadoEn; }
    public void setIniciadoEn(Instant iniciadoEn) { this.iniciadoEn = iniciadoEn; }
    
    public Instant getEnviadoEn() { return enviadoEn; }
    public void setEnviadoEn(Instant enviadoEn) { this.enviadoEn = enviadoEn; }
    
    public List<RespuestaPregunta> getRespuestas() { return respuestas; }
    public void setRespuestas(List<RespuestaPregunta> respuestas) { this.respuestas = respuestas; }
    
    public EstadoEnvio getEstado() { return estado; }
    public void setEstado(EstadoEnvio estado) { this.estado = estado; }
    
    public BigDecimal getPuntajeTotal() { return puntajeTotal; }
    public void setPuntajeTotal(BigDecimal puntajeTotal) { this.puntajeTotal = puntajeTotal; }
    
    public boolean isCalificado() { return calificado; }
    public void setCalificado(boolean calificado) { this.calificado = calificado; }
    
    public void agregarRespuesta(RespuestaPregunta respuesta) {
        this.respuestas.add(respuesta);
    }
    
    public void entregar() {
        if (this.estado != EstadoEnvio.EN_PROGRESO) {
            throw new IllegalStateException("No se puede entregar - no está en progreso");
        }
        this.enviadoEn = Instant.now();
        this.estado = EstadoEnvio.ENTREGADO;
    }
    
    public void calificar(BigDecimal puntaje) {
        if (this.estado != EstadoEnvio.ENTREGADO) {
            throw new IllegalStateException("No se puede calificar - no ha sido entregado");
        }
        this.puntajeTotal = puntaje;
        this.calificado = true;
        this.estado = EstadoEnvio.CALIFICADO;
    }
    
    // Métodos de compatibilidad para el servicio
    @Deprecated
    public void addAnswer(RespuestaPregunta respuesta) {
        agregarRespuesta(respuesta);
    }
    
    @Deprecated
    public void submit() {
        entregar();
    }
    
    @Deprecated
    public void grade(BigDecimal puntaje) {
        calificar(puntaje);
    }
    
    @Deprecated
    public List<RespuestaPregunta> getAnswers() {
        return getRespuestas();
    }
    
    @Deprecated
    public Long getStudentId() {
        return getEstudianteId();
    }
    
    public enum EstadoEnvio {
        EN_PROGRESO,
        ENTREGADO,
        CALIFICADO
    }
}
