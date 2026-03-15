package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta;

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
public class ActualizarPreguntaBancoDto {

    @NotNull(message = "El identificador de la pregunta es obligatorio")
    private Long id;

    @NotNull(message = "La temática de la pregunta es obligatoria")
    private Long tematicaId;

    private String enunciado;

    private String tipo;

    private String nivelDificultad;

    @Min(value = 1, message = "El puntaje debe ser mayor o igual a 1")
    private Integer puntaje;

    public Long id() {
        return id;
    }

    public Long tematicaId() {
        return tematicaId;
    }

    public String enunciado() {
        return enunciado;
    }

    public String tipo() {
        return tipo;
    }

    public String nivelDificultad() {
        return nivelDificultad;
    }

    public Integer puntaje() {
        return puntaje;
    }
}
