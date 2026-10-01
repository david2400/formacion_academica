package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_sequence;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Reordena los items de un único nivel a la vez (todos los enviados deben
 * compartir el mismo {@code parentItemId}; se valida en el Service). Para
 * mover un item a otro nivel (sacarlo de un Grupo, por ejemplo) no hay
 * endpoint propio en Etapa 2 — se elimina y se vuelve a agregar.
 */
@Data
@NoArgsConstructor
public class ReordenarItemsSequenceDto {

    /** NULL = se está reordenando el nivel raíz. */
    private Long parentItemId;

    @NotEmpty(message = "Debe enviar al menos un ítem para reordenar")
    private List<OrdenItemDto> items;
}
