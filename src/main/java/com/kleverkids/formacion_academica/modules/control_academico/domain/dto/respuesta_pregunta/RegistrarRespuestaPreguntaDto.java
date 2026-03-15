package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_pregunta;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrarRespuestaPreguntaDto {

    @NotNull(message = "El examen es obligatorio")
    private Long examenId;

    @NotNull(message = "El estudiante es obligatorio")
    private Long estudianteId;

    @NotNull(message = "La pregunta es obligatoria")
    private Long preguntaId;

    private Long respuestaBancoId;

    private String respuestaTexto;

    private Boolean esCorrecta;

    @Min(value = 0, message = "El puntaje debe ser mayor o igual a cero")
    private Integer puntajeObtenido;

    public Long examenId() {
        return examenId;
    }

    public Long estudianteId() {
        return estudianteId;
    }

    public Long preguntaId() {
        return preguntaId;
    }

    public Long respuestaBancoId() {
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
