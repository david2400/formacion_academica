package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta;

public record CrearRespuestaBancoDto(String texto,
                                     boolean esCorrecta) {

    public CrearRespuestaBancoDto {
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException("El texto de la respuesta es obligatorio");
        }
    }
}
