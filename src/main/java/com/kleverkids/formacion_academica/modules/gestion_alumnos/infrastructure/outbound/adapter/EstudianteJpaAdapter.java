package com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.estudiante.EstudianteRepositoryPort;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model.Estudiante;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante.CrearEstudianteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante.UpdateEstudianteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.mappers.EstudianteMapper;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.persistence.mysql.entity.EstudianteEntity;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.persistence.mysql.repository.EstudianteJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class EstudianteJpaAdapter implements EstudianteRepositoryPort {

    private final EstudianteJpaRepository estudianteJpaRepository;
    private final EstudianteMapper estudianteMapper;

    @Override
    public Estudiante guardar(CrearEstudianteDto request) {
        EstudianteEntity entity = estudianteMapper.toEntity(request);
        return estudianteMapper.toDomainModel(estudianteJpaRepository.save(entity));
    }

    @Override
    public Estudiante actualizar(UpdateEstudianteDto request) {
        EstudianteEntity entity = estudianteJpaRepository.findByIdAndActivoTrue(request.getEstudianteId())
                .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado"));
        estudianteMapper.updateEntityFromDto(request, entity);
        return estudianteMapper.toDomainModel(estudianteJpaRepository.save(entity));
    }

    @Override
    public Optional<Estudiante> obtenerPorId(Long estudianteId) {
        return estudianteJpaRepository.findByIdAndActivoTrue(estudianteId).map(estudianteMapper::toDomainModel);
    }

    @Override
    public List<Estudiante> listar() {
        return estudianteMapper.toDomainModelList(estudianteJpaRepository.findByActivoTrue());
    }

    @Override
    public Page<Estudiante> listar(Pageable pageable) {
        return estudianteJpaRepository.findByActivoTrue(pageable).map(estudianteMapper::toDomainModel);
    }

    @Override
    public boolean existePorDocumento(String tipoDocumento, String numeroDocumento) {
        return estudianteJpaRepository.existsByTipoDocumentoAndNumeroDocumento(tipoDocumento, numeroDocumento);
    }

    @Override
    public void eliminar(Long estudianteId) {
        EstudianteEntity entity = estudianteJpaRepository.findByIdAndActivoTrue(estudianteId)
                .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado"));
        entity.setActivo(false);
        estudianteJpaRepository.save(entity);
    }
}
