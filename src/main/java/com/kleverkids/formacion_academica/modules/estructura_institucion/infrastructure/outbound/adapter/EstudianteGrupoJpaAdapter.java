package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.estudiantegrupo.EstudianteGrupoRepositoryPort;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.estudiante_grupo.AsignarEstudianteGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.estudiante_grupo.CambiarEstadoEstudianteGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.EstudianteGrupo;
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
    public EstudianteGrupo asignar(AsignarEstudianteGrupoDto request) {
        EstudianteGrupoEntity entity = estudianteGrupoMapper.toEntity(request);
        return estudianteGrupoMapper.toDomainModel(estudianteGrupoJpaRepository.save(entity));
    }

    @Override
    public EstudianteGrupo cambiarEstado(CambiarEstadoEstudianteGrupoDto request) {
        EstudianteGrupoEntity entity = estudianteGrupoJpaRepository.findById(request.asignacionId())
                .orElseThrow(() -> new IllegalArgumentException("Asignación no encontrada"));
        entity.setEstadoLegacy(request.nuevoEstado()); // Usar campo legacy para String
        // TODO: Buscar y asignar EstadoEntity basado en el código del estado
        return estudianteGrupoMapper.toDomainModel(estudianteGrupoJpaRepository.save(entity));
    }

    @Override
    public List<EstudianteGrupo> listarPorGrupo(Long grupoId) {
        return estudianteGrupoMapper.toDomainModelList(estudianteGrupoJpaRepository.findByGrupoId(grupoId));
    }

    @Override
    public EstudianteGrupo consultarPorId(Long estudianteGrupoId) {
        return estudianteGrupoMapper.toDomainModel(
                estudianteGrupoJpaRepository.findById(estudianteGrupoId)
                        .orElseThrow(() -> new IllegalArgumentException("Asignación de estudiante grupo no encontrada"))
        );
    }

    @Override
    public void eliminar(Long estudianteGrupoId) {
        estudianteGrupoJpaRepository.deleteById(estudianteGrupoId);
    }

    @Override
    public List<EstudianteGrupo> listar() {
        return estudianteGrupoMapper.toDomainModelList(estudianteGrupoJpaRepository.findAll());
    }
}
