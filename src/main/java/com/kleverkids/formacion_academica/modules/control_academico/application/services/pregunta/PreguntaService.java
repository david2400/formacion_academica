package com.kleverkids.formacion_academica.modules.control_academico.application.services.pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.pregunta.*;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.*;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.pregunta.PreguntaBanco;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.pregunta.PreguntaRepository;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.pregunta.PreguntaBancoRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.respuesta_pregunta.RespuestaPregunta;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PreguntaService implements 
        // Use Cases de Preguntas (interfaces existentes en inglés)
        CrearPreguntaUseCase, ConsultarPreguntaUseCase, ActualizarPreguntaUseCase,
        EliminarPreguntaUseCase, BuscarPreguntasUseCase, ValidarRespuestaUseCase,
        
        // Use Cases de Banco de Preguntas (español)
        CrearPreguntaBancoUseCase, ActualizarPreguntaBancoUseCase, ListarPreguntasPorTematicaUseCase,
        ConsultarPreguntaBancoUseCase, EliminarPreguntaBancoUseCase {
    
    // Repositories y Servicios
    private final PreguntaRepository preguntaRepository;
    private final PreguntaBancoRepositoryPort preguntaBancoRepositoryPort;
    private final ServicioValidacionRespuesta validationService;
    
    // ==================== USE CASES DE PREGUNTAS (INGLÉS) ====================
    
    @Override
    public RespuestaPregunta create(CreateQuestionCommand command) {
        log.info("Creando pregunta (inglés): {}", command);
        // TODO: Implementar cuando estén disponibles los DTOs correctos
        return null;
    }
    
    @Override
    public RespuestaPregunta consultarPorId(Long id) {
        log.info("Consultando pregunta por ID: {}", id);
        // TODO: Implementar cuando estén disponibles los DTOs correctos
        return null;
    }
    
    @Override
    public RespuestaPregunta actualizar(Long id, UpdateQuestionCommand command) {
        log.info("Actualizando pregunta ID: {}", id);
        // TODO: Implementar cuando estén disponibles los DTOs correctos
        return null;
    }
    
    @Override
    public void eliminar(Long id) {
        log.info("Eliminando pregunta ID: {}", id);
        // TODO: Implementar cuando esté disponible el repository
    }
    
    @Override
    public Page<RespuestaPregunta> buscar(CriterioBusquedaPregunta criterios, Pageable pageable) {
        log.info("Buscando preguntas con criterios: {}", criterios);
        // TODO: Implementar cuando esté disponible el repository
        return Page.empty();
    }
    
    @Override
    public ValidationResult validar(Long preguntaId, SolicitudValidacionRespuesta request) {
        log.info("Validando respuesta para pregunta ID: {}", preguntaId);
        // TODO: Implementar cuando esté disponible el servicio de validación
        return null;
    }
    
    // ==================== USE CASES DE BANCO DE PREGUNTAS (ESPAÑOL) ====================
    
    @Override
    public PreguntaBanco crear(CrearPreguntaBancoDto request) {
        log.info("Creando pregunta en banco: {}", request);
        return preguntaBancoRepositoryPort.guardar(request);
    }

    @Override
    public PreguntaBanco actualizar(ActualizarPreguntaBancoDto request) {
        log.info("Actualizando pregunta en banco ID: {}", request.id());
        return preguntaBancoRepositoryPort.actualizar(request);
    }

    @Override
    public List<PreguntaBanco> listar(Long tematicaId) {
        log.info("Listando preguntas por temática ID: {}", tematicaId);
        return preguntaBancoRepositoryPort.listarPorTematica(tematicaId);
    }

    @Override
    public PreguntaBanco consultarPreguntaBancoPorId(Long preguntaId) {
        log.info("Consultando pregunta de banco por ID: {}", preguntaId);
        return preguntaBancoRepositoryPort.obtenerPorId(preguntaId);
    }

    @Override
    public void eliminarPreguntaBanco(Long preguntaId) {
        log.info("Eliminando pregunta de banco ID: {}", preguntaId);
        preguntaBancoRepositoryPort.eliminar(preguntaId);
    }
}
