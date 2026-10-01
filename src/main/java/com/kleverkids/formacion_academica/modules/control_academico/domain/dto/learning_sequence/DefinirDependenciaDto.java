package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_sequence;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.valueobject.TipoReglaDependencia;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class DefinirDependenciaDto {

    @NotNull(message = "Debe indicar de qué item depende")
    private Long dependsOnItemId;

    @NotNull(message = "El tipo de regla es obligatorio")
    private TipoReglaDependencia ruleType;

    /** Obligatorio para SCORE_GTE y ATTEMPTS_GTE. */
    private BigDecimal ruleValorNumerico;

    /** Obligatorio para DATE_AFTER. */
    private LocalDateTime ruleValorFecha;
}
