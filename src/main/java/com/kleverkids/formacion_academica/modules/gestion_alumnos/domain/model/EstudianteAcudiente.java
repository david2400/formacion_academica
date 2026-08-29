package com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model;

import java.time.Instant;
import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstudianteAcudiente {
    private Long id;
    private Long estudianteId;
    private Long acudienteId;
    private Long parentescoId;
    private boolean esPrincipal;
    private String estado;
    private LocalDate fechaVinculacion;
    private LocalDate fechaFin;
    private boolean eliminado;
    private Integer usrCrea;
    private Integer usrMod;
    private Instant createdAt;
    private Instant updatedAt;
}
