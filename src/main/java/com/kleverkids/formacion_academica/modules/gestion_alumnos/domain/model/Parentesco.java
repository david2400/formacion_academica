package com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model;

import java.time.Instant;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Parentesco {
    private Long id;
    private String nombre;
    private String descripcion;
    private boolean eliminado;
    private Integer usrCrea;
    private Integer usrMod;
    private Instant createdAt;
    private Instant updatedAt;
}
