package com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.util.List;

import lombok.experimental.Accessors;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Examen {
    private Long id;
    private Long claseId;
    private String nombre;
    private LocalDate fecha;
    private List<ReglaCalificacion> reglasCalificacion;

}
