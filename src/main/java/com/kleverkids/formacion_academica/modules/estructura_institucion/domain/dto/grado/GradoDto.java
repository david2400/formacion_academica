package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grado;

import java.util.UUID;

public record GradoDto(UUID id,
                       String nombre,
                       String nivelEducativo,
                       Integer orden) {
}
