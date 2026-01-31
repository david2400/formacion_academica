package com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.estudiante.EstudianteRepositoryPort;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante.ActualizarEstudianteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante.CrearEstudianteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante.EstudianteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.mappers.EstudianteMapper;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.persistence.mysql.entity.EstudianteEntity;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.persistence.mysql.repository.EstudianteJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class EstudianteJpaAdapter implements EstudianteRepositoryPort {

    private final EstudianteJpaRepository estudianteJpaRepository;

    public EstudianteJpaAdapter(EstudianteJpaRepository estudianteJpaRepository) {
        this.estudianteJpaRepository = estudianteJpaRepository;
    }

    @Override
    public EstudianteDto guardar(CrearEstudianteDto request) {
        EstudianteEntity entity = EstudianteMapper.toEntity(request);
        return EstudianteMapper.toDto(estudianteJpaRepository.save(entity));
    }

    @Override
    public EstudianteDto actualizar(ActualizarEstudianteDto request) {
        EstudianteEntity entity = estudianteJpaRepository.findById(request.estudianteId())
                .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado"));
        EstudianteMapper.merge(entity, request);
        return EstudianteMapper.toDto(estudianteJpaRepository.save(entity));
    }

    @Override
    public Optional<EstudianteDto> obtenerPorId(UUID estudianteId) {
        return estudianteJpaRepository.findById(estudianteId).map(EstudianteMapper::toDto);
    }

    @Override
    public List<EstudianteDto> listar() {
        return EstudianteMapper.toDtoList(estudianteJpaRepository.findAll());
    }

    @Override
    public boolean existePorDocumento(String tipoDocumento, String numeroDocumento) {
        return estudianteJpaRepository.existsByTipoDocumentoAndNumeroDocumento(tipoDocumento, numeroDocumento);
    }
}
