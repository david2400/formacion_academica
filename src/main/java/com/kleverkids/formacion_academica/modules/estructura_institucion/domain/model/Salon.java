package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Salon {
    private Long id;
    private String codigo;
    private String nombre;
    private String descripcion;
    private Integer capacidadMaxima;
    private Integer numeroPiso;

    private Long sedeId;
    private Sede sede;
    private Boolean proyector;
    private Boolean pizarronBlanco;
    private Boolean aireAcondicionado;

    private Boolean eliminado;
    private Integer usrCrea;
    private Integer usrMod;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
