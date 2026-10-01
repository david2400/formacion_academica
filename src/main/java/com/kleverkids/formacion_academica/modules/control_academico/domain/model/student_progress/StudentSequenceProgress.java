package com.kleverkids.formacion_academica.modules.control_academico.domain.model.student_progress;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Proyección de progreso de un estudiante en una secuencia — NO es fuente de
 * verdad (ver arquitectura aprobada §2.3): se recalcula por completo a partir
 * de {@code ActivityAttempt} + {@code LearningSequenceItem} cada vez que un
 * intento se completa ({@code StudentProgressService.recalcular}). Corrección
 * respecto al documento original: FK operacional {@code sequenceId} en vez de
 * {@code sequenceVersionId} (mismo criterio aplicado en Etapa 2/3 a
 * LearningSequenceItem/LearningAssignment).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentSequenceProgress {

    private Long id;
    private Long estudianteId;
    private Long sequenceId;
    private Integer completedItems;
    private Integer totalItems;

    /** Próximo item ACTIVITY pendiente, por orden (heurística simple de "siguiente"); NULL si ya completó todo. */
    private Long currentItemId;

    private BigDecimal percentComplete;
    private LocalDateTime lastActivityAt;
}
