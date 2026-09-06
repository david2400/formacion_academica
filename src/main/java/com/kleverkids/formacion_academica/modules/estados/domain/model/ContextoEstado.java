package com.kleverkids.formacion_academica.modules.estados.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Contexto registrado: un tipo de entidad que usa estados.
 *
 * <p>Se identifica por la pareja módulo · entidad, aplanada en un {@code codigo}
 * único que es lo que piden los clientes.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContextoEstado {
    private Long id;
    private String modulo;
    private String entidad;
    /** Ej: {@code formacion_academica.estructura_institucion.estudiante_grupo}. */
    private String codigo;
    private String nombre;
    private String descripcion;
}
