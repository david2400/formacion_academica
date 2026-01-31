package com.kleverkids.formacion_academica.modules.control_academico.domain.model.clase;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record Clase(UUID id,
                    String codigo,
                    String nombre,
                    LocalDate fechaInicio,
                    LocalDate fechaFin,
                    List<UUID> profesoresIds) {
}
