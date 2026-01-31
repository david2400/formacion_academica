package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.grupo;

import java.util.UUID;

public record Grupo(UUID id,
                    String codigo,
                    String nombre,
                    UUID gradoId,
                    Integer capacidadMaxima,
                    UUID tutorId,
                    UUID aulaId,
                    boolean activo) {
}
