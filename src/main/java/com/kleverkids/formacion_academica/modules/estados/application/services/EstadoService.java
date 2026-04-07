package com.kleverkids.formacion_academica.modules.estados.application.services;

import com.kleverkids.formacion_academica.modules.estados.application.input.estado.ActualizarEstadoUseCase;
import com.kleverkids.formacion_academica.modules.estados.application.input.estado.ConsultarEstadoUseCase;
import com.kleverkids.formacion_academica.modules.estados.application.input.estado.CrearEstadoUseCase;
import com.kleverkids.formacion_academica.modules.estados.application.input.estado.EliminarEstadoUseCase;
import com.kleverkids.formacion_academica.modules.estados.application.input.consulta.ConsultaEspecializadaUseCase;
import com.kleverkids.formacion_academica.modules.estados.application.input.entidad.GestionarEntidadEstadoUseCase;
import com.kleverkids.formacion_academica.modules.estados.application.input.transicion.ConsultarTransicionUseCase;
import com.kleverkids.formacion_academica.modules.estados.application.input.transicion.ValidarTransicionUseCase;
import com.kleverkids.formacion_academica.modules.estados.domain.dto.EstadoDto;
import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.entity.EntidadEstadoEntity;
import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.entity.EstadoEntity;
import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.entity.EstadoHistorialEntity;
import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.entity.EstadoTransicionEntity;
import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.repository.EntidadEstadoJpaRepository;
import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.repository.EstadoHistorialJpaRepository;
import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.repository.EstadoJpaRepository;
import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.repository.EstadoTransicionJpaRepository;
import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.mappers.EstadoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class EstadoService implements CrearEstadoUseCase, ConsultarEstadoUseCase, ActualizarEstadoUseCase, EliminarEstadoUseCase, ValidarTransicionUseCase {

    private final EstadoJpaRepository estadoRepository;
    private final EstadoTransicionJpaRepository transicionRepository;
    private final EntidadEstadoJpaRepository entidadEstadoRepository;
    private final EstadoHistorialJpaRepository historialRepository;
    private final EstadoMapper estadoMapper;

    // ========================================
    // GESTIÓN DE ESTADOS
    // ========================================

    @Transactional(readOnly = true)
    public List<EstadoEntity> listarEstadosPorModulo(Long idModulo) {
        return estadoRepository.findByIdModuloAndEliminadoFalseOrderByOrdenAsc(idModulo);
    }

    @Transactional(readOnly = true)
    public List<EstadoEntity> listarEstadosPorModuloYEmpresa(Long idModulo, Long idEmpresa) {
        return estadoRepository.findByIdModuloAndIdEmpresaAndEliminadoFalse(idModulo, idEmpresa);
    }

    @Transactional(readOnly = true)
    public Optional<EstadoEntity> buscarEstadoPorCodigo(String codigo, Long idModulo) {
        return estadoRepository.findByCodigoAndIdModulo(codigo, idModulo);
    }

    @Transactional(readOnly = true)
    public List<EstadoEntity> listarEstadosIniciales(Long idModulo) {
        return estadoRepository.findEstadosInicialesPorModulo(idModulo);
    }

    @Transactional(readOnly = true)
    public List<EstadoEntity> listarEstadosFinales(Long idModulo) {
        return estadoRepository.findEstadosFinalesPorModulo(idModulo);
    }

    // ========================================
    // GESTIÓN DE TRANSICIONES
    // ========================================

    @Transactional(readOnly = true)
    public List<EstadoTransicionEntity> listarTransicionesPorModulo(Long idModulo) {
        return transicionRepository.findByIdModuloAndActivaTrue(idModulo);
    }

    @Transactional(readOnly = true)
    public List<EstadoTransicionEntity> listarTransicionesDesdeEstado(Long estadoOrigenId, Long idModulo) {
        return transicionRepository.findTransicionesDesdeEstado(estadoOrigenId, idModulo);
    }

    @Transactional(readOnly = true)
    public boolean esTransicionValida(Long estadoOrigenId, Long estadoDestinoId, Long idModulo) {
        return transicionRepository.existeTransicionValida(estadoOrigenId, estadoDestinoId, idModulo);
    }

    @Transactional(readOnly = true)
    public boolean esTransicionValida(Long estadoOrigenId, Long estadoDestinoId, Long idModulo, Long idEmpresa) {
        Optional<EstadoTransicionEntity> transicion = transicionRepository.findTransicionPorEmpresa(
            estadoOrigenId, estadoDestinoId, idModulo, idEmpresa);
        return transicion.isPresent();
    }

    // ========================================
    // GESTIÓN DE ESTADOS DE ENTIDADES
    // ========================================

    @Transactional
    public EntidadEstadoEntity asignarEstadoInicial(String entidadTipo, Long entidadId, Long idModulo, Long idUsuarioCambio) {
        // Buscar estados iniciales del módulo
        List<EstadoEntity> estadosIniciales = listarEstadosIniciales(idModulo);
        
        if (estadosIniciales.isEmpty()) {
            throw new IllegalStateException("No hay estados iniciales configurados para el módulo: " + idModulo);
        }
        
        // Tomar el primer estado inicial (puede haber lógica para seleccionar el correcto)
        EstadoEntity estadoInicial = estadosIniciales.get(0);
        
        return crearAsignacionEstado(entidadTipo, entidadId, estadoInicial, null, idUsuarioCambio, "Asignación de estado inicial");
    }

    @Transactional
    public EntidadEstadoEntity cambiarEstado(String entidadTipo, Long entidadId, Long nuevoEstadoId, Long idUsuarioCambio, String motivo) {
        // Obtener estado actual
        Optional<EntidadEstadoEntity> estadoActualOpt = entidadEstadoRepository.findEstadoActual(entidadTipo, entidadId);
        
        if (estadoActualOpt.isEmpty()) {
            throw new IllegalStateException("La entidad no tiene un estado actual asignado");
        }
        
        EntidadEstadoEntity estadoActual = estadoActualOpt.get();
        EstadoEntity nuevoEstado = estadoRepository.findById(nuevoEstadoId)
                .orElseThrow(() -> new IllegalArgumentException("El nuevo estado no existe"));
        
        // Validar que la transición sea válida
        if (!esTransicionValida(estadoActual.getEstado().getId(), nuevoEstadoId, nuevoEstado.getIdModulo())) {
            throw new IllegalStateException("La transición de estado no está permitida");
        }
        
        // Desactivar el estado actual
        estadoActual.setEliminado(false);
        entidadEstadoRepository.save(estadoActual);
        
        // Crear nueva asignación de estado
        return crearAsignacionEstado(entidadTipo, entidadId, nuevoEstado, estadoActual.getEstado(), idUsuarioCambio, motivo);
    }

    @Transactional
    public EntidadEstadoEntity cambiarEstadoConValidacion(String entidadTipo, Long entidadId, Long nuevoEstadoId, Long idModulo, Long idUsuarioCambio, String motivo) {
        // Validar que el nuevo estado pertenezca al módulo correcto
        EstadoEntity nuevoEstado = estadoRepository.findById(nuevoEstadoId)
                .orElseThrow(() -> new IllegalArgumentException("El nuevo estado no existe"));
        
        if (!nuevoEstado.getIdModulo().equals(idModulo)) {
            throw new IllegalArgumentException("El estado no pertenece al módulo especificado");
        }
        
        return cambiarEstado(entidadTipo, entidadId, nuevoEstadoId, idUsuarioCambio, motivo);
    }

    @Transactional(readOnly = true)
    public Optional<EntidadEstadoEntity> obtenerEstadoActual(String entidadTipo, Long entidadId) {
        return entidadEstadoRepository.findEstadoActual(entidadTipo, entidadId);
    }

    @Transactional(readOnly = true)
    public List<EntidadEstadoEntity> listarHistorialEstados(String entidadTipo, Long entidadId) {
        return entidadEstadoRepository.findByEntidadTipoAndEntidadId(entidadTipo, entidadId);
    }

    @Transactional(readOnly = true)
    public List<EntidadEstadoEntity> listarEntidadesConEstado(Integer estadoId) {
        return entidadEstadoRepository.findByEstadoIdAndEliminadoFalse(estadoId);
    }

    @Transactional(readOnly = true)
    public Long contarEntidadesConEstado(Integer estadoId) {
        return entidadEstadoRepository.countEntidadesConEstado(estadoId);
    }

    // ========================================
    // MÉTODOS PRIVADOS
    // ========================================

    private EntidadEstadoEntity crearAsignacionEstado(String entidadTipo, Long entidadId, EstadoEntity estado, 
                                                   EstadoEntity estadoAnterior, Long idUsuarioCambio, String motivo) {
        
        // Crear nueva asignación de estado
        EntidadEstadoEntity nuevaAsignacion = new EntidadEstadoEntity();
        nuevaAsignacion.setUuid(UUID.randomUUID().toString());
        nuevaAsignacion.setEntidadTipo(entidadTipo);
        nuevaAsignacion.setEntidadId(entidadId);
        nuevaAsignacion.setEstado(estado);
        nuevaAsignacion.setEstadoAnterior(estadoAnterior);
        nuevaAsignacion.setIdUsuarioCambio(idUsuarioCambio);
        nuevaAsignacion.setMotivo(motivo);
        nuevaAsignacion.setEliminado(true);
        
        // Guardar la asignación
        EntidadEstadoEntity guardada = entidadEstadoRepository.save(nuevaAsignacion);
        
        // Crear registro en historial
        crearRegistroHistorial(guardada, estadoAnterior, estado, idUsuarioCambio, motivo);
        
        log.info("Estado asignado: {} {} -> {} (motivo: {})", entidadTipo, entidadId, estado.getCodigo(), motivo);
        
        return guardada;
    }

    private void crearRegistroHistorial(EntidadEstadoEntity entidadEstado, EstadoEntity estadoAnterior, 
                                      EstadoEntity estadoNuevo, Long idUsuarioCambio, String motivo) {
        
        EstadoHistorialEntity historial = new EstadoHistorialEntity();
        historial.setUuid(UUID.randomUUID().toString());
        historial.setEntidadEstado(entidadEstado);
        historial.setEntidadTipo(entidadEstado.getEntidadTipo());
        historial.setEntidadId(entidadEstado.getEntidadId());
        historial.setEstadoAnterior(estadoAnterior);
        historial.setEstadoNuevo(estadoNuevo);
        historial.setIdUsuarioCambio(idUsuarioCambio);
        historial.setMotivo(motivo);
        
        historialRepository.save(historial);
    }

    // ========================================
    // MÉTODOS DE CONSULTA AVANZADA
    // ========================================

    @Transactional(readOnly = true)
    public List<EstadoEntity> obtenerWorkflowCompleto(Long idModulo) {
        return estadoRepository.findEstadosActivosPorModulo(idModulo);
    }

    @Transactional(readOnly = true)
    public boolean tieneEstadosConfigurados(Long idModulo) {
        return estadoRepository.existsByCodigoAndIdModuloAndEliminadoFalse("ACTIVO", idModulo);
    }

    @Transactional(readOnly = true)
    public List<EntidadEstadoEntity> listarEntidadesPorModulo(String entidadTipo, Long idModulo) {
        return entidadEstadoRepository.findEntidadesPorTipoYModulo(entidadTipo, idModulo);
    }

    // ========================================
    // IMPLEMENTACIÓN DE INTERFACES USE CASE
    // ========================================

    @Override
    public EstadoDto crear(EstadoDto estado) {
        EstadoEntity entity = estadoMapper.toEntity(estado);
        EstadoEntity saved = estadoRepository.save(entity);
        return estadoMapper.toDto(saved);
    }

    @Override
    public Optional<EstadoDto> consultarPorId(Long id) {
        return estadoRepository.findById(id)
                .map(estadoMapper::toDto);
    }

    @Override
    public Optional<EstadoDto> consultarPorCodigo(String codigo) {
        return estadoRepository.findByCodigo(codigo)
                .map(estadoMapper::toDto);
    }

    @Override
    public List<EstadoDto> listarPorModulo(Long idModulo) {
        return estadoRepository.findByIdModuloAndEliminadoFalseOrderByOrdenAsc(idModulo)
                .stream()
                .map(estadoMapper::toDto)
                .toList();
    }

    @Override
    public List<EstadoDto> listarPorModuloYEmpresa(Long idModulo, Long idEmpresa) {
        return estadoRepository.findByIdModuloAndIdEmpresaAndEliminadoFalse(idModulo, idEmpresa)
                .stream()
                .map(estadoMapper::toDto)
                .toList();
    }

    @Override
    public List<EstadoDto> listarIniciales(Long idModulo) {
        return estadoRepository.findEstadosInicialesPorModulo(idModulo)
                .stream()
                .map(estadoMapper::toDto)
                .toList();
    }

    @Override
    public List<EstadoDto> listarFinales(Long idModulo) {
        return estadoRepository.findEstadosFinalesPorModulo(idModulo)
                .stream()
                .map(estadoMapper::toDto)
                .toList();
    }

    @Override
    public Optional<EstadoDto> actualizar(Long id, EstadoDto estado) {
        return estadoRepository.findById(id)
                .map(existing -> {
                    estadoMapper.updateEntityFromDto(estado, existing);
                    EstadoEntity updated = estadoRepository.save(existing);
                    return estadoMapper.toDto(updated);
                });
    }

    @Override
    public boolean eliminar(Long id) {
        return estadoRepository.findById(id)
                .map(entity -> {
                    entity.setEliminado(false);
                    estadoRepository.save(entity);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public boolean esValida(Long estadoOrigenId, Long estadoDestinoId, Long idModulo) {
        return transicionRepository.existeTransicionValida(estadoOrigenId, estadoDestinoId, idModulo);
    }

    @Override
    public boolean esValida(Long estadoOrigenId, Long estadoDestinoId, Long idModulo, Long idEmpresa) {
        return transicionRepository.findTransicionPorEmpresa(estadoOrigenId, estadoDestinoId, idModulo, idEmpresa)
                .isPresent();
    }
}
