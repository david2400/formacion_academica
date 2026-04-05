package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.EstudianteExamenEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "respuestas_criterio_examen")
public class RespuestaCriterioEntity {

    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "estudiante_examen_id", nullable = false, updatable = false, insertable = false)
    private EstudianteExamenEntity estudianteExamen;

    @Column(nullable = false)
    private Long examenId;

    @Column(nullable = false)
    private Long criterioId;

    @Column(nullable = false)
    private Long estudianteId;

    @Column(nullable = false)
    private String respuesta;

    private BigDecimal puntajeObtenido;

    @Column(nullable = false)
    private LocalDateTime registradaEn;


}
