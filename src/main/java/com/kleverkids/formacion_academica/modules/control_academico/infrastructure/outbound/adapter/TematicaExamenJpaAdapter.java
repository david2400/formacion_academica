package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.tematica.TematicaExamenRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tematica.ActualizarTematicaExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tematica.CrearTematicaExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tematica.TematicaExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers.TematicaExamenMapper;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.TematicaExamenEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository.TematicaExamenJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@Component
public class TematicaExamenJpaAdapter implements TematicaExamenRepositoryPort {

    private final TematicaExamenJpaRepository tematicaExamenJpaRepository;
    private final TematicaExamenMapper tematicaExamenMapper;

    @Override
    public TematicaExamenDto guardar(CrearTematicaExamenDto request) {
        TematicaExamenEntity entity = tematicaExamenMapper.toEntity(request);
        return tematicaExamenMapper.toDto(tematicaExamenJpaRepository.save(entity));
    }

    @Override
    public TematicaExamenDto actualizar(ActualizarTematicaExamenDto request) {
        TematicaExamenEntity entity = tematicaExamenJpaRepository.findById(request.id())
                .orElseThrow(() -> new IllegalArgumentException("Temática no encontrada"));
        tematicaExamenMapper.applyUpdate(entity, request);
        return tematicaExamenMapper.toDto(tematicaExamenJpaRepository.save(entity));
    }

    @Override
    public List<TematicaExamenDto> listarPorExamen(UUID examenId) {
        return tematicaExamenMapper.toDtoList(
                tematicaExamenJpaRepository.findByExamenIdOrderByOrdenAsc(examenId)
        );
    }
}
