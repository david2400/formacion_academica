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
    private final GrupoMapper grupoMapper;

    public GrupoJpaAdapter(GrupoJpaRepository grupoJpaRepository,
                           GrupoMapper grupoMapper) {
        this.grupoJpaRepository = grupoJpaRepository;
        this.grupoMapper = grupoMapper;
    }

    @Override
    public GrupoDto guardar(CrearGrupoDto request) {
        return grupoMapper.toDto(grupoJpaRepository.save(grupoMapper.toEntity(request)));
    }

    @Override
    public GrupoDto actualizar(ActualizarGrupoDto request) {
        GrupoEntity entity = grupoJpaRepository.findById(request.getId())
                .orElseThrow(() -> new IllegalArgumentException("Grupo no encontrado"));
        grupoMapper.updateEntityFromDto(request, entity);
        return grupoMapper.toDto(grupoJpaRepository.save(entity));
    }

    @Override
    public boolean existePorCodigo(String codigo) {
        return grupoJpaRepository.existsByCodigo(codigo);
    }

    @Override
    public GrupoDto obtenerPorId(UUID id) {
        return grupoJpaRepository.findById(id)
                .map(grupoMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Grupo no encontrado"));
    }
}
