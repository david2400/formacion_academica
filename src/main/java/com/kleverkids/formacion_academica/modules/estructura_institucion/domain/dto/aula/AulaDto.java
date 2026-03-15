package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.aula;

public record AulaDto(Long id,
                      String nombre,
                      String descripcion,
                      Integer capacidad,
                      boolean activo) {
}
