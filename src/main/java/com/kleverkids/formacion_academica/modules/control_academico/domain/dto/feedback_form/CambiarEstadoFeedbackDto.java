package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_form;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Transición de estado a través del motor (mismo contrato que el resto del
 * proyecto: el cliente pide el estado destino; el motor decide si procede).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CambiarEstadoFeedbackDto {

    @NotNull(message = "El estado destino es obligatorio")
    private Long nuevoEstadoId;

    @Size(max = 500)
    private String motivo;
}
