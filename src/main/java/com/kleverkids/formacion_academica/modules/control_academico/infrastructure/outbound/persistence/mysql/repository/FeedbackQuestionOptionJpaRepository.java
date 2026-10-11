package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.feedback.FeedbackQuestionOptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface FeedbackQuestionOptionJpaRepository extends JpaRepository<FeedbackQuestionOptionEntity, Long> {

    List<FeedbackQuestionOptionEntity> findByQuestionIdInOrderByQuestionIdAscOrdenAsc(Collection<Long> questionIds);

    List<FeedbackQuestionOptionEntity> findByQuestionId(Long questionId);
}
