package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity;

import com.kleverkids.formacion_academica.shared.common.domain.entity.AuditInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.UUID;


@Data
@Entity
@Table(name = "grupos")
public class GrupoEntity extends AuditInfo {

    @Id
    private UUID id;

    @Column(nullable = false, unique = true)
    private String codigo;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private UUID gradoId;

    private Integer capacidadMaxima;

    private UUID tutorId;

    private UUID aulaId;

    @Column(nullable = false)
    private boolean activo;

}
