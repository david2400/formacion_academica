package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.persistence.mysql.entity.EstudianteEntity;
import com.kleverkids.formacion_academica.shared.common.domain.entity.AuditInfo;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@SuperBuilder
@Data
@Entity
@Table(name = "estudiantes_grupo")
@NoArgsConstructor
public class EstudianteGrupoEntity extends AuditInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "estudiante_id", nullable = false)
    private Long estudianteId;

    // Relaciones opcionales para consultas (no afectan persistencia)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "estudiante_id", nullable = false, insertable = false, updatable = false)
    private EstudianteEntity estudiante;

    @Column(name = "grupo_id", nullable = false)
    private Long grupoId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "grupo_id", nullable = false, insertable = false, updatable = false)
    private GrupoEntity grupo;

    private LocalDate fechaAsignacion;

    // Relación con EstadoEntity eliminada para evitar conflictos entre módulos
    // Se usa estadoLegacy (String) para mantener compatibilidad
    // @ManyToOne(fetch = FetchType.EAGER)
    // @JoinColumn(name = "estado_id", nullable = false)
    // private EstadoEntity estado;

    // Campo temporal para compatibilidad durante migración
    @Column(name = "estado_legacy", nullable = false)
    private String estadoLegacy;
}
