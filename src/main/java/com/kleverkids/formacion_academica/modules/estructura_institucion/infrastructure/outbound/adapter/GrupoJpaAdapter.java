package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.grupo.GrupoRepositoryPort;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grupo.ActualizarGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grupo.CrearGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Grupo;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.mappers.GrupoMapper;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity.GrupoEntity;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.repository.GrupoJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

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
    public Grupo guardar(CrearGrupoDto request) {
        return grupoMapper.toDomainModel(grupoJpaRepository.save(grupoMapper.toEntity(request)));
    }

    @Override
    public Grupo actualizar(ActualizarGrupoDto request) {
        GrupoEntity entity = grupoJpaRepository.findById(request.getId())
                .orElseThrow(() -> new IllegalArgumentException("Grupo no encontrado"));
        grupoMapper.updateEntityFromDto(request, entity);
        return grupoMapper.toDomainModel(grupoJpaRepository.save(entity));
    }

    @Override
    public boolean existePorCodigo(String codigo) {
        return grupoJpaRepository.existsByCodigo(codigo);
    }

    @Override
    public Grupo obtenerPorId(Long id) {
        return grupoJpaRepository.findById(id)
                .map(grupoMapper::toDomainModel)
                .orElseThrow(() -> new IllegalArgumentException("Grupo no encontrado"));
    }

    @Override
    public List<Grupo> listar() {
        return grupoMapper.toDomainModelList(grupoJpaRepository.findAll());
    }

    @Override
    public void eliminar(Long id) {
        grupoJpaRepository.deleteById(id);
    }
}
