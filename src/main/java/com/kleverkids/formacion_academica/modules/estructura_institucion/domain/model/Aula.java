package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model;

import java.time.Instant;

public record Aula(Long id,
                   String nombre,
                   String descripcion,
                   Integer capacidad,
                   boolean activo,
                   Integer usrCrea,
                   Integer usrMod,
                   Instant createdAt,
                   Instant updatedAt) {
}
