package com.kleverkids.formacion_academica.modules.questions.infrastructure.adapter.out.persistence;

import com.kleverkids.formacion_academica.modules.questions.application.dto.QuestionSearchCriteria;
import com.kleverkids.formacion_academica.modules.questions.application.port.out.QuestionRepository;
import com.kleverkids.formacion_academica.modules.questions.domain.model.Question;
import com.kleverkids.formacion_academica.modules.questions.infrastructure.adapter.out.persistence.entity.QuestionEntity;
import com.kleverkids.formacion_academica.modules.questions.infrastructure.adapter.out.persistence.mapper.QuestionPersistenceMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class QuestionPersistenceAdapter implements QuestionRepository {
    
    private final QuestionJpaRepository jpaRepository;
    private final QuestionPersistenceMapper mapper;
    
    public QuestionPersistenceAdapter(QuestionJpaRepository jpaRepository, 
                                       QuestionPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }
    
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
