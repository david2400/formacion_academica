package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.pregunta.PreguntaBancoRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.ActualizarPreguntaBancoDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.CrearPreguntaBancoDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.PreguntaBancoDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers.PreguntaBancoMapper;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.PreguntaBancoEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository.PreguntaBancoJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class PreguntaBancoJpaAdapter implements PreguntaBancoRepositoryPort {

    private final PreguntaBancoJpaRepository preguntaBancoJpaRepository;

    public PreguntaBancoJpaAdapter(PreguntaBancoJpaRepository preguntaBancoJpaRepository) {
        this.preguntaBancoJpaRepository = preguntaBancoJpaRepository;
    }

    @Override
    public PreguntaBancoDto guardar(CrearPreguntaBancoDto request) {
        PreguntaBancoEntity entity = PreguntaBancoMapper.toEntity(request);
        return PreguntaBancoMapper.toDto(preguntaBancoJpaRepository.save(entity));
    }

    @Override
    public PreguntaBancoDto actualizar(ActualizarPreguntaBancoDto request) {
        PreguntaBancoEntity entity = preguntaBancoJpaRepository.findById(request.id())
                .orElseThrow(() -> new IllegalArgumentException("Pregunta no encontrada"));
        PreguntaBancoMapper.applyUpdate(entity, request);
        return PreguntaBancoMapper.toDto(preguntaBancoJpaRepository.save(entity));
    }

    @Override
    public List<PreguntaBancoDto> listarPorTematica(UUID tematicaId) {
        return PreguntaBancoMapper.toDtoList(preguntaBancoJpaRepository.findByTematicaId(tematicaId));
    }
}
