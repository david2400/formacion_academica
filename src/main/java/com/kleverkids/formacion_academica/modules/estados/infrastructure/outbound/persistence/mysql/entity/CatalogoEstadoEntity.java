package com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Catálogo de estados de la aplicación.
 *
 * <p>Un estado existe <b>una sola vez</b> y se reutiliza en todos los contextos que
 * lo necesiten: {@code activo} es la misma fila para una matrícula, un grupo y una
 * asignación estudiante-grupo. Aquí no hay noción de a quién pertenece; eso vive en
 * {@link CatalogoEstadoContextoEntity}.
 *
 * <p>{@code codigo} es el identificador estable con el que los clientes razonan. El
 * {@code id} lo genera la base de datos y cambia entre entornos.
 */
@Data
@Entity
@Table(name = "catalogo_estados", uniqueConstraints = {
        @UniqueConstraint(name = "uk_catalogo_estado_codigo", columnNames = { "codigo" })
})
public class CatalogoEstadoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado", nullable = false, updatable = false)
    private Long idEstado;

    @Column(name = "codigo", nullable = false, length = 60)
    private String codigo;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "descripcion", length = 500)
    private String descripcion;

    /** Color hexadecimal para la UI. Ej: {@code #2563eb}. */
    @Column(name = "color", length = 7)
    private String color;

    @Column(name = "icono", length = 50)
    private String icono;

    @Column(name = "orden", nullable = false)
    private Integer orden = 0;
}
