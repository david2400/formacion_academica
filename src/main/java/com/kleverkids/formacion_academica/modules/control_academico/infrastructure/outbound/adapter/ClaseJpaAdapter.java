package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.clase.ClaseRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.clase.Clase;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.CrearClaseDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers.ClaseMapper;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.ClaseEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository.ClaseJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


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
}
