package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Grupo {
    private Long id;
    private String codigo;
    private String nombre;
    private Long gradoId;
    private Integer capacidadMaxima;
    private String periodoAcademico;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Long tutorId;
    private Long aulaId;
    private boolean eliminado;
    private Integer usrCrea;
    private Integer usrMod;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
