package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstudianteGrupo {
    private Long id;
    private Long estudianteId;
    private Long grupoId;
    private LocalDate fechaAsignacion;
    private Integer estadoId;
    private boolean eliminado;
    private Integer usrCrea;
    private Integer usrMod;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
