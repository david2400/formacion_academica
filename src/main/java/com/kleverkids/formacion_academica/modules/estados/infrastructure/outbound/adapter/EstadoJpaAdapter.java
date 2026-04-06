package com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.estados.application.output.ports.EstadoRepositoryPort;
import com.kleverkids.formacion_academica.modules.estados.domain.dto.EstadoDto;
import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.mappers.EstadoMapper;
import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.entity.EntidadEstadoEntity;
import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.entity.EstadoEntity;
import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.entity.EstadoTransicionEntity;
import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.repository.EstadoJpaRepository;
import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.repository.EstadoTransicionJpaRepository;
import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.repository.EntidadEstadoJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Adaptador JPA que implementa el puerto de repositorio de Estados
 * Sigue el patrón de adapters de los demás módulos
 */
@Repository
@RequiredArgsConstructor
@Transactional
public class EstadoJpaAdapter implements EstadoRepositoryPort {

    private final EstadoJpaRepository estadoRepository;
    private final EstadoTransicionJpaRepository transicionRepository;
    private final EntidadEstadoJpaRepository entidadEstadoRepository;
    private final EstadoMapper mapper;

    @Override
    public EstadoDto crear(EstadoDto estado) {
        EstadoEntity entity = mapper.toEntity(estado);
        EstadoEntity saved = estadoRepository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EstadoDto> buscarPorId(Long id) {
        return estadoRepository.findById(id)
                .map(mapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EstadoDto> buscarPorCodigo(String codigo) {
        return estadoRepository.findByCodigo(codigo)
                .map(mapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EstadoDto> listarTodos() {
        List<EstadoEntity> entities = estadoRepository.findAll();
        return mapper.toDtoList(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EstadoDto> listarPorModulo(Long idModulo) {
        List<EstadoEntity> entities = estadoRepository.findByIdModuloAndActivoTrueOrderByOrdenAsc(idModulo);
        return mapper.toDtoList(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EstadoDto> listarPorModuloYEmpresa(Long idModulo, Long idEmpresa) {
        List<EstadoEntity> entities = estadoRepository.findByIdModuloAndIdEmpresaAndActivoTrue(idModulo, idEmpresa);
        return mapper.toDtoList(entities);
    }

    @Override
    public Optional<EstadoDto> actualizar(Long id, EstadoDto estado) {
        return estadoRepository.findById(id)
                .map(entity -> {
                    mapper.updateEntityFromDto(estado, entity);
                    EstadoEntity updated = estadoRepository.save(entity);
                    return mapper.toDto(updated);
                });
    }

    @Override
    public boolean eliminar(Long id) {
        if (estadoRepository.existsById(id)) {
            estadoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorCodigo(String codigo) {
        // Como los estados siempre están asociados a un módulo, 
        // verificamos si existe algún estado con ese código en cualquier módulo
        return !estadoRepository.findByCodigo(codigo).isEmpty();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean esTransicionValida(Long estadoOrigenId, Long estadoDestinoId, Long idModulo) {
        return transicionRepository.existeTransicionValida(estadoOrigenId, estadoDestinoId, idModulo);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean esTransicionValida(Long estadoOrigenId, Long estadoDestinoId, Long idModulo, Long idEmpresa) {
        Optional<EstadoTransicionEntity> transicion = transicionRepository.findTransicionPorEmpresa(
                estadoOrigenId, estadoDestinoId, idModulo, idEmpresa);
        return transicion.isPresent();
    }

    @Override
    public void asignarEstadoInicial(String entidadTipo, Long entidadId, Long idModulo, Long idUsuarioCambio) {
        // Implementación de asignación de estado inicial
        // Aquí se delegaría a un servicio especializado o se implementaría la lógica directamente
    }

    @Override
    public void cambiarEstado(String entidadTipo, Long entidadId, Long nuevoEstadoId, Long idUsuarioCambio, String motivo) {
        // Implementación de cambio de estado
        // Aquí se delegaría a un servicio especializado o se implementaría la lógica directamente
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EstadoDto> obtenerEstadoActual(String entidadTipo, Long entidadId) {
        return entidadEstadoRepository.findEstadoActual(entidadTipo, entidadId)
                .map(entidadEstado -> mapper.toDto(entidadEstado.getEstado()));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EstadoDto> listarHistorialEstados(String entidadTipo, Long entidadId) {
        List<EntidadEstadoEntity> historial = entidadEstadoRepository.findByEntidadTipoAndEntidadId(entidadTipo, entidadId);
        return historial.stream()
                .map(entidadEstado -> mapper.toDto(entidadEstado.getEstado()))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EstadoDto> listarEstadosIniciales(Long idModulo) {
        List<EstadoEntity> entities = estadoRepository.findEstadosInicialesPorModulo(idModulo);
        return mapper.toDtoList(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EstadoDto> listarEstadosFinales(Long idModulo) {
        List<EstadoEntity> entities = estadoRepository.findEstadosFinalesPorModulo(idModulo);
        return mapper.toDtoList(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EstadoDto> listarEntidadesConEstado(Integer estadoId) {
        List<EntidadEstadoEntity> entidades = entidadEstadoRepository.findByEstadoIdAndActivoTrue(estadoId);
        return entidades.stream()
                .map(entidadEstado -> mapper.toDto(entidadEstado.getEstado()))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Long contarEntidadesConEstado(Integer estadoId) {
        return entidadEstadoRepository.countEntidadesConEstado(estadoId);
    }
}
