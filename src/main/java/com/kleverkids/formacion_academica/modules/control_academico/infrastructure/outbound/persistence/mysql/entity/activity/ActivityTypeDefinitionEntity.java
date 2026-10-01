package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.activity;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_type.valueobject.CategoriaTipoActividad;
import com.kleverkids.formacion_academica.shared.common.domain.entity.AuditInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

/**
 * Registro extensible de tipos de actividad. Reemplaza al enum fijo que tenía
 * la Actividad anterior (ver arquitectura aprobada, decisión §2.1).
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "activity_type_definition")
public class ActivityTypeDefinitionEntity extends AuditInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type", nullable = false, unique = true, length = 60)
    private String type;

    @Column(name = "name", nullable = false)
    private String nombre;

    @Column(name = "description")
    private String descripcion;

    @Column(name = "icon", length = 60)
    private String icono;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", length = 20)
    private CategoriaTipoActividad categoria;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "configuration_schema", columnDefinition = "json")
    private Map<String, Object> configuracionSchema;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "capabilities", columnDefinition = "json")
    private Map<String, Object> capacidades;

    @Column(name = "activo", nullable = false)
    private boolean activo;
}
