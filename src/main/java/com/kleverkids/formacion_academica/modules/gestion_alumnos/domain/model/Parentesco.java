package com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model;

import java.time.Instant;

public record Parentesco(Long id,
                         String nombre,
                         String descripcion,
                         boolean eliminado,
                         Integer usrCrea,
                         Integer usrMod,
                         Instant createdAt,
                         Instant updatedAt) {
}
