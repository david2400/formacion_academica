package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.nivel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CrearNivelEducativoDto(
    @NotBlank(message = "El código es obligatorio")
    @Size(max = 10, message = "El código no puede exceder 10 caracteres")
    @Pattern(regexp = "^[A-Z0-9-_]+$", message = "El código solo puede contener letras mayúsculas, números, guiones y guiones bajos")
    String codigo,

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    String nombre,

    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    String descripcion,

    @NotNull(message = "El orden es obligatorio")
    Integer orden,

    Long nivelSuperiorId,

    @NotBlank(message = "La categoría es obligatoria")
    @Pattern(regexp = "^(PREESCOLAR|BASICA|MEDIA|SUPERIOR)$", message = "La categoría debe ser una de: PREESCOLAR, BASICA, MEDIA, SUPERIOR")
    String categoria
) {}
