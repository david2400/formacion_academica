package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.observacion.ObservacionCriterioRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.observacion.ActualizarObservacionCriterioDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.observacion.ObservacionCriterioDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.observacion.RegistrarObservacionCriterioDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers.ObservacionCriterioMapper;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.ObservacionCriterioEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository.ObservacionCriterioJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ObservacionCriterioJpaAdapter implements ObservacionCriterioRepositoryPort {

    private final ObservacionCriterioJpaRepository observacionCriterioJpaRepository;

    public ObservacionCriterioJpaAdapter(ObservacionCriterioJpaRepository observacionCriterioJpaRepository) {
        this.observacionCriterioJpaRepository = observacionCriterioJpaRepository;
    }

    @Override
    public ObservacionCriterioDto registrar(RegistrarObservacionCriterioDto request) {
        ObservacionCriterioEntity entity = ObservacionCriterioMapper.toEntity(request);
        return ObservacionCriterioMapper.toDto(observacionCriterioJpaRepository.save(entity));
    }

    @Override
    public ObservacionCriterioDto actualizar(ActualizarObservacionCriterioDto request) {
        ObservacionCriterioEntity entity = observacionCriterioJpaRepository.findById(request.id())
                .orElseThrow(() -> new IllegalArgumentException("Observación no encontrada"));
        ObservacionCriterioMapper.applyUpdate(entity, request);
        return ObservacionCriterioMapper.toDto(observacionCriterioJpaRepository.save(entity));
    }

    @Override
    public List<ObservacionCriterioDto> listarPorEstudiante(UUID examenId, UUID estudianteId) {
        return ObservacionCriterioMapper.toDtoList(
                observacionCriterioJpaRepository.findByExamenIdAndEstudianteId(examenId, estudianteId)
        );
    }
}
