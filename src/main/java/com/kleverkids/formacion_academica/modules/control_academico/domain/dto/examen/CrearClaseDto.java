package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrearClaseDto {

    private String codigo;
    private String nombre;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private List<UUID> profesoresIds;

}
