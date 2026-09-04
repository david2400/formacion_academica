package com.kleverkids.formacion_academica.modules.control_academico.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/** Una anotación de la bitácora de una clase. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ObservacionClase {
    private Long id;
    private String observacion;
    private Integer usrCrea;
    private LocalDateTime createdAt;
}
