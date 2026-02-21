package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.pregunta.PreguntaBancoRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.ActualizarPreguntaBancoDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.CrearPreguntaBancoDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.PreguntaBancoDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers.PreguntaBancoMapper;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.pregunta.PreguntaBancoEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository.PreguntaBancoJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class PreguntaBancoJpaAdapter implements PreguntaBancoRepositoryPort {

    private final PreguntaBancoJpaRepository preguntaBancoJpaRepository;
    private final PreguntaBancoMapper preguntaBancoMapper;

    @Override
    public PreguntaBancoDto guardar(CrearPreguntaBancoDto request) {
        PreguntaBancoEntity entity = preguntaBancoMapper.toEntity(request);
        return preguntaBancoMapper.toDto(preguntaBancoJpaRepository.save(entity));
    }

    @Override
    public PreguntaBancoDto actualizar(ActualizarPreguntaBancoDto request) {
        PreguntaBancoEntity entity = preguntaBancoJpaRepository.findById(request.id())
                .orElseThrow(() -> new IllegalArgumentException("Pregunta no encontrada"));
        preguntaBancoMapper.applyUpdate(entity, request);
        return preguntaBancoMapper.toDto(preguntaBancoJpaRepository.save(entity));
    }

    @Override
    public List<PreguntaBancoDto> listarPorTematica(UUID tematicaId) {
        return preguntaBancoMapper.toDtoList(preguntaBancoJpaRepository.findByTematicaId(tematicaId));
    }

    @Override
    public PreguntaBancoDto obtenerPorId(UUID preguntaId) {
        return preguntaBancoJpaRepository.findById(preguntaId)
                .map(preguntaBancoMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Pregunta no encontrada"));
    }

    @Override
    public void eliminar(UUID preguntaId) {
        preguntaBancoJpaRepository.deleteById(preguntaId);
    }
}
