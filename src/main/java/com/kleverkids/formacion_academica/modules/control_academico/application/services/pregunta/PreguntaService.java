package com.kleverkids.formacion_academica.modules.control_academico.application.services.pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.pregunta.*;
import com.kleverkids.formacion_academica.modules.control_academico.application.services.pregunta.factory.PreguntaEntityFactory;
import com.kleverkids.formacion_academica.modules.control_academico.application.services.pregunta.mapper.PreguntaMapper;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.*;
import com.kleverkids.formacion_academica.modules.control_academico.domain.exception.PreguntaNotFoundException;
import com.kleverkids.formacion_academica.modules.control_academico.domain.exception.TipoPreguntaInmutableException;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta.PreguntaEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository.PreguntaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PreguntaService implements 
        GuardarPreguntaUseCase,
        EliminarPreguntaUseCase,
        ClonarPreguntaUseCase,
        CambiarDificultadUseCase,
        AsignarTemaUseCase {
    
    private final PreguntaRepository repository;
    private final Map<String, PreguntaEntityFactory> factories;
    private final PreguntaMapper mapper;
    
    @Override
    @Transactional
    public PreguntaDto guardar(Long id, CreateQuestionCommand command) {
        log.info("Guardando pregunta - ID: {}, Tipo: {}", id, command.questionType());
        
        validarCommand(command);
        
        String tipo = command.questionType();
        PreguntaEntityFactory factory = obtenerFactory(tipo);
        
        PreguntaEntity entity = (id == null) 
            ? crearNuevaPregunta(factory, command, tipo)
            : actualizarPreguntaExistente(id, factory, command, tipo);
        
        entity = repository.save(entity);
        log.info("Pregunta guardada exitosamente con ID: {}", entity.getId());
        
        return mapper.toDto(entity);
    }
    
    private void validarCommand(CreateQuestionCommand command) {
        if (command == null) {
            throw new IllegalArgumentException("El comando no puede ser nulo");
        }
        if (command.questionType() == null || command.questionType().isBlank()) {
            throw new IllegalArgumentException("El tipo de pregunta es obligatorio");
        }
        if (command.questionText() == null || command.questionText().isBlank()) {
            throw new IllegalArgumentException("El texto de la pregunta es obligatorio");
        }
    }
    
    private PreguntaEntityFactory obtenerFactory(String tipo) {
        PreguntaEntityFactory factory = factories.get(tipo);
        if (factory == null) {
            log.error("Tipo de pregunta no soportado: {}. Tipos disponibles: {}", tipo, factories.keySet());
            throw new IllegalArgumentException(
                String.format("Tipo de pregunta no soportado: %s. Tipos disponibles: %s", 
                    tipo, factories.keySet())
            );
        }
        return factory;
    }
    
    private PreguntaEntity crearNuevaPregunta(PreguntaEntityFactory factory, CreateQuestionCommand command, String tipo) {
        log.debug("Creando nueva pregunta de tipo: {}", tipo);
        return factory.crear(command);
    }
    
    private PreguntaEntity actualizarPreguntaExistente(Long id, PreguntaEntityFactory factory, CreateQuestionCommand command, String tipo) {
        log.debug("Actualizando pregunta existente ID: {}", id);
        
        PreguntaEntity entity = repository.findById(id)
            .orElseThrow(() -> new PreguntaNotFoundException(id));
        
        validarCambioTipo(entity, tipo);
        
        return factory.actualizar(entity, command);
    }
    
    private void validarCambioTipo(PreguntaEntity entity, String nuevoTipo) {
        // Obtener el tipo actual desde el factory que corresponde a la clase de la entidad
        String tipoActual = factories.values().stream()
            .filter(f -> f.crear(null).getClass().equals(entity.getClass()))
            .map(PreguntaEntityFactory::getTipoPregunta)
            .findFirst()
            .orElse(entity.getClass().getSimpleName());
            
        if (!tipoActual.equals(nuevoTipo)) {
            log.warn("Intento de cambiar tipo de pregunta de {} a {} para ID: {}", 
                tipoActual, nuevoTipo, entity.getId());
            throw new TipoPreguntaInmutableException();
        }
    }
    
    public PreguntaDto consultarPorId(Long id) {
        log.info("Consultando pregunta por ID: {}", id);
        
        PreguntaEntity entity = repository.findById(id)
            .orElseThrow(() -> new PreguntaNotFoundException(id));
        
        return mapper.toDto(entity);
    }
    
    @Override
    public void eliminar(Long id) {
        log.info("Eliminando pregunta ID: {}", id);
        
        if (!repository.existsById(id)) {
            throw new PreguntaNotFoundException(id);
        }
        
        repository.deleteById(id);
        log.info("Pregunta eliminada exitosamente: {}", id);
    }
    
    @Transactional(readOnly = true)
    public List<PreguntaDto> buscar(CriterioBusquedaPregunta criterios) {
        log.info("Buscando preguntas con criterios: {}", criterios);
        
        List<PreguntaEntity> entities = buscarEntidades(criterios);
        
        return entities.stream()
            .map(mapper::toDto)
            .collect(Collectors.toList());
    }
    
    private List<PreguntaEntity> buscarEntidades(CriterioBusquedaPregunta criterios) {
        if (criterios.temaId() != null) {
            log.debug("Buscando por tema ID: {}", criterios.temaId());
            return repository.findByTemaId(criterios.temaId());
        }
        
        if (criterios.dificultad() != null) {
            log.debug("Buscando por dificultad: {}", criterios.dificultad());
            return repository.findByDificultad(criterios.dificultad());
        }
        
        if (criterios.etiquetas() != null && !criterios.etiquetas().isEmpty()) {
            log.debug("Buscando por etiqueta: {}", criterios.etiquetas().get(0));
            return repository.findByTag(criterios.etiquetas().get(0));
        }
        
        log.debug("Buscando todas las preguntas");
        return repository.findAll();
    }
    
    @Override
    public PreguntaDto clonar(Long preguntaId) {
        log.info("Clonando pregunta ID: {}", preguntaId);
        
        PreguntaEntity original = repository.findById(preguntaId)
            .orElseThrow(() -> new PreguntaNotFoundException(preguntaId));
        
        PreguntaEntity clon = clonarEntidad(original);
        clon = repository.save(clon);
        
        log.info("Pregunta clonada exitosamente. Original: {}, Clon: {}", preguntaId, clon.getId());
        return mapper.toDto(clon);
    }
    
    @Override
    public PreguntaDto cambiarDificultad(Long preguntaId, String nuevaDificultad) {
        log.info("Cambiando dificultad de pregunta ID: {} a {}", preguntaId, nuevaDificultad);
        
        PreguntaEntity entity = repository.findById(preguntaId)
            .orElseThrow(() -> new PreguntaNotFoundException(preguntaId));
        
        entity.setDificultad(nuevaDificultad);
        entity = repository.save(entity);
        
        return mapper.toDto(entity);
    }
    
    @Override
    public PreguntaDto asignarTema(Long preguntaId, Long temaId) {
        log.info("Asignando tema {} a pregunta ID: {}", temaId, preguntaId);
        
        PreguntaEntity entity = repository.findById(preguntaId)
            .orElseThrow(() -> new PreguntaNotFoundException(preguntaId));
        
        entity.setTemaId(temaId);
        entity = repository.save(entity);
        
        return mapper.toDto(entity);
    }
    
    private PreguntaEntity clonarEntidad(PreguntaEntity original) {
        try {
            PreguntaEntity clon = original.getClass().getDeclaredConstructor().newInstance();
            clon.setTextoPregunta(original.getTextoPregunta() + " (Copia)");
            clon.setDificultad(original.getDificultad());
            clon.setPuntajeMaximo(original.getPuntajeMaximo());
            clon.setTemaId(original.getTemaId());
            clon.setHint(original.getHint());
            clon.setExplanation(original.getExplanation());
            clon.setTags(original.getTags());
            clon.setMetadata(original.getMetadata());
            clon.setMedia(original.getMedia());
            
            return clon;
        } catch (Exception e) {
            throw new RuntimeException("Error al clonar pregunta", e);
        }
    }
}
