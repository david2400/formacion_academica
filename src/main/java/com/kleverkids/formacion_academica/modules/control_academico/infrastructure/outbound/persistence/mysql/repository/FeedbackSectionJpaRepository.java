package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.feedback.FeedbackSectionEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackSectionJpaRepository extends JpaRepository<FeedbackSectionEntity, Long> {

    List<FeedbackSectionEntity> findByFormIdOrderByOrdenAsc(Long formId);
}
