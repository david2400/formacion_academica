package com.kleverkids.formacion_academica.modules.control_academico.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record Clase(Long id,
                    String nombre,
                    LocalDate fechaInicio,
                    LocalDate fechaFin,
                    List<Long> profesoresIds,
                    boolean activo,
                    Integer usrCrea,
                    Integer usrMod,
                    LocalDateTime createdAt,
                    LocalDateTime updatedAt) {
}
