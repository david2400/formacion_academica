package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.aula.AulaRepositoryPort;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.aula.ActualizarAulaDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.aula.AulaDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.aula.CrearAulaDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.mappers.AulaMapper;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity.AulaEntity;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.repository.AulaJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class AulaJpaAdapter implements AulaRepositoryPort {

    private final AulaJpaRepository aulaJpaRepository;
    private final AulaMapper aulaMapper;

    public AulaJpaAdapter(AulaJpaRepository aulaJpaRepository,
                          AulaMapper aulaMapper) {
        this.aulaJpaRepository = aulaJpaRepository;
        this.aulaMapper = aulaMapper;
    }

    @Override
    public AulaDto guardar(CrearAulaDto request) {
        AulaEntity entity = aulaMapper.toEntity(request);
        return aulaMapper.toDto(aulaJpaRepository.save(entity));
    }

    @Override
    public AulaDto actualizar(ActualizarAulaDto request) {
        AulaEntity entity = aulaJpaRepository.findById(request.getId())
                .orElseThrow(() -> new IllegalArgumentException("Aula no encontrada"));
        aulaMapper.updateEntityFromDto(request, entity);
        return aulaMapper.toDto(aulaJpaRepository.save(entity));
    }

    @Override
    public boolean existePorNombre(String nombre) {
        return nombre != null && aulaJpaRepository.existsByNombreIgnoreCase(nombre);
    }

    @Override
    public boolean existePorId(UUID id) {
        return aulaJpaRepository.existsById(id);
    }

    @Override
    public AulaDto obtenerPorId(UUID id) {
        return aulaJpaRepository.findById(id)
                .map(aulaMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Aula no encontrada"));
    }

    @Override
    public List<AulaDto> listar() {
        return aulaMapper.toDtoList(aulaJpaRepository.findAll());
    }

    @Override
    public void eliminar(UUID id) {
        aulaJpaRepository.deleteById(id);
    }
}
