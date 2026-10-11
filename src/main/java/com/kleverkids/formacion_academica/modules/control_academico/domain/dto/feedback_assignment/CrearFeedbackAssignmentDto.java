package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_assignment;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Aplicar un formulario PUBLICADO a una clase o a las clases de un grupo en un
 * rango de fechas (exactamente uno de claseId / grupoId).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrearFeedbackAssignmentDto {

    @NotNull(message = "El formulario es obligatorio")
    private Long formId;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 200)
    private String nombre;

    private Long claseId;
    private Long grupoId;
    private LocalDate desde;
    private LocalDate hasta;

    @Min(value = 0, message = "El desfase no puede ser negativo")
    private Integer aperturaOffsetMinutos;

    @Min(value = 1, message = "La ventana debe durar al menos 1 hora")
    @Max(value = 2160, message = "La ventana no puede superar 90 días")
    private Integer vigenciaHoras;

    private LocalDateTime abreEn;
    private LocalDateTime cierraEn;

    private boolean soloAsistentes;

    @Min(value = 1, message = "El umbral de anonimato mínimo es 1")
    private Integer umbralAnonimato;
}
