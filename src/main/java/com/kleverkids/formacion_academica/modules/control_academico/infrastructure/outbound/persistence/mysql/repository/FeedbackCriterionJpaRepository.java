package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.feedback.FeedbackCriterionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeedbackCriterionJpaRepository extends JpaRepository<FeedbackCriterionEntity, Long> {

    Optional<FeedbackCriterionEntity> findByCodigo(String codigo);

    List<FeedbackCriterionEntity> findAllByOrderByNombreAsc();
}
