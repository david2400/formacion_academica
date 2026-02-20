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
public class RegistrarRespuestaPreguntaDto {

    @NotNull(message = "El examen es obligatorio")
    private UUID examenId;

    @NotNull(message = "El estudiante es obligatorio")
    private UUID estudianteId;

    @NotNull(message = "La pregunta es obligatoria")
    private UUID preguntaId;

    private UUID respuestaBancoId;

    private String respuestaTexto;

    private Boolean esCorrecta;

    @Min(value = 0, message = "El puntaje debe ser mayor o igual a cero")
    private Integer puntajeObtenido;

    public UUID examenId() {
        return examenId;
    }

    public UUID estudianteId() {
        return estudianteId;
    }

    public UUID preguntaId() {
        return preguntaId;
    }

    public UUID respuestaBancoId() {
        return respuestaBancoId;
    }

    public String respuestaTexto() {
        return respuestaTexto;
    }

    public Boolean esCorrecta() {
        return esCorrecta;
    }

    public Integer puntajeObtenido() {
        return puntajeObtenido;
    }
}
