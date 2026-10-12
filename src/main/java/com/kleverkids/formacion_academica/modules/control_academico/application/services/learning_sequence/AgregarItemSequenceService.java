package com.kleverkids.formacion_academica.modules.control_academico.application.services.learning_sequence;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_sequence.AgregarItemSequenceUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.activity.ActivityRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.learning_sequence.LearningSequenceRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_sequence.AgregarItemSequenceDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.LearningSequenceItem;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.valueobject.TipoItemSecuencia;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgregarItemSequenceService implements AgregarItemSequenceUseCase {

    private final LearningSequenceRepositoryPort learningSequenceRepositoryPort;
    private final ActivityRepositoryPort activityRepositoryPort;

    @Override
    public LearningSequenceItem agregarItem(Long sequenceId, AgregarItemSequenceDto request) {
        learningSequenceRepositoryPort.obtenerPorId(sequenceId)
                .orElseThrow(() -> new IllegalArgumentException("Secuencia no encontrada"));

        validarItemAAgregar(sequenceId, request);
        return learningSequenceRepositoryPort.agregarItem(sequenceId, request);
    }

    private void validarItemAAgregar(Long sequenceId, AgregarItemSequenceDto request) {
        if (request.getItemType() == TipoItemSecuencia.ACTIVITY) {
            if (request.getActivityId() == null) {
                throw new IllegalArgumentException("Debe indicar la actividad a agregar");
            }
            activityRepositoryPort.obtenerPorId(request.getActivityId())
                    .orElseThrow(() -> new IllegalArgumentException("La actividad indicada no existe o fue eliminada"));
        } else if (request.getItemType() == TipoItemSecuencia.GROUP
                && (request.getTituloGrupo() == null || request.getTituloGrupo().isBlank())) {
            throw new IllegalArgumentException("Debe indicar el título del grupo");
        }

        if (request.getParentItemId() != null) {
            LearningSequenceItem padre = learningSequenceRepositoryPort.obtenerItem(request.getParentItemId())
                    .orElseThrow(() -> new IllegalArgumentException("El grupo indicado no existe"));
            if (!padre.getSequenceId().equals(sequenceId)) {
                throw new IllegalArgumentException("El grupo indicado no pertenece a esta secuencia");
            }
            if (padre.getItemType() != TipoItemSecuencia.GROUP) {
                throw new IllegalArgumentException("Solo se puede agregar contenido dentro de un grupo");
            }
            if (padre.getParentItemId() != null) {
                throw new IllegalArgumentException(
                        "Un grupo no puede anidarse dentro de otro grupo (un solo nivel de anidamiento)");
            }
        }
    }
}
