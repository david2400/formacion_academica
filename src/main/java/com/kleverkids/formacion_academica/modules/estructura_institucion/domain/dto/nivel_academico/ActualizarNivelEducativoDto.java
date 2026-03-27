package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.nivel_academico;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ActualizarNivelEducativoDto(
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    String nombre,

    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    String descripcion,

    Integer orden,

    Long nivelSuperiorId,

    @Pattern(regexp = "^(PREESCOLAR|BASICA|MEDIA|SUPERIOR)$", message = "La categoría debe ser una de: PREESCOLAR, BASICA, MEDIA, SUPERIOR")
    String categoria
) {}
