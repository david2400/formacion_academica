package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.clase.ClaseRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.Clase;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.ActualizarClaseDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.CrearClaseDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.RegistrarSeguimientoClaseDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.clase.EstadoClase;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers.ClaseMapper;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.ClaseEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository.ClaseJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RequiredArgsConstructor
@Component
public class ClaseJpaAdapter implements ClaseRepositoryPort {

    private final ClaseJpaRepository claseJpaRepository;
    private final ClaseMapper claseMapper;

    @Override
    public Clase guardar(CrearClaseDto clase) {
        ClaseEntity entity = aplicarValoresPorDefecto(claseMapper.toEntity(clase));
        return claseMapper.toDomainModel(claseJpaRepository.save(entity));
    }

    @Override
    public List<Clase> guardarTodas(List<CrearClaseDto> clases) {
        List<ClaseEntity> entities = claseMapper.toEntityList(clases).stream()
                .map(this::aplicarValoresPorDefecto)
                .toList();
        return claseMapper.toDomainModelList(claseJpaRepository.saveAll(entities));
    }

    /**
     * Completa los campos que la base exige NOT NULL pero que el DTO de creación
     * no envía: el código (único, generado aquí), la fecha fin (clase de un solo
     * día) y el estado inicial de seguimiento.
     */
    private ClaseEntity aplicarValoresPorDefecto(ClaseEntity entity) {
        if (entity.getCodigo() == null || entity.getCodigo().isBlank()) {
            entity.setCodigo(generarCodigoUnico());
        }
        if (entity.getFechaFin() == null) {
            entity.setFechaFin(entity.getFechaInicio());
        }
        if (entity.getEstado() == null) {
            entity.setEstado(EstadoClase.PROGRAMADA);
        }
        return entity;
    }

    private String generarCodigoUnico() {
        String codigo;
        do {
            codigo = "CLS-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        } while (claseJpaRepository.existsByCodigo(codigo));
        return codigo;
    }

    @Override
    public Clase getClaseById(Long id) {
        return claseJpaRepository.findById(id)
                .map(claseMapper::toDomainModel)
                .orElseThrow(() -> new IllegalArgumentException("Clase no encontrada"));
    }

    @Override
    public Optional<Clase> obtenerPorId(Long id) {
        return claseJpaRepository.findById(id).map(claseMapper::toDomainModel);
    }

    @Override
    public List<Clase> listarTodas() {
        return claseMapper.toDomainModelList(claseJpaRepository.findAll());
    }

    @Override
    public Optional<Clase> buscarPorCodigo(String codigo) {
        return claseJpaRepository.findByCodigo(codigo).map(claseMapper::toDomainModel);
    }

    @Override
    public Clase actualizar(ActualizarClaseDto clase) {
        ClaseEntity existing = claseJpaRepository.findById(clase.getId())
                .orElseThrow(() -> new IllegalArgumentException("Clase no encontrada"));
        existing.setNombre(clase.getNombre());
        existing.setFechaInicio(clase.getFechaInicio());
        existing.setFechaFin(clase.getFechaFin() != null ? clase.getFechaFin() : clase.getFechaInicio());
        existing.setProfesoresIds(clase.getProfesoresIds());
        if (clase.getEstado() != null) {
            existing.setEstado(clase.getEstado());
        }
        if (clase.getObservaciones() != null) {
            existing.setObservaciones(clase.getObservaciones());
        }
        return claseMapper.toDomainModel(claseJpaRepository.save(existing));
    }

    @Override
    public Clase registrarSeguimiento(Long id, RegistrarSeguimientoClaseDto seguimiento) {
        ClaseEntity existing = claseJpaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Clase no encontrada"));

        // Actualización parcial: sólo se toca lo que viene informado.
        if (seguimiento.getEstado() != null) {
            existing.setEstado(seguimiento.getEstado());
        }
        if (seguimiento.getObservaciones() != null) {
            existing.setObservaciones(seguimiento.getObservaciones());
        }

        return claseMapper.toDomainModel(claseJpaRepository.save(existing));
    }

    @Override
    public void eliminar(Long id) {
        ClaseEntity existing = claseJpaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Clase no encontrada"));
        existing.setEliminado(true);
        claseJpaRepository.save(existing);
    }

    @Override
    public boolean existePorCodigo(String codigo) {
        return claseJpaRepository.existsByCodigo(codigo);
    }
}
