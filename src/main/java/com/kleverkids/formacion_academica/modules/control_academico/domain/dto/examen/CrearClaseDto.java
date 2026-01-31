package com.kleverkids.formacion_academica.modules.control_academico.domain.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record CrearClaseDto(String codigo,
                            String nombre,
                            LocalDate fechaInicio,
                            LocalDate fechaFin,
                            List<UUID> profesoresIds) {
}
