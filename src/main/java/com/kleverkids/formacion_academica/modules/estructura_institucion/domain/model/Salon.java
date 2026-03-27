package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model;

import java.time.LocalDateTime;

public record Salon(
        Long id,
        String codigo,
        String nombre,
        String descripcion,
        Integer capacidadMaxima,
        Integer numeroPiso,
        Boolean tieneProyector,
        Boolean tienePizarronBlanco,
        Boolean tieneAireAcondicionado,
        String nombreEdificio,
        Boolean activo,
        Integer usrCrea,
        Integer usrMod,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
