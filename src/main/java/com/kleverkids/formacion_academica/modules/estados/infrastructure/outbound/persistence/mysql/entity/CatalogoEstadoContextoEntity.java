package com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Parametrización: qué estado del catálogo aplica a qué contexto.
 *
 * <p>Es la tabla que se toca para habilitar o quitar estados sin desplegar código.
 * Apunta al contexto por llave foránea, no por texto libre.
 *
 * <p>{@code esInicial}, {@code esFinal} y {@code orden} viven aquí y no en el
 * catálogo porque dependen del contexto: {@code activo} es inicial para una
 * asignación a grupo, pero puede no serlo en otro contexto.
 *
 * <p>{@code idEmpresa} = 0 es parametrización global; otro valor permite que cada
 * empresa habilite su propio subconjunto.
 */
@Data
@Entity
@Table(name = "catalogo_estado_contextos", uniqueConstraints = {
        @UniqueConstraint(name = "uk_catalogo_contexto_estado", columnNames = { "contexto_id", "estado_id",
                "id_empresa" })
}, indexes = {
        @Index(name = "idx_catalogo_contexto_lookup", columnList = "contexto_id, id_empresa, orden")
})
public class CatalogoEstadoContextoEntity {

    public static final Long EMPRESA_GLOBAL = 0L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_parametrizacion", nullable = false, updatable = false)
    private Long idParametrizacion;

    @Column(name = "contexto_id", nullable = false)
    private Long contextoId;

    @Column(name = "estado_id", nullable = false)
    private Long estadoId;

    /** Estado que se asigna automáticamente al crear una entidad de este contexto. */
    @Column(name = "es_inicial", nullable = false)
    private Boolean esInicial = false;

    /** Estado terminal dentro de este contexto. */
    @Column(name = "es_final", nullable = false)
    private Boolean esFinal = false;

    @Column(name = "orden", nullable = false)
    private Integer orden = 0;

    @Column(name = "id_empresa", nullable = false)
    private Long idEmpresa = EMPRESA_GLOBAL;
}
