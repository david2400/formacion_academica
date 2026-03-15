package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.pregunta.QuestionRepository;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.pregunta.PreguntaBancoRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.ActualizarPreguntaBancoDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.CrearPreguntaBancoDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.PreguntaBancoDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.QuestionSearchCriteria;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.pregunta.Question;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers.PreguntaBancoMapper;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers.QuestionPersistenceMapper;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.pregunta.PreguntaBancoEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.pregunta.QuestionEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository.PreguntaBancoJpaRepository;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository.QuestionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class PreguntaJpaAdapter implements QuestionRepository, PreguntaBancoRepositoryPort {

    // Repositorios del sistema en inglés
    private final QuestionJpaRepository questionJpaRepository;
    private final QuestionPersistenceMapper questionPersistenceMapper;
    
    // Repositorios del sistema en español
    private final PreguntaBancoJpaRepository preguntaBancoJpaRepository;
    private final PreguntaBancoMapper preguntaBancoMapper;

    // Implementación de QuestionRepository (inglés)
    @Override
    public Question save(Question question) {
        QuestionEntity entity = questionPersistenceMapper.toEntity(question);
        QuestionEntity saved = questionJpaRepository.save(entity);
        return questionPersistenceMapper.toDomain(saved);
    }
    
    @Override
    public Optional<Question> findById(Long id) {
        return questionJpaRepository.findById(id)
            .map(questionPersistenceMapper::toDomain);
    }
    
    @Override
    public void deleteById(Long id) {
        questionJpaRepository.deleteById(id);
    }
    
    @Override
    public Page<Question> search(QuestionSearchCriteria criteria, Pageable pageable) {
        Page<QuestionEntity> entities;
        
        if (Boolean.TRUE.equals(criteria.includeDeleted())) {
            entities = questionJpaRepository.searchWithDeleted(
                criteria.questionType(),
                criteria.difficulty(),
                criteria.themeId(),
                criteria.searchText(),
                true,
                pageable
            );
        } else {
            entities = questionJpaRepository.search(
                criteria.questionType(),
                criteria.difficulty(),
                criteria.themeId(),
                criteria.searchText(),
                pageable
            );
        }
        
        return entities.map(questionPersistenceMapper::toDomain);
    }
    
    @Override
    public boolean existsById(Long id) {
        return questionJpaRepository.existsById(id);
    }

    // Implementación de PreguntaBancoRepositoryPort (español)
    @Override
    public PreguntaBancoDto guardar(CrearPreguntaBancoDto request) {
        PreguntaBancoEntity entity = preguntaBancoMapper.toEntity(request);
        return preguntaBancoMapper.toDto(preguntaBancoJpaRepository.save(entity));
    }

    @Override
    public PreguntaBancoDto actualizar(ActualizarPreguntaBancoDto request) {
        PreguntaBancoEntity entity = preguntaBancoJpaRepository.findById(request.id())
                .orElseThrow(() -> new IllegalArgumentException("Pregunta no encontrada"));
        preguntaBancoMapper.applyUpdate(entity, request);
        return preguntaBancoMapper.toDto(preguntaBancoJpaRepository.save(entity));
    }

    @Override
    public List<PreguntaBancoDto> listarPorTematica(Long tematicaId) {
        return preguntaBancoMapper.toDtoList(preguntaBancoJpaRepository.findByTematicaId(tematicaId));
    }

    @Override
    public PreguntaBancoDto obtenerPorId(Long preguntaId) {
        return preguntaBancoJpaRepository.findById(preguntaId)
                .map(preguntaBancoMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Pregunta no encontrada"));
    }

    @Override
    public void eliminar(Long preguntaId) {
        preguntaBancoJpaRepository.deleteById(preguntaId);
    }
}
