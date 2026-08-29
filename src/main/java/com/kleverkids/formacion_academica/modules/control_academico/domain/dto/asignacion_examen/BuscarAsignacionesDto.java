package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asignacion_examen;

import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import lombok.experimental.Accessors;
@Data
@NoArgsConstructor
public class BuscarAsignacionesDto {
    private Long examenId;
    private Long claseId;
    private String grado;
    private String grupo;
    private String estado;
    private LocalDate fechaDesde;
    private LocalDate fechaHasta;
    private Boolean activas;

public BuscarAsignacionesDto(Long examenId, Long claseId, String grado, String grupo, String estado, LocalDate fechaDesde, LocalDate fechaHasta, Boolean activas) {
        if (activas == null) {
            activas = true;
        this.examenId = examenId;
        this.claseId = claseId;
        this.grado = grado;
        this.grupo = grupo;
        this.estado = estado;
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
        this.activas = activas;
    }
    }

}
