package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.tematica.TematicaRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tematica.ActualizarTematicaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tematica.CrearTematicaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tematica.TematicaDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers.TematicaMapper;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.TematicaEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository.TematicaJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@Component
public class TematicaJpaAdapter implements TematicaRepositoryPort {

    private final TematicaJpaRepository TematicaJpaRepository;
    private final TematicaMapper TematicaMapper;

    @Override
    public TematicaDto guardar(CrearTematicaDto request) {
        TematicaEntity entity = TematicaMapper.toEntity(request);
        return TematicaMapper.toDto(TematicaJpaRepository.save(entity));
    }

    @Override
    public TematicaDto actualizar(ActualizarTematicaDto request) {
        TematicaEntity entity = TematicaJpaRepository.findById(request.id())
                .orElseThrow(() -> new IllegalArgumentException("Temática no encontrada"));
        TematicaMapper.applyUpdate(entity, request);
        return TematicaMapper.toDto(TematicaJpaRepository.save(entity));
    }

    @Override
    public List<TematicaDto> listarPorExamen(UUID examenId) {
        return TematicaMapper.toDtoList(
                TematicaJpaRepository.findByExamenIdOrderByOrdenAsc(examenId)
        );
    }

    @Override
    public TematicaDto obtenerPorId(UUID tematicaId) {
        return TematicaJpaRepository.findById(tematicaId)
                .map(TematicaMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Temática no encontrada"));
    }

    @Override
    public void eliminar(UUID tematicaId) {
        TematicaJpaRepository.deleteById(tematicaId);
    }
}
