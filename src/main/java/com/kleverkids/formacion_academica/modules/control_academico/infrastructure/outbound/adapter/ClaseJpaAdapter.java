package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.clase.ClaseRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.ClaseDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.CrearClaseDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers.ClaseMapper;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.ClaseEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository.ClaseJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class ClaseJpaAdapter implements ClaseRepositoryPort {

    private final ClaseJpaRepository claseJpaRepository;
    private final ClaseMapper claseMapper;

    @Override
    public ClaseDto guardar(CrearClaseDto clase) {
        ClaseEntity entity = claseMapper.toEntity(clase);
        return claseMapper.toDto(claseJpaRepository.save(entity));
    }

    @Override
    public List<ClaseDto> guardarTodas(List<CrearClaseDto> clases) {
        List<ClaseEntity> entities = claseMapper.toEntityList(clases);
        return claseMapper.toDtoList(claseJpaRepository.saveAll(entities));
    }


    @Override
    public ClaseDto getClaseById(UUID id) {
        return claseJpaRepository.findById(id)
                .map(claseMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Clase no encontrada"));
    }
}
