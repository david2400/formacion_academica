package com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.entity;

import com.kleverkids.formacion_academica.shared.common.domain.entity.AuditInfo;
import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.entity.EstadoEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "entidad_estados")
@EqualsAndHashCode(callSuper = false)
public class EntidadEstadoEntity extends AuditInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid", nullable = false, unique = true, length = 36)
    private String uuid;

    @Column(name = "entidad_tipo", nullable = false, length = 50)
    private String entidadTipo;

    @Column(name = "entidad_id", nullable = false)
    private Long entidadId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_estado", nullable = false)
    private EstadoEntity estado;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_estado_anterior")
    private EstadoEntity estadoAnterior;

    @Column(name = "id_empresa")
    private Long idEmpresa;

    @Column(name = "id_usuario_cambio")
    private Long idUsuarioCambio;

    @Column(length = 500)
    private String motivo;

    @Column(columnDefinition = "JSON")
    private String metadata;

    @Column(nullable = false)
    private Boolean activo = true;

    @OneToMany(mappedBy = "entidadEstado", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<EstadoHistorialEntity> historial;

    @PrePersist
    public void prePersist() {
        if (uuid == null) {
            uuid = UUID.randomUUID().toString();
        }
    }
}
