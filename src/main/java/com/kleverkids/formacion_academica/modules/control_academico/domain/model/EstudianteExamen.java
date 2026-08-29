package com.kleverkids.formacion_academica.modules.control_academico.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

import lombok.experimental.Accessors;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstudianteExamen {
    private Long id;
    private Long examenId;
    private Long estudianteId;
    private LocalDateTime asignadoEn;
    private boolean eliminado;
    private Integer usrCrea;
    private Integer usrMod;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
