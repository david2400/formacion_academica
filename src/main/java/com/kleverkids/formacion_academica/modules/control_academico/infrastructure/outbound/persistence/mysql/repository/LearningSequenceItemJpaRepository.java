package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.learning_sequence.LearningSequenceItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LearningSequenceItemJpaRepository extends JpaRepository<LearningSequenceItemEntity, Long> {

    List<LearningSequenceItemEntity> findBySequenceIdOrderByOrdenAsc(Long sequenceId);

    List<LearningSequenceItemEntity> findBySequenceIdAndParentItemIdOrderByOrdenAsc(Long sequenceId, Long parentItemId);

    List<LearningSequenceItemEntity> findBySequenceIdAndParentItemIdIsNullOrderByOrdenAsc(Long sequenceId);

    int countBySequenceIdAndParentItemId(Long sequenceId, Long parentItemId);

    int countBySequenceIdAndParentItemIdIsNull(Long sequenceId);

    List<LearningSequenceItemEntity> findByParentItemId(Long parentItemId);

}
