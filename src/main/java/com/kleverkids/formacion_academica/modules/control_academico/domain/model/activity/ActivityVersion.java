package com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Snapshot inmutable de una Activity en el momento en que fue publicada. Las
 * asignaciones (LearningAssignment, Etapa 3) referencian siempre un
 * activity_version_id concreto, nunca el draft editable, para que editar una
 * actividad ya publicada no cambie retroactivamente lo que ve un estudiante
 * que ya empezó (ver arquitectura aprobada, sección 2.7 "Versionamiento").
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityVersion {

    private Long id;
    private Long activityId;
    private Integer numeroVersion;

    /** Copia congelada de título/instrucciones/contenido en el momento de publicar. */
    private Map<String, Object> snapshot;

    private LocalDateTime publicadoEn;
    private Long publicadoPor;
}
