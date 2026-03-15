package com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.estudiante_acudiente.EstudianteAcudienteRepositoryPort;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante_acudiente.ActualizarEstudianteAcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante_acudiente.CrearEstudianteAcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante_acudiente.EstudianteAcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.mappers.RelacionEstudianteAcudienteMapper;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.persistence.mysql.entity.EstudianteAcudienteEntity;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.persistence.mysql.repository.EstudianteAcudienteJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class EstudianteAcudienteJpaAdapter implements EstudianteAcudienteRepositoryPort {

    private final EstudianteAcudienteJpaRepository relacionJpaRepository;
    private final RelacionEstudianteAcudienteMapper relacionMapper;

    @Override
    public EstudianteAcudienteDto crear(CrearEstudianteAcudienteDto request) {
        EstudianteAcudienteEntity entity = relacionMapper.toEntity(request);
        return relacionMapper.toDto(relacionJpaRepository.save(entity));
    }

    @Override
    public EstudianteAcudienteDto actualizar(ActualizarEstudianteAcudienteDto request) {
        EstudianteAcudienteEntity entity = relacionJpaRepository.findById(request.relacionId())
                .orElseThrow(() -> new IllegalArgumentException("Relación no encontrada"));
        relacionMapper.updateEntityFromDto(request, entity);
        return relacionMapper.toDto(relacionJpaRepository.save(entity));
    }

    @Override
    public Optional<EstudianteAcudienteDto> obtenerPorId(Long relacionId) {
        return relacionJpaRepository.findById(relacionId).map(relacionMapper::toDto);
    }

    @Override
    public List<EstudianteAcudienteDto> listarPorEstudiante(Long estudianteId) {
        return relacionMapper.toDtoList(relacionJpaRepository.findByEstudianteId(estudianteId));
    }

    @Override
    public List<EstudianteAcudienteDto> listarPorAcudiente(Long acudienteId) {
        return relacionMapper.toDtoList(relacionJpaRepository.findByAcudienteId(acudienteId));
    }

    @Override
    public boolean existeRelacionPrincipal(Long estudianteId, Long excluirRelacionId) {
        if (excluirRelacionId == null) {
            return relacionJpaRepository.existsByEstudianteIdAndEsPrincipalIsTrue(estudianteId);
        }
        return relacionJpaRepository.existsByEstudianteIdAndEsPrincipalIsTrueAndIdNot(estudianteId, excluirRelacionId);
    }

    @Override
    public void eliminar(Long relacionId) {
        relacionJpaRepository.deleteById(relacionId);
    }
}
