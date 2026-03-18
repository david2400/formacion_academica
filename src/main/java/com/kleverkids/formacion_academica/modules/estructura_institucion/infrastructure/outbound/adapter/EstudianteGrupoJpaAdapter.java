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

@Component
public class EstudianteGrupoJpaAdapter implements EstudianteGrupoRepositoryPort {

    private final EstudianteGrupoJpaRepository estudianteGrupoJpaRepository;
    private final EstudianteGrupoMapper estudianteGrupoMapper;

    public EstudianteGrupoJpaAdapter(EstudianteGrupoJpaRepository estudianteGrupoJpaRepository,
                                     EstudianteGrupoMapper estudianteGrupoMapper) {
        this.estudianteGrupoJpaRepository = estudianteGrupoJpaRepository;
        this.estudianteGrupoMapper = estudianteGrupoMapper;
    }

    @Override
    public EstudianteGrupoDto asignar(AsignarEstudianteGrupoDto request) {
        EstudianteGrupoEntity entity = estudianteGrupoMapper.toEntity(request);
        return estudianteGrupoMapper.toDto(estudianteGrupoJpaRepository.save(entity));
    }

    @Override
    public EstudianteGrupoDto cambiarEstado(CambiarEstadoEstudianteGrupoDto request) {
        EstudianteGrupoEntity entity = estudianteGrupoJpaRepository.findById(request.asignacionId())
                .orElseThrow(() -> new IllegalArgumentException("Asignación no encontrada"));
        entity.setEstadoLegacy(request.nuevoEstado()); // Usar campo legacy para String
        // TODO: Buscar y asignar EstadoEntity basado en el código del estado
        return estudianteGrupoMapper.toDto(estudianteGrupoJpaRepository.save(entity));
    }

    @Override
    public List<EstudianteGrupoDto> listarPorGrupo(Long grupoId) {
        return estudianteGrupoMapper.toDtoList(estudianteGrupoJpaRepository.findByGrupoId(grupoId));
    }

    @Override
    public EstudianteGrupoDto consultarPorId(Long estudianteGrupoId) {
        return estudianteGrupoMapper.toDto(
                estudianteGrupoJpaRepository.findById(estudianteGrupoId)
                        .orElseThrow(() -> new IllegalArgumentException("Asignación de estudiante grupo no encontrada"))
        );
    }

    @Override
    public void eliminar(Long estudianteGrupoId) {
        estudianteGrupoJpaRepository.deleteById(estudianteGrupoId);
    }

    @Override
    public List<EstudianteGrupoDto> listar() {
        return estudianteGrupoMapper.toDtoList(estudianteGrupoJpaRepository.findAll());
    }
}
