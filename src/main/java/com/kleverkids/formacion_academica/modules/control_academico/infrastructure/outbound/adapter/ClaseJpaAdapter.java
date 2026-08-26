package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.clase.ClaseRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.Clase;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.ActualizarClaseDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.CrearClaseDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers.ClaseMapper;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.ClaseEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository.ClaseJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Component
public class ClaseJpaAdapter implements ClaseRepositoryPort {

    private final ClaseJpaRepository claseJpaRepository;
    private final ClaseMapper claseMapper;

    @Override
    public Clase guardar(CrearClaseDto clase) {
        ClaseEntity entity = claseMapper.toEntity(clase);
        return claseMapper.toDomainModel(claseJpaRepository.save(entity));
    }

    @Override
    public List<Clase> guardarTodas(List<CrearClaseDto> clases) {
        List<ClaseEntity> entities = claseMapper.toEntityList(clases);
        return claseMapper.toDomainModelList(claseJpaRepository.saveAll(entities));
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
        existing.setFechaFin(clase.getFechaFin());
        existing.setProfesoresIds(clase.getProfesoresIds());
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
