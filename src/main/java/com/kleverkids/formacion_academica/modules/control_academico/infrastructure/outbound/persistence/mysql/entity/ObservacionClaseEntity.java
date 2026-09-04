package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity;

import com.kleverkids.formacion_academica.shared.common.domain.entity.AuditInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Observación puntual sobre una clase. Es una bitácora: cada anotación queda
 * registrada con su fecha y su autor, y nunca se sobrescribe la anterior.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "clase_observaciones")
public class ObservacionClaseEntity extends AuditInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "observacion", columnDefinition = "TEXT", nullable = false)
    private String observacion;

}
