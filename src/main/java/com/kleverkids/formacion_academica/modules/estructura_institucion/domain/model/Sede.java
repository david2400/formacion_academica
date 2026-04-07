package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model;

import java.time.LocalDateTime;

public record Sede(
        Long id,
//        String codigo,
        String nombre,
        String descripcion,
        String direccion,
        String ciudadId,
        String departamentoId,
        String pais,
        String telefono,
        String email,
        String contactoPrincipal,
        String telefonoContacto,
        String emailContacto,
        
        Boolean eliminado,
        Integer usrCrea,
        Integer usrMod,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
