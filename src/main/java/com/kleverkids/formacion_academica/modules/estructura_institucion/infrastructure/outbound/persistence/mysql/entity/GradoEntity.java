package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity;

import com.kleverkids.formacion_academica.shared.common.domain.entity.AuditInfo;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "grados")
public class GradoEntity extends AuditInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(name = "nivel_educativo_id", nullable = false)
    private Long nivelEducativoId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "nivel_educativo_id", nullable = false, insertable = false, updatable = false)
    private NivelEducativoEntity nivelEducativo;

    private Integer orden;


}
