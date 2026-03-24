package com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.parentesco.ParentescoRepositoryPort;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model.Parentesco;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.parentesco.CrearParentescoDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.parentesco.ActualizarParentescoDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.mappers.ParentescoMapper;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.persistence.mysql.entity.ParentescoEntity;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.persistence.mysql.repository.ParentescoJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ParentescoJpaAdapter implements ParentescoRepositoryPort {

    private final ParentescoJpaRepository jpaRepository;
    private final ParentescoMapper mapper;

    public ParentescoJpaAdapter(ParentescoJpaRepository jpaRepository, ParentescoMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Parentesco guardar(CrearParentescoDto request) {
        ParentescoEntity entity = mapper.toEntity(request);
        ParentescoEntity saved = jpaRepository.save(entity);
        return mapper.toDomainModel(saved);
    }

    @Override
    public Parentesco actualizar(ActualizarParentescoDto request) {
        Optional<ParentescoEntity> existing = jpaRepository.findById(request.getId());
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Parentesco no encontrado");
        }

        ParentescoEntity entity = existing.get();
        mapper.updateEntityFromDto(entity, request);
        ParentescoEntity updated = jpaRepository.save(entity);
        return mapper.toDomainModel(updated);
    }

    @Override
    public Optional<Parentesco> obtenerPorId(Long parentescoId) {
        return jpaRepository.findById(parentescoId)
                .map(mapper::toDomainModel);
    }

    @Override
    public List<Parentesco> listarTodos() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Parentesco> listarActivos() {
        return jpaRepository.findByActivoTrue().stream()
                .map(mapper::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existePorNombre(String nombre) {
        return jpaRepository.existsByNombre(nombre);
    }

    @Override
    public boolean existePorNombreConIdDiferente(String nombre, Long id) {
        return jpaRepository.existsByNombreAndIdNot(nombre, id);
    }

    @Override
    public void eliminar(Long parentescoId) {
        if (!jpaRepository.existsById(parentescoId)) {
            throw new IllegalArgumentException("Parentesco no encontrado");
        }
        jpaRepository.deleteById(parentescoId);
    }
}
