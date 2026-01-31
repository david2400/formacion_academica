package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.tematica.TematicaExamenRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tematica.ActualizarTematicaExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tematica.CrearTematicaExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tematica.TematicaExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers.TematicaExamenMapper;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.TematicaExamenEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository.TematicaExamenJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class TematicaExamenJpaAdapter implements TematicaExamenRepositoryPort {

    private final TematicaExamenJpaRepository tematicaExamenJpaRepository;

    public TematicaExamenJpaAdapter(TematicaExamenJpaRepository tematicaExamenJpaRepository) {
        this.tematicaExamenJpaRepository = tematicaExamenJpaRepository;
    }

    @Override
    public TematicaExamenDto guardar(CrearTematicaExamenDto request) {
        TematicaExamenEntity entity = TematicaExamenMapper.toEntity(request);
        return TematicaExamenMapper.toDto(tematicaExamenJpaRepository.save(entity));
    }

    @Override
    public TematicaExamenDto actualizar(ActualizarTematicaExamenDto request) {
        TematicaExamenEntity entity = tematicaExamenJpaRepository.findById(request.id())
                .orElseThrow(() -> new IllegalArgumentException("Temática no encontrada"));
        TematicaExamenMapper.applyUpdate(entity, request);
        return TematicaExamenMapper.toDto(tematicaExamenJpaRepository.save(entity));
    }

    @Override
    public List<TematicaExamenDto> listarPorExamen(UUID examenId) {
        return TematicaExamenMapper.toDtoList(
                tematicaExamenJpaRepository.findByExamenIdOrderByOrdenAsc(examenId)
        );
    }
}
