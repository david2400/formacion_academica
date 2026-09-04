package com.kleverkids.formacion_academica.modules.control_academico.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/** Tipo de clase del catálogo. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoClase {
    private Long id;
    private String nombre;
    private String descripcion;
    private String color;
    private boolean eliminado;
    private Integer usrCrea;
    private Integer usrMod;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
