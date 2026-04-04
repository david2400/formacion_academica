package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity;

import com.kleverkids.formacion_academica.shared.common.domain.entity.AuditInfo;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "niveles_educativos")
public class NivelEducativoEntity extends AuditInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String codigo;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

//    @Column(nullable = false)
//    private Integer orden;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "nivel_superior_id")
    private NivelEducativoEntity nivelSuperior;

//    @Column(nullable = false)
//    private String categoria; // PREESCOLAR, BASICA, MEDIA, SUPERIOR
}
