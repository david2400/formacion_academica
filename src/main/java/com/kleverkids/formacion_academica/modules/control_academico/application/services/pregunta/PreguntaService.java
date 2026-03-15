package com.kleverkids.formacion_academica.modules.control_academico.application.services.pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.pregunta.*;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.*;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.pregunta.QuestionEventPublisher;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.pregunta.QuestionRepository;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.pregunta.PreguntaBancoRepositoryPort;
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

import java.util.List;


@RequiredArgsConstructor
@Service
@Transactional
public class PreguntaService implements CrearPreguntaUseCase, ConsultarPreguntaUseCase, ActualizarPreguntaUseCase,
        EliminarPreguntaUseCase, BuscarPreguntasUseCase, ValidarRespuestaUseCase,
        CrearPreguntaBancoUseCase, ActualizarPreguntaBancoUseCase, ListarPreguntasPorTematicaUseCase,
        ConsultarPreguntaBancoUseCase, EliminarPreguntaBancoUseCase {
    
    // Use Cases del sistema en inglés
    private final QuestionRepository questionRepository;
    private final QuestionEventPublisher eventPublisher;
    private final QuestionMapper questionMapper;
    private final ServicioValidacionRespuesta validationService;
    
    // Use Cases del sistema en español
    private final PreguntaBancoRepositoryPort preguntaBancoRepositoryPort;
    
    // Implementación de Use Cases en inglés
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
    public QuestionResponse actualizar(Long id, UpdateQuestionCommand command) {
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
    public QuestionResponse consultarPorId(Long id) {
        Question question = questionRepository.findById(id)
            .orElseThrow(() -> new QuestionNotFoundException(id));
        return QuestionResponse.fromDomain(question);
    }
    
    public void delete(Long id) {
        Question question = questionRepository.findById(id)
            .orElseThrow(() -> new QuestionNotFoundException(id));
        
        question.markAsDeleted();
        questionRepository.save(question);
        
        eventPublisher.publish(new QuestionDeletedEvent(id));
    }
    
    @Transactional(readOnly = true)
    public Page<QuestionResponse> search(QuestionSearchCriteria criteria, Pageable pageable) {
        return questionRepository.search(criteria, pageable)
            .map(QuestionResponse::fromDomain);
    }
    
    @Transactional(readOnly = true)
    public ValidationResult validate(Long questionId, AnswerValidationRequest request) {
        Question question = questionRepository.findById(questionId)
            .orElseThrow(() -> new QuestionNotFoundException(questionId));
        return validationService.validate(question, request);
    }
    
    @Override
    public void eliminar(Long id) {
        Question question = questionRepository.findById(id)
            .orElseThrow(() -> new QuestionNotFoundException(id));
        
        question.markAsDeleted();
        questionRepository.save(question);
        
        eventPublisher.publish(new QuestionDeletedEvent(id));
    }
    
    // Implementación de Use Cases en español
    @Override
    public PreguntaBancoDto crear(CrearPreguntaBancoDto request) {
        return preguntaBancoRepositoryPort.guardar(request);
    }

    @Override
    public PreguntaBancoDto actualizar(ActualizarPreguntaBancoDto request) {
        return preguntaBancoRepositoryPort.actualizar(request);
    }

    @Override
    public List<PreguntaBancoDto> listar(Long tematicaId) {
        return preguntaBancoRepositoryPort.listarPorTematica(tematicaId);
    }

    @Override
    public void eliminarPreguntaBanco(Long preguntaId) {
        preguntaBancoRepositoryPort.obtenerPorId(preguntaId);
        preguntaBancoRepositoryPort.eliminar(preguntaId);
    }
    
    // Implementación de ConsultarPreguntaBancoUseCase
    @Override
    public PreguntaBancoDto consultarPreguntaBancoPorId(Long preguntaId) {
        return preguntaBancoRepositoryPort.obtenerPorId(preguntaId);
    }
    
    @Override
    public Page<QuestionResponse> buscar(QuestionSearchCriteria criterios, Pageable pageable) {
        return search(criterios, pageable);
    }
    
    @Override
    public ValidationResult validar(Long preguntaId, AnswerValidationRequest request) {
        return validate(preguntaId, request);
    }
}
