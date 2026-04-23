package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.asignacion_examen;

import com.kleverkids.formacion_academica.shared.common.domain.entity.AuditInfo;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(
    name = "asignaciones_examenes",
    indexes = {
        @Index(name = "idx_examen_id", columnList = "examen_id"),
        @Index(name = "idx_clase_id", columnList = "clase_id"),
        @Index(name = "idx_estado", columnList = "estado"),
        @Index(name = "idx_fecha_inicio", columnList = "fecha_inicio"),
        @Index(name = "idx_examen_clase", columnList = "examen_id, clase_id")
    },
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_examen_clase_activa", columnNames = {"examen_id", "clase_id", "estado"})
    }
)
public class AsignacionExamenEntity extends AuditInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "examen_id", nullable = false)
    private Long examenId;

    @Column(name = "clase_id", nullable = false)
    private Long claseId;

    @Column(name = "grado", length = 50)
    private String grado;

    @Column(name = "grupo", length = 50)
    private String grupo;

    @Column(name = "fecha_asignacion", nullable = false)
    private LocalDate fechaAsignacion;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDateTime fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDateTime fechaFin;

    @Column(name = "duracion_minutos")
    private Integer duracionMinutos;

    @Column(name = "intentos_permitidos", nullable = false)
    private Integer intentosPermitidos;

    @Column(name = "mostrar_resultados_inmediatos", nullable = false)
    private Boolean mostrarResultadosInmediatos;

    @Column(name = "permitir_revision", nullable = false)
    private Boolean permitirRevision;

    @Column(name = "estado", nullable = false, length = 20)
    private String estado; // PROGRAMADA, ACTIVA, FINALIZADA, CANCELADA

    @PrePersist
    protected void onCreate() {
        if (estado == null) {
            estado = "PROGRAMADA";
        }
        if (mostrarResultadosInmediatos == null) {
            mostrarResultadosInmediatos = false;
        }
        if (permitirRevision == null) {
            permitirRevision = true;
        }
        if (intentosPermitidos == null) {
            intentosPermitidos = 1;
        }
    }
}
