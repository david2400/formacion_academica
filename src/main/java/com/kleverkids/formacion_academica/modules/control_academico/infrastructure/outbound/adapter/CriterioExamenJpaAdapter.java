package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.criterio.CriterioExamenRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio.ActualizarCriterioExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio.CrearCriterioExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio.CriterioExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers.CriterioExamenMapper;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.CriterioExamenEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository.CriterioExamenJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class CriterioExamenJpaAdapter implements CriterioExamenRepositoryPort {

    private final CriterioExamenJpaRepository criterioExamenJpaRepository;
    private final CriterioExamenMapper criterioExamenMapper;

    @Override
    public CriterioExamenDto guardar(CrearCriterioExamenDto request) {
        CriterioExamenEntity entity = criterioExamenMapper.toEntity(request);
        return criterioExamenMapper.toDto(criterioExamenJpaRepository.save(entity));
    }

    @Override
    public CriterioExamenDto actualizar(ActualizarCriterioExamenDto request) {
        CriterioExamenEntity entity = criterioExamenJpaRepository.findById(request.getId())
                .orElseThrow(() -> new IllegalArgumentException("Criterio no encontrado"));
        criterioExamenMapper.applyUpdate(entity, request);
        return criterioExamenMapper.toDto(criterioExamenJpaRepository.save(entity));
    }

    @Override
    public List<CriterioExamenDto> listarPorExamen(Long examenId) {
        return criterioExamenMapper.toDtoList(
                criterioExamenJpaRepository.findByExamenIdOrderByOrdenAsc(examenId)
        );
    }

    @Override
    public CriterioExamenDto obtenerPorId(Long criterioId) {
        return criterioExamenJpaRepository.findById(criterioId)
                .map(criterioExamenMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Criterio no encontrado"));
    }

    @Override
    public void eliminar(Long criterioId) {
        criterioExamenJpaRepository.deleteById(criterioId);
    }
}
