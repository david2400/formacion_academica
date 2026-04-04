package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.salon;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CrearSalonDto(
        @NotBlank(message = "El código es obligatorio")
        String codigo,
        
        @NotBlank(message = "El nombre es obligatorio")
        String nombre,
        
        @NotBlank(message = "La descripción es obligatoria")
        String descripcion,
        
        @NotNull(message = "La capacidad máxima es obligatoria")
        @Positive(message = "La capacidad máxima debe ser positiva")
        Integer capacidadMaxima,
        
        Integer numeroPiso,
        
        Boolean proyector,
        
        Boolean pizarronBlanco,
        
        Boolean aireAcondicionado,
        
        @NotBlank(message = "El nombre del edificio es obligatorio")
        String nombreEdificio
) {}
