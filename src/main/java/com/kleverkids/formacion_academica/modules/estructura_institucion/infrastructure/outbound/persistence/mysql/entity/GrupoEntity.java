package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity;

import com.kleverkids.formacion_academica.shared.common.domain.entity.AuditInfo;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@SuperBuilder
@Data
@Entity
@Table(name = "grupos")
@NoArgsConstructor
public class GrupoEntity extends AuditInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "codigo", nullable = false, unique = true)
    private String codigo;

    @Column(name = "grado_id", nullable = false)
    private Long gradoId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "grado_id", nullable = false, insertable = false, updatable = false)
    private GradoEntity grado;

    @Column(name = "salon_id")
    private Long salonId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "salon_id", insertable = false, updatable = false)
    private SalonEntity salon;

    @Column(name = "capacidad_maxima")
    private Integer capacidadMaxima;

    @Column(name = "periodo_academico")
    private String periodoAcademico;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Column(name = "tutor_id")
    private Long tutorId;

    @Column(name = "estado_id")
    private Integer estadoId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "grupo_aula", joinColumns = @JoinColumn(name = "grupo_id"), inverseJoinColumns = @JoinColumn(name = "aula_id"))
    private Set<AulaEntity> aulas = new HashSet<>();

}
