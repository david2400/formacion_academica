package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.aula;

public record Aula(Long id,
                   String nombre,
                   String descripcion,
                   Integer capacidad,
                   boolean activo) {
}
