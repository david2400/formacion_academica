package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_assignment;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Edición de una aplicación. El formulario y el alcance no cambian (cambiaría
 * a quién pertenecen las respuestas ya recibidas); soloAsistentes y el umbral
 * solo se pueden cambiar mientras está en BORRADOR (lo valida el servicio).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarFeedbackAssignmentDto {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 200)
    private String nombre;

    @Min(0)
    private Integer aperturaOffsetMinutos;

    @Min(1)
    @Max(2160)
    private Integer vigenciaHoras;

    private LocalDateTime abreEn;
    private LocalDateTime cierraEn;

    private Boolean soloAsistentes;

    @Min(1)
    private Integer umbralAnonimato;
}
