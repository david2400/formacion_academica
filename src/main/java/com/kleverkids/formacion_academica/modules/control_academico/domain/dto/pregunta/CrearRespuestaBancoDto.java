package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrearRespuestaBancoDto {

    @NotBlank(message = "El texto de la respuesta es obligatorio")
    private String texto;

    private boolean esCorrecta;

    public String texto() {
        return texto;
    }

    public boolean esCorrecta() {
        return esCorrecta;
    }
}
