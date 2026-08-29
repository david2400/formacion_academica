package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NivelEducativo {
    private Long id;
    private String codigo;
    private String nombre;
    private String descripcion;
//                              Integer orden,
    private Long nivelSuperiorId;
    private NivelEducativo nivelSuperior;
    private boolean eliminado;
    private Integer usrCrea;
    private Integer usrMod;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
