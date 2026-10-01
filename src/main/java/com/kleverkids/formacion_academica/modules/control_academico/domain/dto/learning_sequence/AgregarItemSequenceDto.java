package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_sequence;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.valueobject.TipoItemSecuencia;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * El orden no se recibe aquí: cada item nuevo se agrega al final del nivel
 * correspondiente (mismo criterio que AgregarContenidoActivityDto en Etapa 1).
 * {@code parentItemId} NULL agrega al nivel raíz; si se envía, debe apuntar a
 * un item existente de tipo GROUP y sin padre (un solo nivel de anidamiento).
 */
@Data
@NoArgsConstructor
public class AgregarItemSequenceDto {

    @NotNull(message = "El tipo de item es obligatorio")
    private TipoItemSecuencia itemType;

    /** NULL = se agrega al nivel raíz de la secuencia. */
    private Long parentItemId;

    /** Obligatorio cuando itemType=ACTIVITY: debe existir y no estar eliminada. */
    private Long activityId;

    /** Obligatorio cuando itemType=GROUP. */
    private String tituloGrupo;

    /** Si no se envía, el item se crea como obligatorio. */
    private Boolean esObligatoria;

    private LocalDateTime disponibleDesde;
    private LocalDateTime fechaLimite;
}
