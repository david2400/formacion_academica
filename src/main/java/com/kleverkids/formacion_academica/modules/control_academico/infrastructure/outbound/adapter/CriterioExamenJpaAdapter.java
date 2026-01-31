package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.criterio.CriterioExamenRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio.ActualizarCriterioExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio.CrearCriterioExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio.CriterioExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers.CriterioExamenMapper;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.CriterioExamenEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository.CriterioExamenJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class CriterioExamenJpaAdapter implements CriterioExamenRepositoryPort {

    private final CriterioExamenJpaRepository criterioExamenJpaRepository;

    public CriterioExamenJpaAdapter(CriterioExamenJpaRepository criterioExamenJpaRepository) {
        this.criterioExamenJpaRepository = criterioExamenJpaRepository;
    }

    @Override
    public CriterioExamenDto guardar(CrearCriterioExamenDto request) {
        CriterioExamenEntity entity = CriterioExamenMapper.toEntity(request);
        return CriterioExamenMapper.toDto(criterioExamenJpaRepository.save(entity));
    }

    @Override
    public CriterioExamenDto actualizar(ActualizarCriterioExamenDto request) {
        CriterioExamenEntity entity = criterioExamenJpaRepository.findById(request.id())
                .orElseThrow(() -> new IllegalArgumentException("Criterio no encontrado"));
        CriterioExamenMapper.applyUpdate(entity, request);
        return CriterioExamenMapper.toDto(criterioExamenJpaRepository.save(entity));
    }

    @Override
    public List<CriterioExamenDto> listarPorExamen(UUID examenId) {
        return CriterioExamenMapper.toDtoList(
                criterioExamenJpaRepository.findByExamenIdOrderByOrdenAsc(examenId)
        );
    }
}
