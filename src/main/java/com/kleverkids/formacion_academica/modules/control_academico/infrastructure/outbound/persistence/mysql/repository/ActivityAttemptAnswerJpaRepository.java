package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.activity_attempt.ActivityAttemptAnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ActivityAttemptAnswerJpaRepository extends JpaRepository<ActivityAttemptAnswerEntity, Long> {

    List<ActivityAttemptAnswerEntity> findByAttemptId(Long attemptId);

    Optional<ActivityAttemptAnswerEntity> findByAttemptIdAndContentItemId(Long attemptId, Long contentItemId);
}
