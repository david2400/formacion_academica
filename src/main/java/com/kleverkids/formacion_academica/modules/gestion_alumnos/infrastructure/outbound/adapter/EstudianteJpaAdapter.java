package com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.estudiante.EstudianteRepositoryPort;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante.CrearEstudianteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante.EstudianteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante.UpdateEstudianteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.mappers.EstudianteMapper;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.persistence.mysql.entity.EstudianteEntity;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.persistence.mysql.repository.EstudianteJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class EstudianteJpaAdapter implements EstudianteRepositoryPort {

    private final EstudianteJpaRepository estudianteJpaRepository;
    private final EstudianteMapper estudianteMapper;

    @Override
    public EstudianteDto guardar(CrearEstudianteDto request) {
        EstudianteEntity entity = estudianteMapper.toEntity(request);
        return estudianteMapper.toDto(estudianteJpaRepository.save(entity));
    }

    @Override
    public EstudianteDto actualizar(UpdateEstudianteDto request) {
        EstudianteEntity entity = estudianteJpaRepository.findById(request.getEstudianteId())
                .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado"));
        estudianteMapper.updateEntityFromDto(request, entity);
        return estudianteMapper.toDto(estudianteJpaRepository.save(entity));
    }

    @Override
    public Optional<EstudianteDto> obtenerPorId(UUID estudianteId) {
        return estudianteJpaRepository.findById(estudianteId).map(estudianteMapper::toDto);
    }

    @Override
    public List<EstudianteDto> listar() {
        return estudianteMapper.toDtoList(estudianteJpaRepository.findAll());
    }

    @Override
    public boolean existePorDocumento(String tipoDocumento, String numeroDocumento) {
        return estudianteJpaRepository.existsByTipoDocumentoAndNumeroDocumento(tipoDocumento, numeroDocumento);
    }
}
