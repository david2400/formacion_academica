package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.examen_criterio.ExamenCriterioRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_criterio.ActualizarExamenCriterioDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_criterio.CrearExamenCriterioDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_criterio.ExamenCriterioDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers.ExamenCriterioMapper;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.ExamenCriterioEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository.ExamenCriterioJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ExamenCriterioJpaAdapter implements ExamenCriterioRepositoryPort {

    private final ExamenCriterioJpaRepository examenCriterioJpaRepository;
    private final ExamenCriterioMapper examenCriterioMapper;

    @Override
    public ExamenCriterioDto guardar(CrearExamenCriterioDto request) {
        ExamenCriterioEntity asignacion = examenCriterioMapper.toEntity(request);
        return examenCriterioMapper.toDto(examenCriterioJpaRepository.save(asignacion));
    }

    @Override
    public ExamenCriterioDto actualizar(ActualizarExamenCriterioDto request) {
        ExamenCriterioEntity asignacion = examenCriterioJpaRepository.findById(request.getId())
                .orElseThrow(() -> new IllegalArgumentException("Asignación de criterio no encontrada"));
        examenCriterioMapper.applyUpdate(asignacion, request);
        return examenCriterioMapper.toDto(examenCriterioJpaRepository.save(asignacion));
    }

    @Override
    public List<ExamenCriterioDto> listarPorExamen(Long examenId) {
        return examenCriterioMapper.toDtoList(examenCriterioJpaRepository.findByExamenIdOrderByOrdenAsc(examenId));
    }

    @Override
    public ExamenCriterioDto obtenerPorId(Long id) {
        return examenCriterioJpaRepository.findById(id)
                .map(examenCriterioMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Asignación de criterio no encontrada"));
    }

    @Override
    public void eliminar(Long id) {
        examenCriterioJpaRepository.deleteById(id);
    }
}
