package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity;

import com.kleverkids.formacion_academica.shared.common.domain.entity.AuditInfo;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
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

    @Column(name = "tiene_proyector")
    private Boolean tieneProyector;

    @Column(name = "tiene_pizarron_blanco")
    private Boolean tienePizarronBlanco;

    @Column(name = "tiene_aire_acondicionado")
    private Boolean tieneAireAcondicionado;

    @Column(name = "nombre_edificio", nullable = false)
    private String nombreEdificio;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    // Relación con sede
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sede_id", nullable = false)
    private SedeEntity sede;
}
