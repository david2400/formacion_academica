package com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Registro de contextos: qué contextos existen dentro de esta aplicación.
 *
 * <p>Un contexto no es un texto libre sino una fila registrada, identificada por
 * la pareja <b>módulo · entidad</b>. Como la parametrización apunta aquí por llave
 * foránea, un nombre mal escrito no puede crear un contexto fantasma.
 *
 * <p>{@code codigo} conserva el prefijo de la aplicación
 * ({@code formacion_academica.modulo.entidad}) aunque hoy solo haya una: si en el
 * futuro el catálogo vuelve a centralizarse, los códigos ya encajan.
 */
@Data
@Entity
@Table(name = "catalogo_contextos", uniqueConstraints = {
        @UniqueConstraint(name = "uk_catalogo_contexto_codigo", columnNames = { "codigo" }),
        @UniqueConstraint(name = "uk_catalogo_contexto_modulo_entidad", columnNames = { "modulo", "entidad" })
})
public class CatalogoContextoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contexto", nullable = false, updatable = false)
    private Long idContexto;

    /** Módulo dentro de la aplicación. Ej: {@code estructura_institucion}. */
    @Column(name = "modulo", nullable = false, length = 80)
    private String modulo;

    /** Entidad dentro del módulo. Ej: {@code estudiante_grupo}. */
    @Column(name = "entidad", nullable = false, length = 80)
    private String entidad;

    /** Terna aplanada y única con la que los clientes piden su catálogo. */
    @Column(name = "codigo", nullable = false, length = 200)
    private String codigo;

    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @Column(name = "descripcion", length = 500)
    private String descripcion;
}
