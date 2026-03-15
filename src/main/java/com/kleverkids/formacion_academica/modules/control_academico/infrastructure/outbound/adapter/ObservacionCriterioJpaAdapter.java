package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.observacion.ObservacionCriterioRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.observacion.ActualizarObservacionCriterioDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.observacion.ObservacionCriterioDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.observacion.RegistrarObservacionCriterioDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers.ObservacionCriterioMapper;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.ObservacionCriterioEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository.ObservacionCriterioJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@RequiredArgsConstructor
@Component
public class ObservacionCriterioJpaAdapter implements ObservacionCriterioRepositoryPort {

    private final ObservacionCriterioJpaRepository observacionCriterioJpaRepository;
    private final ObservacionCriterioMapper observacionCriterioMapper;

    @Override
    public ObservacionCriterioDto registrar(RegistrarObservacionCriterioDto request) {
        ObservacionCriterioEntity entity = observacionCriterioMapper.toEntity(request);
        return observacionCriterioMapper.toDto(observacionCriterioJpaRepository.save(entity));
    }

    @Override
    public ObservacionCriterioDto actualizar(ActualizarObservacionCriterioDto request) {
        ObservacionCriterioEntity entity = observacionCriterioJpaRepository.findById(request.id())
                .orElseThrow(() -> new IllegalArgumentException("Observación no encontrada"));
        observacionCriterioMapper.applyUpdate(entity, request);
        return observacionCriterioMapper.toDto(observacionCriterioJpaRepository.save(entity));
    }

    @Override
    public List<ObservacionCriterioDto> listarPorEstudiante(Long examenId, Long estudianteId) {
        return observacionCriterioMapper.toDtoList(
                observacionCriterioJpaRepository.findByExamenIdAndEstudianteId(examenId, estudianteId)
        );
    }

    @Override
    public ObservacionCriterioDto obtenerPorId(Long observacionId) {
        return observacionCriterioJpaRepository.findById(observacionId)
                .map(observacionCriterioMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Observación no encontrada"));
    }

    @Override
    public void eliminar(Long observacionId) {
        observacionCriterioJpaRepository.deleteById(observacionId);
    }
}
