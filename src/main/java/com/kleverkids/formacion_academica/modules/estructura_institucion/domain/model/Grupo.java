package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model;

import java.time.LocalDateTime;

public record Grupo(Long id,
                    String codigo,
                    String nombre,
                    Long gradoId,
                    Integer capacidadMaxima,
                    Long tutorId,
                    Long aulaId,
                    boolean activo,
                    Integer usrCrea,
                    Integer usrMod,
                    LocalDateTime createdAt,
                    LocalDateTime updatedAt) {
}
