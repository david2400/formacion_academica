package com.kleverkids.formacion_academica.modules.usuarios.infrastructure.outbound.persistence.mysql.entity;

import com.kleverkids.formacion_academica.shared.common.domain.entity.AuditInfo;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

/**
 * Sin datos personales: la persona vive en access_control y se referencia por {@code usuario_id}.
 * Índices: único (usuario_id, empresa_id) y (empresa_id, activo) para el listado por empresa.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "profesores",
        uniqueConstraints = @UniqueConstraint(name = "uk_profesores_usuario_empresa", columnNames = {"usuario_id", "empresa_id"}),
        indexes = @Index(name = "idx_profesores_empresa_activo", columnList = "empresa_id, activo"))
public class ProfesorEntity extends AuditInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Column(name = "empresa_id", nullable = false)
    private Long empresaId;

    @Column(name = "activo", nullable = false)
    private boolean activo;

    @Column(name = "fecha_vinculacion")
    private LocalDate fechaVinculacion;

    @Column(name = "observaciones", length = 500)
    private String observaciones;
}
