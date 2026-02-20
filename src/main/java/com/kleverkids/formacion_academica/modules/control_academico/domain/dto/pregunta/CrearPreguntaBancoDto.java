package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrearPreguntaBancoDto {

    @NotNull(message = "La temática es obligatoria")
    private UUID tematicaId;

    @NotBlank(message = "El enunciado es obligatorio")
    private String enunciado;

    @NotBlank(message = "El tipo de pregunta es obligatorio")
    private String tipo;

    private String nivelDificultad;

    @NotNull(message = "El puntaje es obligatorio")
    @Min(value = 1, message = "El puntaje debe ser mayor o igual a 1")
    private Integer puntaje;

    @Valid
    private List<CrearRespuestaBancoDto> respuestas;

}
