package com.kleverkids.formacion_academica.modules.control_academico.application.services.pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.pregunta.*;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.*;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.pregunta.QuestionEventPublisher;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.pregunta.QuestionRepository;
import com.kleverkids.formacion_academica.modules.control_academico.domain.events.pregunta.QuestionCreatedEvent;
import com.kleverkids.formacion_academica.modules.control_academico.domain.events.pregunta.QuestionDeletedEvent;
import com.kleverkids.formacion_academica.modules.control_academico.domain.events.pregunta.QuestionUpdatedEvent;
import com.kleverkids.formacion_academica.modules.control_academico.domain.exception.QuestionNotFoundException;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.pregunta.Question;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers.QuestionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class QuestionService implements CreateQuestionUseCase, UpdateQuestionUseCase,
        GetQuestionUseCase, DeleteQuestionUseCase, SearchQuestionsUseCase, ValidateAnswerUseCase {
    
    private final QuestionRepository questionRepository;
    private final QuestionEventPublisher eventPublisher;
    private final QuestionMapper questionMapper;
    private final AnswerValidationService validationService;

    
    @Override
    public QuestionResponse create(CreateQuestionCommand command) {
        Question question = questionMapper.toDomain(command);
        question.validate();
        
        Question saved = questionRepository.save(question);
        
        eventPublisher.publish(new QuestionCreatedEvent(
            saved.getId(), 
            saved.getQuestionType(), 
            saved.getThemeId()
        ));
        
        return QuestionResponse.fromDomain(saved);
    }
    
    @Override
    public QuestionResponse update(UUID id, UpdateQuestionCommand command) {
        Question question = questionRepository.findById(id)
            .orElseThrow(() -> new QuestionNotFoundException(id));
        
        questionMapper.updateDomain(question, command);
        question.incrementVersion();
        question.validate();
        
        Question saved = questionRepository.save(question);
        
        eventPublisher.publish(new QuestionUpdatedEvent(saved.getId()));
        
        return QuestionResponse.fromDomain(saved);
    }
    
    @Override
    @Transactional(readOnly = true)
    public QuestionResponse getById(UUID id) {
        Question question = questionRepository.findById(id)
            .orElseThrow(() -> new QuestionNotFoundException(id));
        return QuestionResponse.fromDomain(question);
    }
    
    @Override
    public void delete(UUID id) {
        Question question = questionRepository.findById(id)
            .orElseThrow(() -> new QuestionNotFoundException(id));
        
        question.markAsDeleted();
        questionRepository.save(question);
        
        eventPublisher.publish(new QuestionDeletedEvent(id));
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<QuestionResponse> search(QuestionSearchCriteria criteria, Pageable pageable) {
        return questionRepository.search(criteria, pageable)
            .map(QuestionResponse::fromDomain);
    }
    
    @Override
    @Transactional(readOnly = true)
    public ValidationResult validate(UUID questionId, AnswerValidationRequest request) {
        Question question = questionRepository.findById(questionId)
            .orElseThrow(() -> new QuestionNotFoundException(questionId));
        return validationService.validate(question, request);
    }
}
