package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity;

import com.kleverkids.formacion_academica.shared.common.domain.entity.AuditInfo;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@NoArgsConstructor
@Entity
@Table(name = "salones")
@EqualsAndHashCode(callSuper = true, exclude = {"sede"})
@ToString(exclude = {"sede"})
public class SalonEntity extends AuditInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String codigo;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

    @Column(name = "capacidad_maxima", nullable = false)
    private Integer capacidadMaxima;

    @Column(name = "numero_piso")
    private Integer numeroPiso;

    @Column(name = "proyector")
    private Boolean proyector;

    @Column(name = "pizarron_blanco")
    private Boolean pizarronBlanco;

    @Column(name = "aire_acondicionado")
    private Boolean aireAcondicionado;

    @Column(name = "sede_id", nullable = false)
    private Long sedeId;

    // Relación con sede
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sede_id", nullable = false, updatable = false, insertable = false)
    private SedeEntity sede;
}
