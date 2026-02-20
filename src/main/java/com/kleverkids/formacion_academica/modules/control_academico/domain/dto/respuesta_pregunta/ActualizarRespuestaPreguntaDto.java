package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_pregunta;

import jakarta.validation.constraints.Min;
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
public class ActualizarRespuestaPreguntaDto {

    @NotNull(message = "El identificador de la respuesta es obligatorio")
    private UUID id;

    private String respuestaTexto;

    private UUID respuestaBancoId;

    private Boolean esCorrecta;

    @Min(value = 0, message = "El puntaje debe ser mayor o igual a cero")
    private Integer puntajeObtenido;

    public UUID id() {
        return id;
    }

    public String respuestaTexto() {
        return respuestaTexto;
    }

    public UUID respuestaBancoId() {
        return respuestaBancoId;
    }

    public Boolean esCorrecta() {
        return esCorrecta;
    }

    public Integer puntajeObtenido() {
        return puntajeObtenido;
    }
}
