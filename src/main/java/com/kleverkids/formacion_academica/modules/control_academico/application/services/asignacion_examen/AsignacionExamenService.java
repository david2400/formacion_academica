package com.kleverkids.formacion_academica.modules.control_academico.application.services.asignacion_examen;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.asignacion_examen.*;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.asignacion_examen.AsignacionExamenRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asignacion_examen.*;
import com.kleverkids.formacion_academica.modules.control_academico.domain.exception.*;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers.AsignacionExamenMapper;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.asignacion_examen.AsignacionExamenEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AsignacionExamenService implements
    CrearAsignacionExamenUseCase,
    ActualizarAsignacionExamenUseCase,
    ConsultarAsignacionExamenUseCase,
    BuscarAsignacionesExamenUseCase,
    EliminarAsignacionExamenUseCase,
    CambiarEstadoAsignacionUseCase {
    
    private final AsignacionExamenRepositoryPort repository;
    private final AsignacionExamenMapper mapper;
    
    @Override
    @Transactional
    public AsignacionExamenDto crear(CrearAsignacionExamenDto dto) {
        log.info("Creando asignación de examen {} para clase {}", dto.getExamenId(), dto.getClaseId());
        
        // Validar fechas
        validarFechas(dto.getFechaInicio(), dto.getFechaFin());
        
        // Verificar que no exista asignación activa duplicada
        if (repository.existsByExamenIdAndClaseIdAndEstado(dto.getExamenId(), dto.getClaseId(), "PROGRAMADA") ||
            repository.existsByExamenIdAndClaseIdAndEstado(dto.getExamenId(), dto.getClaseId(), "ACTIVA")) {
            throw new AsignacionDuplicadaException(dto.getExamenId(), dto.getClaseId());
        }
        
        // TODO: Obtener grado y grupo desde el servicio de clases
        String grado = ""; // Implementar llamada al servicio de clases
        String grupo = ""; // Implementar llamada al servicio de clases
        
        AsignacionExamenEntity entity = mapper.toEntity(dto, grado, grupo);
        entity = repository.save(entity);
        
        log.info("Asignación de examen creada con ID: {}", entity.getId());
        
        // TODO: Obtener nombres de examen y clase
        return mapper.toDto(entity, "", "");
    }
    
    @Override
    @Transactional
    public AsignacionExamenDto actualizar(Long id, ActualizarAsignacionExamenDto dto) {
        log.info("Actualizando asignación de examen ID: {}", id);
        
        AsignacionExamenEntity entity = repository.findById(id)
            .orElseThrow(() -> new AsignacionExamenNotFoundException(id));
        
        // Validar fechas si se proporcionan
        if (dto.getFechaInicio() != null && dto.getFechaFin() != null) {
            validarFechas(dto.getFechaInicio(), dto.getFechaFin());
        }
        
        // Actualizar campos
        if (dto.getFechaAsignacion() != null) {
            entity.setFechaAsignacion(dto.getFechaAsignacion());
        }
        if (dto.getFechaInicio() != null) {
            entity.setFechaInicio(dto.getFechaInicio());
        }
        if (dto.getFechaFin() != null) {
            entity.setFechaFin(dto.getFechaFin());
        }
        if (dto.getDuracionMinutos() != null) {
            entity.setDuracionMinutos(dto.getDuracionMinutos());
        }
        if (dto.getIntentosPermitidos() != null) {
            entity.setIntentosPermitidos(dto.getIntentosPermitidos());
        }
        if (dto.getMostrarResultadosInmediatos() != null) {
            entity.setMostrarResultadosInmediatos(dto.getMostrarResultadosInmediatos());
        }
        if (dto.getPermitirRevision() != null) {
            entity.setPermitirRevision(dto.getPermitirRevision());
        }
        if (dto.getEstado() != null) {
            entity.setEstado(dto.getEstado());
        }
        
        entity = repository.save(entity);
        
        log.info("Asignación de examen actualizada: {}", id);
        
        return mapper.toDto(entity, "", "");
    }
    
    @Override
    @Transactional(readOnly = true)
    public AsignacionExamenDto consultarPorId(Long id) {
        log.info("Consultando asignación de examen ID: {}", id);
        
        AsignacionExamenEntity entity = repository.findById(id)
            .orElseThrow(() -> new AsignacionExamenNotFoundException(id));
        
        return mapper.toDto(entity, "", "");
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<AsignacionExamenDto> buscar(BuscarAsignacionesDto criterios, Pageable pageable) {
        log.info("Buscando asignaciones de examen con criterios: {}", criterios);
        
        Page<AsignacionExamenEntity> entities = repository.findAll(
            AsignacionExamenSpecifications.withCriteria(criterios),
            pageable
        );
        
        return entities.map(entity -> mapper.toDto(entity, "", ""));
    }
    
    @Override
    @Transactional
    public void eliminar(Long id) {
        log.info("Eliminando asignación de examen ID: {}", id);
        
        if (!repository.existsById(id)) {
            throw new AsignacionExamenNotFoundException(id);
        }
        
        repository.deleteById(id);
        
        log.info("Asignación de examen eliminada: {}", id);
    }
    
    @Override
    @Transactional
    public AsignacionExamenDto cambiarEstado(Long id, String nuevoEstado) {
        log.info("Cambiando estado de asignación {} a {}", id, nuevoEstado);
        
        AsignacionExamenEntity entity = repository.findById(id)
            .orElseThrow(() -> new AsignacionExamenNotFoundException(id));
        
        entity.setEstado(nuevoEstado);
        entity = repository.save(entity);
        
        log.info("Estado de asignación cambiado: {} -> {}", id, nuevoEstado);
        
        return mapper.toDto(entity, "", "");
    }
    
    private void validarFechas(java.time.LocalDateTime inicio, java.time.LocalDateTime fin) {
        if (inicio.isAfter(fin)) {
            throw new FechasInvalidasException();
        }
    }
}
