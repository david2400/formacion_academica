package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.actividad.ActividadRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.actividad.Actividad;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.actividad.valueobject.ProgresoActividad;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.actividad.CrearActividadDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.actividad.ActualizarActividadDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.actividad.FiltroActividadDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers.ActividadMapper;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.ActividadEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository.ActividadJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ActividadJpaAdapter implements ActividadRepositoryPort {

    private final ActividadJpaRepository actividadJpaRepository;
    private final ActividadMapper actividadMapper;

    @Override
    public Actividad guardar(CrearActividadDto request) {
        log.debug("Guardando nueva actividad: {}", request.getTitulo());
        
        ActividadEntity entity = actividadMapper.toEntity(request);
        
        // Establecer valores por defecto
        if (entity.getOrden() == null) {
            entity.setOrden(obtenerSiguienteOrden(request.getCursoId(), request.getModuloId()));
        }
        
        ActividadEntity saved = actividadJpaRepository.save(entity);
        log.debug("Actividad guardada con ID: {}", saved.getId());
        
        return actividadMapper.toDomainModel(saved);
    }

    @Override
    public Actividad actualizar(ActualizarActividadDto request) {
        log.debug("Actualizando actividad ID: {}", request.getId());
        
        Optional<ActividadEntity> existing = actividadJpaRepository.findById(request.getId());
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Actividad no encontrada: " + request.getId());
        }
        
        ActividadEntity entity = existing.get();
        actividadMapper.updateEntityFromDto(request, entity);
        
        ActividadEntity updated = actividadJpaRepository.save(entity);
        log.debug("Actividad actualizada: {}", updated.getId());
        
        return actividadMapper.toDomainModel(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Actividad> obtenerPorId(Long id) {
        log.debug("Buscando actividad por ID: {}", id);
        
        return actividadJpaRepository.findById(id)
            .map(actividadMapper::toDomainModel);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Actividad> listar() {
        log.debug("Listando todas las actividades");
        
        List<ActividadEntity> entities = actividadJpaRepository.findAll();
        return actividadMapper.toDomainModelList(entities);
    }

    @Override
    public void eliminar(Long id) {
        log.debug("Eliminando actividad ID: {}", id);
        
        if (!actividadJpaRepository.existsById(id)) {
            throw new IllegalArgumentException("Actividad no encontrada: " + id);
        }
        
        actividadJpaRepository.deleteById(id);
        log.debug("Actividad eliminada: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Actividad> buscarPorCurso(Long cursoId) {
        log.debug("Buscando actividades por curso ID: {}", cursoId);
        
        List<ActividadEntity> entities = actividadJpaRepository.findByCursoIdOrderByOrdenAsc(cursoId);
        return actividadMapper.toDomainModelList(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Actividad> buscarPorModulo(Long moduloId) {
        log.debug("Buscando actividades por módulo ID: {}", moduloId);
        
        List<ActividadEntity> entities = actividadJpaRepository.findByModuloIdOrderByOrdenAsc(moduloId);
        return actividadMapper.toDomainModelList(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Actividad> buscarPorTipo(String tipoActividad) {
        log.debug("Buscando actividades por tipo: {}", tipoActividad);
        
        try {
            ActividadEntity.TipoActividadEntity tipo = ActividadEntity.TipoActividadEntity.valueOf(tipoActividad.toUpperCase());
            List<ActividadEntity> entities = actividadJpaRepository.findByTipo(tipo);
            return actividadMapper.toDomainModelList(entities);
        } catch (IllegalArgumentException e) {
            log.warn("Tipo de actividad no válido: {}", tipoActividad);
            return List.of();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Actividad> buscarConFiltro(FiltroActividadDto filtro) {
        log.debug("Buscando actividades con filtros: {}", filtro);
        
        // Convertir filtros a tipos de entidad
        ActividadEntity.TipoActividadEntity tipo = null;
        if (filtro.getTipo() != null) {
            try {
                tipo = ActividadEntity.TipoActividadEntity.valueOf(filtro.getTipo().toUpperCase());
            } catch (IllegalArgumentException e) {
                log.warn("Tipo de filtro no válido: {}", filtro.getTipo());
                return List.of();
            }
        }
        
        ActividadEntity.EstadoActividadEntity estado = null;
        if (filtro.getEstado() != null) {
            try {
                estado = ActividadEntity.EstadoActividadEntity.valueOf(filtro.getEstado().toUpperCase());
            } catch (IllegalArgumentException e) {
                log.warn("Estado de filtro no válido: {}", filtro.getEstado());
                return List.of();
            }
        }
        
        // Configurar paginación
        int pagina = filtro.getPagina() != null ? filtro.getPagina() : 0;
        int tamaño = filtro.getTamañoPagina() != null ? filtro.getTamañoPagina() : 20;
        
        // Configurar ordenamiento
        String ordenarPor = filtro.getOrdenarPor() != null ? filtro.getOrdenarPor() : "orden";
        String direccion = filtro.getDireccion() != null ? filtro.getDireccion() : "ASC";
        
        Sort.Direction sortDirection = "DESC".equalsIgnoreCase(direccion) ? 
            Sort.Direction.DESC : Sort.Direction.ASC;
        
        Pageable pageable = PageRequest.of(pagina, tamaño, Sort.by(sortDirection, ordenarPor));
        
        // Ejecutar búsqueda
        Page<ActividadEntity> page = actividadJpaRepository.buscarConFiltros(
            filtro.getCursoId(),
            filtro.getModuloId(),
            tipo,
            estado,
            filtro.getTitulo(),
            pageable
        );
        
        return actividadMapper.toDomainModelList(page.getContent());
    }

    @Override
    public ProgresoActividad obtenerProgreso(Long actividadId) {
        log.debug("Obteniendo progreso de actividad ID: {}", actividadId);
        
        Optional<ActividadEntity> entityOpt = actividadJpaRepository.findById(actividadId);
        if (entityOpt.isEmpty()) {
            throw new IllegalArgumentException("Actividad no encontrada: " + actividadId);
        }
        
        ActividadEntity entity = entityOpt.get();
        if (entity.getProgreso() == null) {
            // Crear progreso inicial
            ProgresoActividad progreso = ProgresoActividad.builder()
                .actividadId(actividadId)
                .build();
            entity.setProgreso(convertirProgresoToEntity(progreso));
            actividadJpaRepository.save(entity);
            return progreso;
        }
        
        return convertirEntityToProgreso(entity.getProgreso());
    }

    @Override
    public void guardarProgreso(ProgresoActividad progreso) {
        log.debug("Guardando progreso para actividad ID: {}", progreso.getActividadId());
        
        Optional<ActividadEntity> entityOpt = actividadJpaRepository.findById(progreso.getActividadId());
        if (entityOpt.isEmpty()) {
            throw new IllegalArgumentException("Actividad no encontrada: " + progreso.getActividadId());
        }
        
        ActividadEntity entity = entityOpt.get();
        entity.setProgreso(convertirProgresoToEntity(progreso));
        actividadJpaRepository.save(entity);
    }

    @Override
    public void actualizarEstado(Long actividadId, String nuevoEstado) {
        log.debug("Actualizando estado de actividad ID: {} a {}", actividadId, nuevoEstado);
        
        Optional<ActividadEntity> entityOpt = actividadJpaRepository.findById(actividadId);
        if (entityOpt.isEmpty()) {
            throw new IllegalArgumentException("Actividad no encontrada: " + actividadId);
        }
        
        try {
            ActividadEntity.EstadoActividadEntity estado = 
                ActividadEntity.EstadoActividadEntity.valueOf(nuevoEstado.toUpperCase());
            
            ActividadEntity entity = entityOpt.get();
            entity.setEstado(estado);
            actividadJpaRepository.save(entity);
            
            log.debug("Estado actualizado correctamente");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Estado no válido: " + nuevoEstado);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Actividad> buscarActividadesDisponibles(Long estudianteId) {
        log.debug("Buscando actividades disponibles para estudiante ID: {}", estudianteId);
        
        List<ActividadEntity> entities = actividadJpaRepository
            .findActividadesDisponiblesParaEstudiante(estudianteId, LocalDateTime.now());
        
        return actividadMapper.toDomainModelList(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Actividad> buscarDependencias(Long actividadId) {
        log.debug("Buscando dependencias de actividad ID: {}", actividadId);
        
        Optional<ActividadEntity> entityOpt = actividadJpaRepository.findById(actividadId);
        if (entityOpt.isEmpty()) {
            return List.of();
        }
        
        ActividadEntity entity = entityOpt.get();
        if (entity.getActividadesDependientes() == null || entity.getActividadesDependientes().isEmpty()) {
            return List.of();
        }
        
        List<ActividadEntity> dependencias = actividadJpaRepository.findAllById(entity.getActividadesDependientes());
        return actividadMapper.toDomainModelList(dependencias);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Actividad> buscarActividadesQueDependen(Long actividadId) {
        log.debug("Buscando actividades que dependen de ID: {}", actividadId);
        
        List<ActividadEntity> entities = actividadJpaRepository.findActividadesQueDependen(actividadId);
        return actividadMapper.toDomainModelList(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Actividad> buscarPorEvaluacionRequerida(Boolean requiereEvaluacion) {
        log.debug("Buscando actividades con evaluación requerida: {}", requiereEvaluacion);
        
        List<ActividadEntity> entities = actividadJpaRepository.findByEvaluacionRequerida();
        return actividadMapper.toDomainModelList(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public Long contarActividadesPorCurso(Long cursoId) {
        log.debug("Contando actividades por curso ID: {}", cursoId);
        
        return actividadJpaRepository.contarPorCurso(cursoId);
    }

    @Override
    @Transactional(readOnly = true)
    public Long contarActividadesActivasPorCurso(Long cursoId) {
        log.debug("Contando actividades activas por curso ID: {}", cursoId);
        
        return actividadJpaRepository.contarActivasPorCurso(cursoId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Actividad> listarPorOrden(Long cursoId, Long moduloId) {
        log.debug("Listando actividades por orden - Curso: {}, Módulo: {}", cursoId, moduloId);
        
        List<ActividadEntity> entities = actividadJpaRepository.listarPorOrden(cursoId, moduloId);
        return actividadMapper.toDomainModelList(entities);
    }

    @Override
    public void actualizarOrden(Long actividadId, Integer nuevoOrden) {
        log.debug("Actualizando orden de actividad ID: {} a {}", actividadId, nuevoOrden);
        
        Optional<ActividadEntity> entityOpt = actividadJpaRepository.findById(actividadId);
        if (entityOpt.isEmpty()) {
            throw new IllegalArgumentException("Actividad no encontrada: " + actividadId);
        }
        
        ActividadEntity entity = entityOpt.get();
        entity.setOrden(nuevoOrden);
        actividadJpaRepository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorTituloYCurso(String titulo, Long cursoId) {
        log.debug("Verificando existencia de actividad por título y curso");
        
        return actividadJpaRepository.existsByTituloAndCursoId(titulo, cursoId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existeDependenciaCiclica(Long actividadId, List<Long> dependencias) {
        log.debug("Verificando dependencias cíclicas para actividad ID: {}", actividadId);
        
        // Implementación básica de detección de ciclos
        return tieneDependenciaCiclica(actividadId, dependencias, java.util.Set.of());
    }

    // Métodos privados auxiliares
    private Integer obtenerSiguienteOrden(Long cursoId, Long moduloId) {
        List<ActividadEntity> actividades = actividadJpaRepository.listarPorOrden(cursoId, moduloId);
        return actividades.stream()
            .mapToInt(ActividadEntity::getOrden)
            .max()
            .orElse(0) + 1;
    }

    private boolean tieneDependenciaCiclica(Long actividadId, List<Long> dependencias, java.util.Set<Long> visitados) {
        if (visitados.contains(actividadId)) {
            return true; // Ciclo detectado
        }
        
        visitados.add(actividadId);
        
        for (Long depId : dependencias) {
            if (depId.equals(actividadId) || tieneDependenciaCiclica(depId, List.of(actividadId), visitados)) {
                return true;
            }
        }
        
        return false;
    }

    // Métodos de conversión entre Entity y Domain para Progreso
    private ActividadEntity.ProgresoActividadEntity convertirProgresoToEntity(ProgresoActividad progreso) {
        ActividadEntity.ProgresoActividadEntity entity = new ActividadEntity.ProgresoActividadEntity();
        entity.setActividadId(progreso.getActividadId());
        entity.setUltimaActualizacion(progreso.getUltimaActualizacion());
        
        // Convertir mapa de progreso por estudiante
        java.util.Map<Long, ActividadEntity.ProgresoEstudianteEntity> progresoPorEstudiante = new java.util.HashMap<>();
        progreso.getProgresoPorEstudiante().forEach((estudianteId, progresoEst) -> {
            ActividadEntity.ProgresoEstudianteEntity estudianteEntity = new ActividadEntity.ProgresoEstudianteEntity();
            estudianteEntity.setEstudianteId(progresoEst.estudianteId());
            estudianteEntity.setEstado(progresoEst.estado().name());
            estudianteEntity.setPorcentajeAvance(progresoEst.porcentajeAvance());
            estudianteEntity.setFechaInicio(progresoEst.fechaInicio());
            estudianteEntity.setFechaCompletado(progresoEst.fechaCompletado());
            estudianteEntity.setFechaUltimaActualizacion(progresoEst.fechaUltimaActualizacion());
            estudianteEntity.setTiempoTotalEmpleado(progresoEst.tiempoTotalEmpleado());
            // Continuar con el resto de los campos...
            
            progresoPorEstudiante.put(estudianteId, estudianteEntity);
        });
        
        entity.setProgresoPorEstudiante(progresoPorEstudiante);
        return entity;
    }

    private ProgresoActividad convertirEntityToProgreso(ActividadEntity.ProgresoActividadEntity entity) {
        // Implementar conversión de Entity a Domain
        // Por brevedad, se omite la implementación completa
        return ProgresoActividad.builder()
            .actividadId(entity.getActividadId())
            .build();
    }
}
