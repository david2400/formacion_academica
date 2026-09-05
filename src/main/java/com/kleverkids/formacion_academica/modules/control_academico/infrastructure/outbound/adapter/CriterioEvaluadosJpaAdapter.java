package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.criterio_evaluado.CriterioEvaluadosRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio_evaluado.ActualizarCriterioEvaluadosDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio_evaluado.CrearCriterioEvaluadosDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.CriterioEvaluados;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers.CriterioEvaluadosMapper;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.CriterioEvaluadosEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository.CriterioEvaluadosJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class CriterioEvaluadosJpaAdapter implements CriterioEvaluadosRepositoryPort {

    private final CriterioEvaluadosJpaRepository criterioExamenJpaRepository;
    private final CriterioEvaluadosMapper criterioExamenMapper;

    @Override
    public CriterioEvaluados guardar(CrearCriterioEvaluadosDto request) {
        CriterioEvaluadosEntity criterio = criterioExamenMapper.toEntity(request);
        return criterioExamenMapper.toDto(criterioExamenJpaRepository.save(criterio));
    }

    @Override
    public CriterioEvaluados actualizar(ActualizarCriterioEvaluadosDto request) {
        CriterioEvaluadosEntity criterio = criterioExamenJpaRepository.findById(request.getId())
                .orElseThrow(() -> new IllegalArgumentException("Criterio no encontrado"));
        criterioExamenMapper.applyUpdate(criterio, request);
        return criterioExamenMapper.toDto(criterioExamenJpaRepository.save(criterio));
    }

    @Override
    public List<CriterioEvaluados> listarPorExamen(Long examenId) {
        return criterioExamenMapper.toDtoList(criterioExamenJpaRepository.findAll());
    }

    @Override
    public CriterioEvaluados obtenerPorId(Long criterioId) {
        return criterioExamenJpaRepository.findById(criterioId)
                .map(criterioExamenMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Criterio no encontrado"));
    }

    @Override
    public void eliminar(Long criterioId) {
        criterioExamenJpaRepository.deleteById(criterioId);
    }
}
