package com.kleverkids.formacion_academica.modules.control_academico.domain.model.respuesta_pregunta;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public class RespuestaPregunta {
    private Long id;
    private Long estudianteExamenId;
    private Long examenId;
    private Long estudianteId;
    private Long preguntaId;
    private Long respuestaBancoId;
    private String respuestaTexto;
    private Boolean esCorrecta;
    private Integer puntajeObtenido;
    private LocalDateTime registradaEn;
    private BigDecimal puntaje;
    private String retroalimentacion;
    
    public RespuestaPregunta() {}
    
    public RespuestaPregunta(Long id, Long preguntaId, Object respuesta) {
        this.id = id;
        this.preguntaId = preguntaId;
        // Convertir respuesta al formato apropiado
        if (respuesta instanceof String) {
            this.respuestaTexto = (String) respuesta;
        } else if (respuesta instanceof BigDecimal) {
            this.puntaje = (BigDecimal) respuesta;
        }
        this.registradaEn = LocalDateTime.now();
    }
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getEstudianteExamenId() { return estudianteExamenId; }
    public void setEstudianteExamenId(Long estudianteExamenId) { this.estudianteExamenId = estudianteExamenId; }
    
    public Long getExamenId() { return examenId; }
    public void setExamenId(Long examenId) { this.examenId = examenId; }
    
    public Long getEstudianteId() { return estudianteId; }
    public void setEstudianteId(Long estudianteId) { this.estudianteId = estudianteId; }
    
    public Long getPreguntaId() { return preguntaId; }
    public void setPreguntaId(Long preguntaId) { this.preguntaId = preguntaId; }
    
    public Long getRespuestaBancoId() { return respuestaBancoId; }
    public void setRespuestaBancoId(Long respuestaBancoId) { this.respuestaBancoId = respuestaBancoId; }
    
    public String getRespuestaTexto() { return respuestaTexto; }
    public void setRespuestaTexto(String respuestaTexto) { this.respuestaTexto = respuestaTexto; }
    
    public Boolean getEsCorrecta() { return esCorrecta; }
    public void setEsCorrecta(Boolean esCorrecta) { this.esCorrecta = esCorrecta; }
    
    public Integer getPuntajeObtenido() { return puntajeObtenido; }
    public void setPuntajeObtenido(Integer puntajeObtenido) { this.puntajeObtenido = puntajeObtenido; }
    
    public LocalDateTime getRegistradaEn() { return registradaEn; }
    public void setRegistradaEn(LocalDateTime registradaEn) { this.registradaEn = registradaEn; }
    
    public BigDecimal getPuntaje() { return puntaje; }
    public void setPuntaje(BigDecimal puntaje) { this.puntaje = puntaje; }
    
    public String getRetroalimentacion() { return retroalimentacion; }
    public void setRetroalimentacion(String retroalimentacion) { this.retroalimentacion = retroalimentacion; }
    
    // Métodos de compatibilidad
    public void grade(BigDecimal puntaje, String retroalimentacion) {
        this.puntaje = puntaje;
        this.retroalimentacion = retroalimentacion;
        this.puntajeObtenido = puntaje.intValue();
        this.esCorrecta = puntaje.compareTo(BigDecimal.ZERO) > 0;
    }
}
