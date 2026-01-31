package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.estudiantegrupo.EstudianteGrupoRepositoryPort;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.estudiante_grupo.AsignarEstudianteGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.estudiante_grupo.CambiarEstadoEstudianteGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.estudiante_grupo.EstudianteGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.mappers.EstudianteGrupoMapper;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity.EstudianteGrupoEntity;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.repository.EstudianteGrupoJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class EstudianteGrupoJpaAdapter implements EstudianteGrupoRepositoryPort {

    private final EstudianteGrupoJpaRepository estudianteGrupoJpaRepository;

    public EstudianteGrupoJpaAdapter(EstudianteGrupoJpaRepository estudianteGrupoJpaRepository) {
        this.estudianteGrupoJpaRepository = estudianteGrupoJpaRepository;
    }

    @Override
    public EstudianteGrupoDto asignar(AsignarEstudianteGrupoDto request) {
        EstudianteGrupoEntity entity = EstudianteGrupoMapper.toEntity(request);
        return EstudianteGrupoMapper.toDto(estudianteGrupoJpaRepository.save(entity));
    }

    @Override
    public EstudianteGrupoDto cambiarEstado(CambiarEstadoEstudianteGrupoDto request) {
        EstudianteGrupoEntity entity = estudianteGrupoJpaRepository.findById(request.asignacionId())
                .orElseThrow(() -> new IllegalArgumentException("Asignación no encontrada"));
        entity.setEstado(request.nuevoEstado());
        return EstudianteGrupoMapper.toDto(estudianteGrupoJpaRepository.save(entity));
    }

    @Override
    public List<EstudianteGrupoDto> listarPorGrupo(UUID grupoId) {
        return EstudianteGrupoMapper.toDtoList(estudianteGrupoJpaRepository.findByGrupoId(grupoId));
    }
}
