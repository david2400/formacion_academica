package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.learning_sequence.LearningSequenceVersionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LearningSequenceVersionJpaRepository extends JpaRepository<LearningSequenceVersionEntity, Long> {

    int countBySequenceId(Long sequenceId);
}
