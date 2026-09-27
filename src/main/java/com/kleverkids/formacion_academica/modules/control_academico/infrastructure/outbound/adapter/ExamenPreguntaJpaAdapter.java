package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.examen_pregunta.ExamenPreguntaRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_pregunta.ActualizarExamenPreguntaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_pregunta.CrearExamenPreguntaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_pregunta.ExamenPreguntaDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers.ExamenPreguntaMapper;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.ExamenPreguntaEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository.ExamenPreguntaJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ExamenPreguntaJpaAdapter implements ExamenPreguntaRepositoryPort {

    private final ExamenPreguntaJpaRepository examenPreguntaJpaRepository;
    private final ExamenPreguntaMapper examenPreguntaMapper;

    @Override
    public ExamenPreguntaDto guardar(CrearExamenPreguntaDto request) {
        ExamenPreguntaEntity asignacion = examenPreguntaMapper.toEntity(request);
        return examenPreguntaMapper.toDto(examenPreguntaJpaRepository.save(asignacion));
    }

    @Override
    public ExamenPreguntaDto actualizar(ActualizarExamenPreguntaDto request) {
        ExamenPreguntaEntity asignacion = examenPreguntaJpaRepository.findById(request.getId())
                .orElseThrow(() -> new IllegalArgumentException("Asignación de pregunta no encontrada"));
        examenPreguntaMapper.applyUpdate(asignacion, request);
        return examenPreguntaMapper.toDto(examenPreguntaJpaRepository.save(asignacion));
    }

    @Override
    public List<ExamenPreguntaDto> listarPorExamen(Long examenId) {
        return examenPreguntaMapper.toDtoList(examenPreguntaJpaRepository.findByExamenIdOrderByOrdenAsc(examenId));
    }

    @Override
    public ExamenPreguntaDto obtenerPorId(Long id) {
        return examenPreguntaJpaRepository.findById(id)
                .map(examenPreguntaMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Asignación de pregunta no encontrada"));
    }

    @Override
    public void eliminar(Long id) {
        examenPreguntaJpaRepository.deleteById(id);
    }
}
