package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.examen.ExamSubmissionRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers.ExamPersistenceMapper;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository.ExamSubmissionJpaRepository;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen.ExamSubmission;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.examenes.ExamSubmissionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class ExamSubmissionPersistenceJpaAdapter implements ExamSubmissionRepositoryPort {
    
    private final ExamSubmissionJpaRepository jpaRepository;
    private final ExamPersistenceMapper mapper;
    
    @Override
    public ExamSubmission save(ExamSubmission submission) {
        ExamSubmissionEntity entity = mapper.toEntity(submission);
        ExamSubmissionEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }
    
    @Override
    public Optional<ExamSubmission> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }
    
    @Override
    public Optional<ExamSubmission> findByExamIdAndStudentId(UUID examId, UUID studentId) {
        return jpaRepository.findByExamIdAndStudentId(examId, studentId).map(mapper::toDomain);
    }
    
    @Override
    public List<ExamSubmission> findByExamId(UUID examId) {
        return jpaRepository.findByExamId(examId).stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
}
