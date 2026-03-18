package com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.entity;

import com.kleverkids.formacion_academica.shared.common.domain.entity.AuditInfo;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@Entity
@Table(name = "estado_historial")
@EqualsAndHashCode(callSuper = false)
public class EstadoHistorialEntity extends AuditInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid", nullable = false, unique = true, length = 36)
    private String uuid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_entidad_estado", nullable = false)
    private EntidadEstadoEntity entidadEstado;

    @Column(name = "entidad_tipo", nullable = false, length = 50)
    private String entidadTipo;

    @Column(name = "entidad_id", nullable = false)
    private Long entidadId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_estado_anterior")
    private EstadoEntity estadoAnterior;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_estado_nuevo", nullable = false)
    private EstadoEntity estadoNuevo;

    @Column(name = "id_empresa")
    private Long idEmpresa;

    @Column(name = "id_usuario_cambio")
    private Long idUsuarioCambio;

    @Column(length = 500)
    private String motivo;

    @Column(columnDefinition = "JSON")
    private String metadata;

    @PrePersist
    public void prePersist() {
        if (uuid == null) {
            uuid = UUID.randomUUID().toString();
        }
    }
}
