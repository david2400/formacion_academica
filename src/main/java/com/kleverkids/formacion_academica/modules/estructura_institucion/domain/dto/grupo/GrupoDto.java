package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grupo;

import java.util.UUID;

public record GrupoDto(UUID id,
                       String codigo,
                       String nombre,
                       UUID gradoId,
                       Integer capacidadMaxima,
                       UUID tutorId,
                       UUID aulaId,
                       boolean activo) {
}
