package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.aula;

import java.util.UUID;

public record AulaDto(UUID id,
                      String nombre,
                      String descripcion,
                      Integer capacidad,
                      boolean activo) {
}
