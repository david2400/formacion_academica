package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.examenes;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta.ResultadoPreguntaEmbeddable;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
@Entity
@Table(name = "exam_results")
public class ResultadoExamenEntity {
    
    @Id
    private Long id;
    
    @Column(name = "exam_id", nullable = false)
    private Long examenId;
    
    @Column(name = "student_id", nullable = false)
    private Long estudianteId;
    
    @Column(name = "submission_id", nullable = false)
    private Long envioId;
    
    @Column(name = "total_score", precision = 10, scale = 2)
    private BigDecimal puntajeTotal;
    
    @Column(name = "max_score", precision = 10, scale = 2)
    private BigDecimal puntajeMaximo;
    
    @Column(name = "percentage", precision = 5, scale = 2)
    private BigDecimal porcentaje;
    
    private String calificacion;
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "question_results", columnDefinition = "json")
    private List<ResultadoPreguntaEmbeddable> resultadosPreguntas;
    
    @Column(name = "graded_at")
    private Instant fechaCalificacion;
    
    @Column(name = "graded_by")
    private Long calificadoPor;
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getExamenId() { return examenId; }
    public void setExamenId(Long examenId) { this.examenId = examenId; }
    
    public Long getEstudianteId() { return estudianteId; }
    public void setEstudianteId(Long estudianteId) { this.estudianteId = estudianteId; }
    
    public Long getEnvioId() { return envioId; }
    public void setEnvioId(Long envioId) { this.envioId = envioId; }
    
    public BigDecimal getPuntajeTotal() { return puntajeTotal; }
    public void setPuntajeTotal(BigDecimal puntajeTotal) { this.puntajeTotal = puntajeTotal; }
    
    public BigDecimal getPuntajeMaximo() { return puntajeMaximo; }
    public void setPuntajeMaximo(BigDecimal puntajeMaximo) { this.puntajeMaximo = puntajeMaximo; }
    
    public BigDecimal getPorcentaje() { return porcentaje; }
    public void setPorcentaje(BigDecimal porcentaje) { this.porcentaje = porcentaje; }
    
    public String getCalificacion() { return calificacion; }
    public void setCalificacion(String calificacion) { this.calificacion = calificacion; }
    
    public List<ResultadoPreguntaEmbeddable> getResultadosPreguntas() { return resultadosPreguntas; }
    public void setResultadosPreguntas(List<ResultadoPreguntaEmbeddable> resultadosPreguntas) { this.resultadosPreguntas = resultadosPreguntas; }
    
    public Instant getFechaCalificacion() { return fechaCalificacion; }
    public void setFechaCalificacion(Instant fechaCalificacion) { this.fechaCalificacion = fechaCalificacion; }
    
    public Long getCalificadoPor() { return calificadoPor; }
    public void setCalificadoPor(Long calificadoPor) { this.calificadoPor = calificadoPor; }
}
