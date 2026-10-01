package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.learning_assignment.LearningAssignmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LearningAssignmentJpaRepository extends JpaRepository<LearningAssignmentEntity, Long>,
        JpaSpecificationExecutor<LearningAssignmentEntity> {
}
