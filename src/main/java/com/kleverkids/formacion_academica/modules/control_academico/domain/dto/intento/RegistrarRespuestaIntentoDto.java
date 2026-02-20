package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrarRespuestaIntentoDto {

    @NotNull(message = "El intento es obligatorio")
    private UUID intentoId;

    @NotNull(message = "La pregunta es obligatoria")
    private UUID preguntaId;

    @NotBlank(message = "La respuesta es obligatoria")
    private String respuesta;

    private Boolean esCorrecta;

    private Integer puntajeObtenido;

    public UUID intentoId() {
        return intentoId;
    }

    public UUID preguntaId() {
        return preguntaId;
    }

    public String respuesta() {
        return respuesta;
    }

    public Boolean esCorrecta() {
        return esCorrecta;
    }

    public Integer puntajeObtenido() {
        return puntajeObtenido;
    }
}
