package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity;

import com.kleverkids.formacion_academica.shared.common.domain.entity.AuditInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "estudiantes_grupo")
public class EstudianteGrupoEntity extends AuditInfo {

    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID estudianteId;

    @Column(nullable = false)
    private UUID grupoId;

    private LocalDate fechaAsignacion;

    @Column(nullable = false)
    private String estado;


}
