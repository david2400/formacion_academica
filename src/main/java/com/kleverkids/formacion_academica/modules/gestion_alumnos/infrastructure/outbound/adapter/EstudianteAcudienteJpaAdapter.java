package com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.estudiante_acudiente.EstudianteAcudienteRepositoryPort;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante_acudiente.ActualizarEstudianteAcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante_acudiente.CrearEstudianteAcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante_acudiente.EstudianteAcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.mappers.RelacionEstudianteAcudienteMapper;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.persistence.mysql.entity.EstudianteAcudienteEntity;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.persistence.mysql.repository.EstudianteAcudienteJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class EstudianteAcudienteJpaAdapter implements EstudianteAcudienteRepositoryPort {

    private final EstudianteAcudienteJpaRepository relacionJpaRepository;

    public EstudianteAcudienteJpaAdapter(EstudianteAcudienteJpaRepository relacionJpaRepository) {
        this.relacionJpaRepository = relacionJpaRepository;
    }

    @Override
    public EstudianteAcudienteDto crear(CrearEstudianteAcudienteDto request) {
        EstudianteAcudienteEntity entity = RelacionEstudianteAcudienteMapper.toEntity(request);
        return RelacionEstudianteAcudienteMapper.toDto(relacionJpaRepository.save(entity));
    }

    @Override
    public EstudianteAcudienteDto actualizar(ActualizarEstudianteAcudienteDto request) {
        EstudianteAcudienteEntity entity = relacionJpaRepository.findById(request.relacionId())
                .orElseThrow(() -> new IllegalArgumentException("Relación no encontrada"));
        RelacionEstudianteAcudienteMapper.merge(entity, request);
        return RelacionEstudianteAcudienteMapper.toDto(relacionJpaRepository.save(entity));
    }

    @Override
    public Optional<EstudianteAcudienteDto> obtenerPorId(UUID relacionId) {
        return relacionJpaRepository.findById(relacionId).map(RelacionEstudianteAcudienteMapper::toDto);
    }

    @Override
    public List<EstudianteAcudienteDto> listarPorEstudiante(UUID estudianteId) {
        return RelacionEstudianteAcudienteMapper.toDtoList(relacionJpaRepository.findByEstudianteId(estudianteId));
    }

    @Override
    public List<EstudianteAcudienteDto> listarPorAcudiente(UUID acudienteId) {
        return RelacionEstudianteAcudienteMapper.toDtoList(relacionJpaRepository.findByAcudienteId(acudienteId));
    }

    @Override
    public boolean existeRelacionPrincipal(UUID estudianteId, UUID excluirRelacionId) {
        if (excluirRelacionId == null) {
            return relacionJpaRepository.existsByEstudianteIdAndEsPrincipalIsTrue(estudianteId);
        }
        return relacionJpaRepository.existsByEstudianteIdAndEsPrincipalIsTrueAndIdNot(estudianteId, excluirRelacionId);
    }
}
