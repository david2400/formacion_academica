package com.kleverkids.formacion_academica.modules.control_academico.application.output.learning_sequence;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_sequence.ActualizarLearningSequenceDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_sequence.AgregarItemSequenceDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_sequence.CrearLearningSequenceDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_sequence.DefinirDependenciaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_sequence.FiltroLearningSequenceDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_sequence.ReordenarItemsSequenceDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.ActivityDependencyRule;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.LearningSequence;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.LearningSequenceItem;

import java.util.List;
import java.util.Optional;

public interface LearningSequenceRepositoryPort {

    LearningSequence guardar(CrearLearningSequenceDto dto);

    Optional<LearningSequence> obtenerPorId(Long id);

    List<LearningSequence> listarTodas();

    List<LearningSequence> buscar(FiltroLearningSequenceDto filtro);

    LearningSequence actualizar(ActualizarLearningSequenceDto dto);

    void eliminar(Long id);

    /** Congela una LearningSequenceVersion nueva y marca la secuencia como PUBLISHED. */
    LearningSequence publicar(Long id);

    LearningSequence archivar(Long id);

    /** Copia la secuencia (DRAFT), sus items y dependencias; no copia versiones. */
    LearningSequence duplicar(Long id);

    LearningSequenceItem agregarItem(Long sequenceId, AgregarItemSequenceDto dto);

    List<LearningSequenceItem> listarItems(Long sequenceId);

    Optional<LearningSequenceItem> obtenerItem(Long itemId);

    void eliminarItem(Long sequenceId, Long itemId);

    List<LearningSequenceItem> reordenar(Long sequenceId, ReordenarItemsSequenceDto dto);

    ActivityDependencyRule definirDependencia(Long itemId, DefinirDependenciaDto dto);

    List<ActivityDependencyRule> listarDependencias(Long itemId);

    void eliminarDependencia(Long itemId, Long dependencyId);
}
