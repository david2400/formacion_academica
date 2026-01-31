package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.grupo.GrupoRepositoryPort;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grupo.ActualizarGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grupo.CrearGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grupo.GrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.mappers.GrupoMapper;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity.GrupoEntity;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.repository.GrupoJpaRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GrupoJpaAdapter implements GrupoRepositoryPort {

    private final GrupoJpaRepository grupoJpaRepository;

    public GrupoJpaAdapter(GrupoJpaRepository grupoJpaRepository) {
        this.grupoJpaRepository = grupoJpaRepository;
    }

    @Override
    public GrupoDto guardar(CrearGrupoDto request) {
        return GrupoMapper.toDto(grupoJpaRepository.save(GrupoMapper.toEntity(request)));
    }

    @Override
    public GrupoDto actualizar(ActualizarGrupoDto request) {
        GrupoEntity entity = grupoJpaRepository.findById(request.id())
                .orElseThrow(() -> new IllegalArgumentException("Grupo no encontrado"));
        GrupoMapper.applyUpdate(entity, request);
        return GrupoMapper.toDto(grupoJpaRepository.save(entity));
    }

    @Override
    public boolean existePorCodigo(String codigo) {
        return grupoJpaRepository.existsByCodigo(codigo);
    }

    @Override
    public GrupoDto obtenerPorId(UUID id) {
        return grupoJpaRepository.findById(id)
                .map(GrupoMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Grupo no encontrado"));
    }
}
