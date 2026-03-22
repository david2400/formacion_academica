package com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.List;


public class ResultadoExamen {
    
    private Long id;
    private Long examenId;
    private Long estudianteId;
    private Long envioId;
    private BigDecimal puntajeTotal;
    private BigDecimal puntajeMaximo;
    private BigDecimal porcentaje;
    private String calificacion;
    private List<ResultadoPregunta> resultadosPreguntas;
    private Instant calificadoEn;
    private Long calificadoPor;
    
    public ResultadoExamen() {}
    
    public ResultadoExamen(Long id, Long examenId, Long estudianteId, Long envioId,
                          BigDecimal puntajeTotal, BigDecimal puntajeMaximo, List<ResultadoPregunta> resultadosPreguntas) {
        this.id = id ;
        this.examenId = examenId;
        this.estudianteId = estudianteId;
        this.envioId = envioId;
        this.puntajeTotal = puntajeTotal;
        this.puntajeMaximo = puntajeMaximo;
        this.resultadosPreguntas = resultadosPreguntas;
        this.calificadoEn = Instant.now();
        calcularPorcentaje();
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getExamenId() { return examenId; }
    public void setExamenId(Long examenId) { this.examenId = examenId; }
    
    public Long getEstudianteId() { return estudianteId; }
    public void setEstudianteId(Long estudianteId) { this.estudianteId = estudianteId; }
    
    public Long getEnvioId() { return envioId; }
    public void setEnvioId(Long envioId) { this.envioId = envioId; }
    
    public BigDecimal getPuntajeTotal() { return puntajeTotal; }
    public void setPuntajeTotal(BigDecimal puntajeTotal) { 
        this.puntajeTotal = puntajeTotal; 
        calcularPorcentaje();
    }
    
    public BigDecimal getPuntajeMaximo() { return puntajeMaximo; }
    public void setPuntajeMaximo(BigDecimal puntajeMaximo) { 
        this.puntajeMaximo = puntajeMaximo; 
        calcularPorcentaje();
    }
    
    public BigDecimal getPorcentaje() { return porcentaje; }
    
    public String getCalificacion() { return calificacion; }
    public void setCalificacion(String calificacion) { this.calificacion = calificacion; }
    
    public List<ResultadoPregunta> getResultadosPreguntas() { return resultadosPreguntas; }
    public void setResultadosPreguntas(List<ResultadoPregunta> resultadosPreguntas) { this.resultadosPreguntas = resultadosPreguntas; }
    
    public Instant getCalificadoEn() { return calificadoEn; }
    public void setCalificadoEn(Instant calificadoEn) { this.calificadoEn = calificadoEn; }
    
    public Long getCalificadoPor() { return calificadoPor; }
    public void setCalificadoPor(Long calificadoPor) { this.calificadoPor = calificadoPor; }
    
    private void calcularPorcentaje() {
        if (puntajeTotal != null && puntajeMaximo != null && puntajeMaximo.compareTo(BigDecimal.ZERO) > 0) {
            this.porcentaje = puntajeTotal
                .multiply(BigDecimal.valueOf(100))
                .divide(puntajeMaximo, 2, RoundingMode.HALF_UP);
        }
    }
    
    // Métodos de compatibilidad para el servicio
    @Deprecated
    public BigDecimal getPercentage() {
        return porcentaje;
    }
    
    @Deprecated
    public void setTotalScore(BigDecimal totalScore) {
        this.puntajeTotal = totalScore;
    }
    
    // Alias para compatibilidad temporal durante la conversión
    @Deprecated
    public static class QuestionResult extends ResultadoPregunta {
        public QuestionResult(Long preguntaId, BigDecimal puntaje, BigDecimal puntajeMaximo, boolean correcto, String retroalimentacion) {
            super(preguntaId, puntaje, puntajeMaximo, correcto, retroalimentacion);
        }
    }
    
    public static class ResultadoPregunta {
        private Long preguntaId;
        private BigDecimal puntaje;
        private BigDecimal puntajeMaximo;
        private boolean correcto;
        private String retroalimentacion;
        
        public ResultadoPregunta() {}
        
        public ResultadoPregunta(Long preguntaId, BigDecimal puntaje, BigDecimal puntajeMaximo, boolean correcto, String retroalimentacion) {
            this.preguntaId = preguntaId;
            this.puntaje = puntaje;
            this.puntajeMaximo = puntajeMaximo;
            this.correcto = correcto;
            this.retroalimentacion = retroalimentacion;
        }
        
        public Long getPreguntaId() { return preguntaId; }
        public void setPreguntaId(Long preguntaId) { this.preguntaId = preguntaId; }
        
        public BigDecimal getPuntaje() { return puntaje; }
        public void setPuntaje(BigDecimal puntaje) { this.puntaje = puntaje; }
        
        public BigDecimal getPuntajeMaximo() { return puntajeMaximo; }
        public void setPuntajeMaximo(BigDecimal puntajeMaximo) { this.puntajeMaximo = puntajeMaximo; }
        
        public boolean isCorrecto() { return correcto; }
        public void setCorrecto(boolean correcto) { this.correcto = correcto; }
        
        public String getRetroalimentacion() { return retroalimentacion; }
        public void setRetroalimentacion(String retroalimentacion) { this.retroalimentacion = retroalimentacion; }
    }
}
