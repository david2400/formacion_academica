package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model;

import java.time.LocalDateTime;

public record Aula(Long id,
                   String nombre,
                   String descripcion,
                   Integer capacidad,
                   boolean eliminado,
                   Integer usrCrea,
                   Integer usrMod,
                   LocalDateTime createdAt,
                   LocalDateTime updatedAt) {
}
