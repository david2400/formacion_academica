package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model;

import java.time.Instant;

public record Grado(Long id,
                    String nombre,
                    Long nivelEducativoId,
                    boolean activo,
                    Integer usrCrea,
                    Integer usrMod,
                    Instant createdAt,
                    Instant updatedAt) {
}
