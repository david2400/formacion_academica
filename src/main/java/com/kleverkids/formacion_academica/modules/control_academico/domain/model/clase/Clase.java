package com.kleverkids.formacion_academica.modules.control_academico.domain.model.clase;

import java.time.LocalDate;
import java.util.List;


public record Clase(Long id,
                    String nombre,
                    LocalDate fechaInicio,
                    LocalDate fechaFin,
                    List<Long> profesoresIds) {
}
