package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.tematica.TematicaRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tematica.ActualizarTematicaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tematica.CrearTematicaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.tematica.Tematica;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers.TematicaMapper;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.TematicaEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository.TematicaJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@RequiredArgsConstructor
@Component
public class TematicaJpaAdapter implements TematicaRepositoryPort {

    private final TematicaJpaRepository TematicaJpaRepository;
    private final TematicaMapper TematicaMapper;

    @Override
    public Tematica guardar(CrearTematicaDto request) {
        TematicaEntity entity = TematicaMapper.toEntity(request);
        return TematicaMapper.toDomainModel(TematicaJpaRepository.save(entity));
    }

    @Override
    public Tematica actualizar(ActualizarTematicaDto request) {
        TematicaEntity entity = TematicaJpaRepository.findById(request.id())
                .orElseThrow(() -> new IllegalArgumentException("Temática no encontrada"));
        TematicaMapper.applyUpdate(entity, request);
        return TematicaMapper.toDomainModel(TematicaJpaRepository.save(entity));
    }

    @Override
    public List<Tematica> listarPorExamen(Long examenId) {
        return TematicaMapper.toDomainModelList(
                TematicaJpaRepository.findByExamenIdOrderByOrdenAsc(examenId)
        );
    }

    @Override
    public Tematica obtenerPorId(Long tematicaId) {
        return TematicaJpaRepository.findById(tematicaId)
                .map(TematicaMapper::toDomainModel)
                .orElseThrow(() -> new IllegalArgumentException("Temática no encontrada"));
    }

    @Override
    public void eliminar(Long tematicaId) {
        TematicaJpaRepository.deleteById(tematicaId);
    }
}
