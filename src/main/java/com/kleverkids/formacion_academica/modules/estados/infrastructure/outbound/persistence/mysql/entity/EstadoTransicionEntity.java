package com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.entity;

import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.entity.EstadoEntity;
import com.kleverkids.formacion_academica.shared.common.domain.entity.AuditInfo;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@Entity
@Table(name = "estado_transiciones")
@EqualsAndHashCode(callSuper = false)
public class EstadoTransicionEntity extends AuditInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid", nullable = false, unique = true, length = 36)
    private String uuid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_estado_origen", nullable = false, updatable = false, insertable = false)
    private EstadoEntity estadoOrigen;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_estado_destino", nullable = false, updatable = false, insertable = false)
    private EstadoEntity estadoDestino;

    @Column(name = "id_modulo", nullable = false)
    private Long idModulo;

    @Column(name = "id_empresa")
    private Long idEmpresa;

    @Column(length = 500)
    private String condicion;

    @Column(length = 500)
    private String accion;

    @Column(nullable = false)
    private Boolean automatica = false;

    @Column(nullable = false)
    private Integer orden = 0;

    @Column(nullable = false)
    private Boolean activa = true;

    @Column(columnDefinition = "JSON")
    private String metadata;

    @PrePersist
    public void prePersist() {
        if (uuid == null) {
            uuid = UUID.randomUUID().toString();
        }
    }
}
