package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_response;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_assignment.valueobject.EstadoSolicitudFeedback;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Una fila de "Mis feedbacks": una aplicación + una clase, con el estado
 * derivado para el estudiante autenticado. No incluye nada de otros estudiantes.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudFeedbackDto {

    private Long assignmentId;
    private String assignmentNombre;
    private Long formId;
    private String formTitulo;
    private boolean anonimo;

    private Long claseId;
    private String claseNombre;
    private LocalDateTime claseInicio;
    private LocalDateTime claseFin;
    private Long grupoId;

    /** Ids de docentes de la clase (no hay entidad Docente en este servicio). */
    @Builder.Default
    private List<Long> profesoresIds = new ArrayList<>();

    private EstadoSolicitudFeedback estado;
    private LocalDateTime abre;
    private LocalDateTime cierra;
}
