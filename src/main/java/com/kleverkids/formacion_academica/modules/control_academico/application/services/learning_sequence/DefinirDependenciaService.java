package com.kleverkids.formacion_academica.modules.control_academico.application.services.learning_sequence;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_sequence.DefinirDependenciaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.learning_sequence.LearningSequenceRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_sequence.DefinirDependenciaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.ActivityDependencyRule;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.LearningSequenceItem;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.valueobject.TipoReglaDependencia;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefinirDependenciaService implements DefinirDependenciaUseCase {

    private final LearningSequenceRepositoryPort learningSequenceRepositoryPort;

    @Override
    public ActivityDependencyRule definir(Long sequenceId, Long itemId, DefinirDependenciaDto request) {
        LearningSequenceItem item = learningSequenceRepositoryPort.obtenerItem(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Item no encontrado"));
        if (!item.getSequenceId().equals(sequenceId)) {
            throw new IllegalArgumentException("El item no pertenece a esta secuencia");
        }

        if (itemId.equals(request.getDependsOnItemId())) {
            throw new IllegalArgumentException("Un item no puede depender de sí mismo");
        }

        LearningSequenceItem dependeDe = learningSequenceRepositoryPort.obtenerItem(request.getDependsOnItemId())
                .orElseThrow(() -> new IllegalArgumentException("El item del que depende no existe"));
        if (!dependeDe.getSequenceId().equals(sequenceId)) {
            throw new IllegalArgumentException("Solo se puede depender de items de la misma secuencia");
        }

        validarValorDeRegla(request);

        return learningSequenceRepositoryPort.definirDependencia(itemId, request);
    }

    private void validarValorDeRegla(DefinirDependenciaDto request) {
        boolean esNumerica = request.getRuleType() == TipoReglaDependencia.SCORE_GTE
                || request.getRuleType() == TipoReglaDependencia.ATTEMPTS_GTE;
        boolean esDeFecha = request.getRuleType() == TipoReglaDependencia.DATE_AFTER;

        if (esNumerica && request.getRuleValorNumerico() == null) {
            throw new IllegalArgumentException(
                    "Debe indicar el valor numérico mínimo para esta regla (" + request.getRuleType() + ")");
        }
        if (esDeFecha && request.getRuleValorFecha() == null) {
            throw new IllegalArgumentException("Debe indicar la fecha para esta regla");
        }
    }
}
