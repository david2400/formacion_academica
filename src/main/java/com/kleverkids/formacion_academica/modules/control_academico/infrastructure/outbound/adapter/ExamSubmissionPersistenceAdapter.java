package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository.ExamSubmissionRepository;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.ExamSubmission;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository.ExamSubmissionJpaRepository;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.ExamSubmissionEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ExamSubmissionPersistenceAdapter implements ExamSubmissionRepository {
    
    private final ExamSubmissionJpaRepository jpaRepository;
    private final ExamPersistenceMapper mapper;
    
    public ExamSubmissionPersistenceAdapter(ExamSubmissionJpaRepository jpaRepository, ExamPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }
    
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
