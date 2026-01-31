package com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.acudiente.AcudienteRepositoryPort;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.acudiente.AcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.acudiente.ActualizarAcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.acudiente.CrearAcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.mappers.AcudienteMapper;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.persistence.mysql.entity.AcudienteEntity;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.persistence.mysql.repository.AcudienteJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class AcudienteJpaAdapter implements AcudienteRepositoryPort {

    private final AcudienteJpaRepository acudienteJpaRepository;

    public AcudienteJpaAdapter(AcudienteJpaRepository acudienteJpaRepository) {
        this.acudienteJpaRepository = acudienteJpaRepository;
    }

    @Override
    public AcudienteDto guardar(CrearAcudienteDto request) {
        AcudienteEntity entity = AcudienteMapper.toEntity(request);
        return AcudienteMapper.toDto(acudienteJpaRepository.save(entity));
    }

    @Override
    public AcudienteDto actualizar(ActualizarAcudienteDto request) {
        AcudienteEntity entity = acudienteJpaRepository.findById(request.acudienteId())
                .orElseThrow(() -> new IllegalArgumentException("Acudiente no encontrado"));
        AcudienteMapper.merge(entity, request);
        return AcudienteMapper.toDto(acudienteJpaRepository.save(entity));
    }

    @Override
    public Optional<AcudienteDto> obtenerPorId(UUID acudienteId) {
        return acudienteJpaRepository.findById(acudienteId).map(AcudienteMapper::toDto);
    }

    @Override
    public List<AcudienteDto> listarPorEstudiante(UUID estudianteId) {
        return AcudienteMapper.toDtoList(acudienteJpaRepository.findByEstudianteId(estudianteId));
    }

    @Override
    public boolean existePrincipalParaEstudiante(UUID estudianteId, UUID excluirAcudienteId) {
        if (excluirAcudienteId == null) {
            return acudienteJpaRepository.existsByEstudianteIdAndEsPrincipalIsTrue(estudianteId);
        }
        return acudienteJpaRepository.existsByEstudianteIdAndEsPrincipalIsTrueAndIdNot(estudianteId, excluirAcudienteId);
    }
}
