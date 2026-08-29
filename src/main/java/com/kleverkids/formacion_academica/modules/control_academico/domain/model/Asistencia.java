package com.kleverkids.formacion_academica.modules.control_academico.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

import lombok.experimental.Accessors;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Asistencia {
    private Long id;
    private Long claseId;
    private Long estudianteId;
    private LocalDateTime fechaRegistro;
    private boolean presente;
    private boolean eliminado;
    private Integer usrCrea;
    private Integer usrMod;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
