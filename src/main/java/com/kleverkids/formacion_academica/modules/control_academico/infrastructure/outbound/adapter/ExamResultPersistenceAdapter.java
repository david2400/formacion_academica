package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository.ExamResultRepository;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.ExamResult;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository.ExamResultJpaRepository;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.ExamResultEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ExamResultPersistenceAdapter implements ExamResultRepository {
    
    private final ExamResultJpaRepository jpaRepository;
    private final ExamPersistenceMapper mapper;
    
    public ExamResultPersistenceAdapter(ExamResultJpaRepository jpaRepository, ExamPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }
    
    @Override
    public ExamResult save(ExamResult result) {
        ExamResultEntity entity = mapper.toEntity(result);
        ExamResultEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }
    
    @Override
    public Optional<ExamResult> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }
    
    @Override
    public Optional<ExamResult> findByExamIdAndStudentId(UUID examId, UUID studentId) {
        return jpaRepository.findByExamIdAndStudentId(examId, studentId).map(mapper::toDomain);
    }
    
    @Override
    public List<ExamResult> findByExamId(UUID examId) {
        return jpaRepository.findByExamId(examId).stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
}
