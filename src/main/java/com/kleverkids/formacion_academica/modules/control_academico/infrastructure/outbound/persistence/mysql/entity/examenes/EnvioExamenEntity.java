package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.examenes;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta.RespuestaPreguntaEmbeddable;
import com.kleverkids.formacion_academica.shared.common.domain.entity.AuditInfo;
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
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "exam_submissions")
public class EnvioExamenEntity extends AuditInfo {
    
    @Id
    private Long id;
    
    @Column(name = "exam_id", nullable = false)
    private Long examenId;
    
    @Column(name = "student_id", nullable = false)
    private Long estudianteId;
    
    @Column(name = "started_at")
    private Instant fechaInicio;
    
    @Column(name = "submitted_at")
    private Instant fechaEnvio;
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "answers", columnDefinition = "json")
    private List<RespuestaPreguntaEmbeddable> respuestas;
    
    @Column(nullable = false)
    private String estado;
    
    @Column(name = "total_score", precision = 10, scale = 2)
    private BigDecimal puntajeTotal;
    
    @Column(name = "graded")
    private boolean calificado;
}
