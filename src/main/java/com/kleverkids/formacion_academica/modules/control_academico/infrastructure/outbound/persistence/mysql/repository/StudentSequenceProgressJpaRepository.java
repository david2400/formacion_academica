package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.student_progress.StudentSequenceProgressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentSequenceProgressJpaRepository extends JpaRepository<StudentSequenceProgressEntity, Long> {

    Optional<StudentSequenceProgressEntity> findByEstudianteIdAndSequenceId(Long estudianteId, Long sequenceId);

    List<StudentSequenceProgressEntity> findBySequenceId(Long sequenceId);
}
