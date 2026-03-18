package com.kleverkids.formacion_academica.modules.estados.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CambioEstadoRequest(
    @NotBlank(message = "El tipo de entidad es obligatorio")
    @Size(max = 50, message = "El tipo de entidad no puede exceder 50 caracteres")
    String entidadTipo,
    
    @NotNull(message = "El ID de la entidad es obligatorio")
    Long entidadId,
    
    @NotNull(message = "El ID del nuevo estado es obligatorio")
    Long nuevoEstadoId,
    
    @NotNull(message = "El ID del módulo es obligatorio")
    Long idModulo,
    
    Long idUsuarioCambio,
    
    @Size(max = 500, message = "El motivo no puede exceder 500 caracteres")
    String motivo
) {}
