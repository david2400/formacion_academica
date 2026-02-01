package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.QuestionSearchCriteria;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.pregunta.QuestionRepository;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.pregunta.Question;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.pregunta.QuestionEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository.QuestionJpaRepository;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers.QuestionPersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class QuestionPersistenceJpaAdapter implements QuestionRepository {
    
    private final QuestionJpaRepository jpaRepository;
    private final QuestionPersistenceMapper mapper;

    @Override
    public Question save(Question question) {
        QuestionEntity entity = mapper.toEntity(question);
        QuestionEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }
    
    @Override
    public Optional<Question> findById(UUID id) {
        return jpaRepository.findById(id)
            .map(mapper::toDomain);
    }
    
    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }
    
    @Override
    public Page<Question> search(QuestionSearchCriteria criteria, Pageable pageable) {
        Page<QuestionEntity> entities;
        
        if (Boolean.TRUE.equals(criteria.includeDeleted())) {
            entities = jpaRepository.searchWithDeleted(
                criteria.questionType(),
                criteria.difficulty(),
                criteria.themeId(),
                criteria.searchText(),
                true,
                pageable
            );
        } else {
            entities = jpaRepository.search(
                criteria.questionType(),
                criteria.difficulty(),
                criteria.themeId(),
                criteria.searchText(),
                pageable
            );
        }
        
        return entities.map(mapper::toDomain);
    }
    
    @Override
    public boolean existsById(UUID id) {
        return jpaRepository.existsById(id);
    }
}
