package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model;

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
        Boolean activo
) {}
