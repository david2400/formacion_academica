package com.kleverkids.formacion_academica.modules.estados.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Estado del catálogo. Se serializa en snake_case. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Estado {
    private Long id;
    private String codigo;
    private String nombre;
    private String descripcion;
    private String color;
    private String icono;
    private Integer orden;
}
