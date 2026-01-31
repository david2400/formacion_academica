package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.clase.ClaseRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.ClaseDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.CrearClaseDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers.ClaseMapper;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.ClaseEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository.ClaseJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ClaseJpaAdapter implements ClaseRepositoryPort {

    private final ClaseJpaRepository claseJpaRepository;

    public ClaseJpaAdapter(ClaseJpaRepository claseJpaRepository) {
        this.claseJpaRepository = claseJpaRepository;
    }

    @Override
    public ClaseDto guardar(CrearClaseDto clase) {
        ClaseEntity entity = ClaseMapper.toEntity(clase);
        return ClaseMapper.toDto(claseJpaRepository.save(entity));
    }

    @Override
    public List<ClaseDto> guardarTodas(List<CrearClaseDto> clases) {
        List<ClaseEntity> entities = clases.stream()
                .map(ClaseMapper::toEntity)
                .toList();
        return claseJpaRepository.saveAll(entities).stream()
                .map(ClaseMapper::toDto)
                .toList();
    }

    @Override
    public boolean existePorCodigo(String codigo) {
        return claseJpaRepository.existsByCodigo(codigo);
    }

    @Override
    public ClaseDto obtenerPorId(UUID id) {
        return claseJpaRepository.findById(id)
                .map(ClaseMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Clase no encontrada"));
    }
}
