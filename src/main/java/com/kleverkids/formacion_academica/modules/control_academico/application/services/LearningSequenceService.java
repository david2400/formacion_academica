package com.kleverkids.formacion_academica.modules.control_academico.application.services;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_sequence.ActualizarLearningSequenceUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_sequence.AgregarItemSequenceUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_sequence.ArchivarLearningSequenceUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_sequence.ConsultarLearningSequenceUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_sequence.CrearLearningSequenceUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_sequence.DefinirDependenciaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_sequence.DuplicarLearningSequenceUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_sequence.EliminarDependenciaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_sequence.EliminarItemSequenceUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_sequence.EliminarLearningSequenceUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_sequence.ListarDependenciasItemUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_sequence.ListarItemsSequenceUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_sequence.ListarLearningSequenceUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_sequence.PublicarLearningSequenceUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_sequence.ReordenarItemsSequenceUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.activity.ActivityRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.learning_sequence.LearningSequenceEventPublisher;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.learning_sequence.LearningSequenceRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_sequence.ActualizarLearningSequenceDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_sequence.AgregarItemSequenceDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_sequence.CrearLearningSequenceDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_sequence.DefinirDependenciaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_sequence.FiltroLearningSequenceDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_sequence.ReordenarItemsSequenceDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.ActivityDependencyRule;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.LearningSequence;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.LearningSequenceItem;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.valueobject.TipoItemSecuencia;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.valueobject.TipoReglaDependencia;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LearningSequenceService implements CrearLearningSequenceUseCase,
        ActualizarLearningSequenceUseCase,
        ConsultarLearningSequenceUseCase,
        ListarLearningSequenceUseCase,
        EliminarLearningSequenceUseCase,
        PublicarLearningSequenceUseCase,
        ArchivarLearningSequenceUseCase,
        DuplicarLearningSequenceUseCase,
        AgregarItemSequenceUseCase,
        ListarItemsSequenceUseCase,
        EliminarItemSequenceUseCase,
        ReordenarItemsSequenceUseCase,
        DefinirDependenciaUseCase,
        ListarDependenciasItemUseCase,
        EliminarDependenciaUseCase {

    private final LearningSequenceRepositoryPort learningSequenceRepositoryPort;
    private final ActivityRepositoryPort activityRepositoryPort;
    private final LearningSequenceEventPublisher learningSequenceEventPublisher;

    @Override
    public LearningSequence crear(CrearLearningSequenceDto request) {
        return learningSequenceRepositoryPort.guardar(request);
    }

    @Override
    public LearningSequence actualizar(ActualizarLearningSequenceDto request) {
        return learningSequenceRepositoryPort.actualizar(request);
    }

    @Override
    public Optional<LearningSequence> consultarPorId(Long id) {
        return learningSequenceRepositoryPort.obtenerPorId(id);
    }

    @Override
    public List<LearningSequence> listarTodas() {
        return learningSequenceRepositoryPort.listarTodas();
    }

    @Override
    public List<LearningSequence> buscar(FiltroLearningSequenceDto filtro) {
        if (filtro == null || filtro.estaVacio()) {
            return learningSequenceRepositoryPort.listarTodas();
        }
        return learningSequenceRepositoryPort.buscar(filtro);
    }

    @Override
    public void eliminar(Long id) {
        learningSequenceRepositoryPort.eliminar(id);
    }

    @Override
    public LearningSequence publicar(Long id) {
        learningSequenceRepositoryPort.obtenerPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Secuencia no encontrada"));

        List<LearningSequenceItem> items = learningSequenceRepositoryPort.listarItems(id);
        if (items.isEmpty()) {
            throw new IllegalStateException(
                    "La secuencia debe tener al menos una actividad o grupo para poder publicarse");
        }

        LearningSequence publicada = learningSequenceRepositoryPort.publicar(id);
        publicada.registrarEventoPublicacion();
        publicada.getDomainEvents().forEach(learningSequenceEventPublisher::publish);
        publicada.clearDomainEvents();
        return publicada;
    }

    @Override
    public LearningSequence archivar(Long id) {
        return learningSequenceRepositoryPort.archivar(id);
    }

    @Override
    public LearningSequence duplicar(Long id) {
        learningSequenceRepositoryPort.obtenerPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Secuencia no encontrada"));
        return learningSequenceRepositoryPort.duplicar(id);
    }

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

    @Override
    public List<LearningSequenceItem> listarItems(Long sequenceId) {
        return learningSequenceRepositoryPort.listarItems(sequenceId);
    }

    @Override
    public void eliminarItem(Long sequenceId, Long itemId) {
        learningSequenceRepositoryPort.eliminarItem(sequenceId, itemId);
    }

    @Override
    public List<LearningSequenceItem> reordenar(Long sequenceId, ReordenarItemsSequenceDto request) {
        return learningSequenceRepositoryPort.reordenar(sequenceId, request);
    }

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

    @Override
    public List<ActivityDependencyRule> listarDependencias(Long itemId) {
        return learningSequenceRepositoryPort.listarDependencias(itemId);
    }

    @Override
    public void eliminarDependencia(Long itemId, Long dependencyId) {
        learningSequenceRepositoryPort.eliminarDependencia(itemId, dependencyId);
    }
}
