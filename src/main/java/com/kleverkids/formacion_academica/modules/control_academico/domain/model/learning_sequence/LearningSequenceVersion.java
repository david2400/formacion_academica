package com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Snapshot inmutable de una LearningSequence publicada (misma lógica que
 * ActivityVersion en Etapa 1): congela el árbol de items + dependencias en el
 * momento de publicar, para que una asignación futura (Etapa 3) nunca cambie
 * retroactivamente lo que ve un estudiante que ya empezó.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LearningSequenceVersion {

    private Long id;
    private Long sequenceId;
    private Integer numeroVersion;
    private Map<String, Object> snapshot;
    private LocalDateTime publicadoEn;
    private Long publicadoPor;
}
