package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.aula;

import java.util.UUID;

public record Aula(UUID id,
                   String nombre,
                   String descripcion,
                   Integer capacidad,
                   boolean activo) {
}
