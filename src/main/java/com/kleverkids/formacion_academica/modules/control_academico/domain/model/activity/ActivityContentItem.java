package com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.valueobject.ContentKind;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Una pieza de contenido dentro de una Activity. Para QUESTION, referencia
 * directa (FK, sin snapshot) a una Pregunta ya existente del módulo de
 * preguntas — ver decisión §2.2 de la arquitectura aprobada. Para TEXT, el
 * contenido enriquecido vive acotado en {@code texto} (JSON).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityContentItem {

    private Long id;
    private Long activityId;
    private Integer orden;
    private ContentKind contentKind;

    /** FK directo a questions.id cuando contentKind=QUESTION. */
    private Long preguntaId;

    /** Contenido enriquecido acotado cuando contentKind=TEXT. */
    private Map<String, Object> texto;

    /**
     * Recurso de terceros cuando contentKind=EXTERNAL: {@code tipo}
     * (VIDEO/EMBED/LINK), {@code url} (https), {@code titulo} y
     * {@code descripcion} opcional. Mismo enfoque de JSON acotado que
     * {@code texto}, sin modelar un tipo Java propio por lo mismo que
     * {@code texto} no lo tiene: la forma es simple y no necesita validación
     * de dominio rica, solo la validación de entrada ya aplicada al agregar.
     */
    private Map<String, Object> contenidoExterno;

    private BigDecimal puntos;

    private boolean eliminado;
    private Integer usrCrea;
    private Integer usrMod;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
