package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity;

import com.kleverkids.formacion_academica.shared.common.domain.entity.AuditInfo;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

/**
 * Asignación de un estudiante a un grupo.
 *
 * <p>{@code estadoId} replica el estado que decide el motor de access_control para
 * la máquina {@code ASIGNACION_GRUPO_LIFECYCLE}. No es un enum a propósito: los
 * estados y sus transiciones se configuran allí, sin desplegar este servicio.
 *
 * <p>No declara relaciones a {@code EstudianteEntity} ni {@code GrupoEntity}: los
 * nombres se resuelven en el cliente a partir de sus propios catálogos. Evita
 * acoplar módulos y el coste de traer dos entidades por fila en cada listado.
 */
@SuperBuilder
@Data
@Entity
@Table(name = "estudiantes_grupo", uniqueConstraints = {
        @UniqueConstraint(name = "uk_estudiante_grupo", columnNames = { "estudiante_id", "grupo_id" })
}, indexes = {
        @Index(name = "idx_estudiante_grupo_grupo", columnList = "grupo_id"),
        @Index(name = "idx_estudiante_grupo_estudiante", columnList = "estudiante_id")
})
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class EstudianteGrupoEntity extends AuditInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "estudiante_id", nullable = false)
    private Long estudianteId;

    @Column(name = "grupo_id", nullable = false)
    private Long grupoId;

    @Column(name = "fecha_asignacion", nullable = false)
    private LocalDate fechaAsignacion;

    /** Estado en ASIGNACION_GRUPO_LIFECYCLE. Réplica de {@code security.state.id_state}: sin FK, es otra base. */
    @Column(name = "estado_id", nullable = false)
    private Long estadoId;
}
