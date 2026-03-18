package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity;

import com.kleverkids.formacion_academica.shared.common.domain.entity.AuditInfo;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "grupos")
public class GrupoEntity extends AuditInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String codigo;

    @Column(nullable = false)
    private String nombre;

    @Column(name = "grado_id", nullable = false)
    private Long gradoId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "grado_id", nullable = false, insertable = false, updatable = false)
    private GradoEntity grado;

    @Column(name = "salon_id", nullable = false)
    private Long salonId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "salon_id", nullable = false, insertable = false, updatable = false)
    private SalonEntity salon;

    private Integer capacidadMaxima;

    private Long tutorId;

    @Column(nullable = false)
    private boolean estado;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "grupo_aula",
            joinColumns = @JoinColumn(name = "grupo_id"),
            inverseJoinColumns = @JoinColumn(name = "aula_id")
    )
    private Set<AulaEntity> aulas = new HashSet<>();

}
