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

/** Catálogo de tipos de clase (magistral, taller, refuerzo, evaluación...). */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "tipos_clase")
public class TipoClaseEntity extends AuditInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    /** Color para distinguirlo en el calendario (hex, ej. #2563eb). */
    @Column(name = "color", length = 20)
    private String color;

}
