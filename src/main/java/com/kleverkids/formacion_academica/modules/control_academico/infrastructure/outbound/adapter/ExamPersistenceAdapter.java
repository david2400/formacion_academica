package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.ExamSearchCriteria;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository.ExamRepository;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.Exam;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository.ExamJpaRepository;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.ExamEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class ExamPersistenceAdapter implements ExamRepository {
    
    private final ExamJpaRepository jpaRepository;
    private final ExamPersistenceMapper mapper;
    
    public ExamPersistenceAdapter(ExamJpaRepository jpaRepository, ExamPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }
    
    @Override
    public Exam save(Exam exam) {
        ExamEntity entity = mapper.toEntity(exam);
        ExamEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }
    
    @Override
    public Optional<Exam> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }
    
    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }
    
    @Override
    public Page<Exam> search(ExamSearchCriteria criteria, Pageable pageable) {
        Page<ExamEntity> entities = jpaRepository.search(
            criteria.status(),
            criteria.subject(),
            criteria.gradeLevel(),
            criteria.searchText(),
            pageable
        );
        return entities.map(mapper::toDomain);
    }
    
    @Override
    public boolean existsById(UUID id) {
        return jpaRepository.existsById(id);
    }
}
