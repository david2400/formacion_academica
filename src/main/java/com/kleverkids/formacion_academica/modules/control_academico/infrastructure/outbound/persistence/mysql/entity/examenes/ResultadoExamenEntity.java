package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.examenes;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta.ResultadoPreguntaEmbeddable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
@EqualsAndHashCode
@Entity
@SuperBuilder
@NoArgsConstructor
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
}
