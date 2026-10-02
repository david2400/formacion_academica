package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.valueobject.ContentKind;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

/**
 * El orden no se recibe aquí: cada pieza de contenido nueva se agrega al
 * final de la lista actual (el reordenamiento es una operación aparte, ver
 * {@code ReordenarContenidoActivityDto}), evitando que el cliente tenga que
 * conocer la posición exacta para agregar.
 */
@Data
@NoArgsConstructor
public class AgregarContenidoActivityDto {

    @NotNull(message = "El tipo de contenido es obligatorio")
    private ContentKind contentKind;

    /** Obligatorio cuando contentKind=QUESTION: debe existir y no estar eliminada. */
    private Long preguntaId;

    /** Obligatorio cuando contentKind=TEXT. */
    private Map<String, Object> texto;

    /**
     * Obligatorio cuando contentKind=EXTERNAL: debe traer {@code tipo}
     * (VIDEO|EMBED|LINK) y {@code url} (https) — validado en
     * {@code ActivityJpaAdapter#agregarContenido}, igual que QUESTION/TEXT.
     */
    private Map<String, Object> contenidoExterno;

    private BigDecimal puntos;
}
