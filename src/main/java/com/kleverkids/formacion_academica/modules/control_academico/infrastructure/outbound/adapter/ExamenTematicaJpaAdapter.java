package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.examen_tematica.ExamenTematicaRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_tematica.CrearExamenTematicaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_tematica.ExamenTematicaDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers.ExamenTematicaMapper;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.examenes.ExamenTematicaEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository.ExamenTematicaJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ExamenTematicaJpaAdapter implements ExamenTematicaRepositoryPort {

    private final ExamenTematicaJpaRepository examenTematicaJpaRepository;
    private final ExamenTematicaMapper examenTematicaMapper;

    @Override
    public ExamenTematicaDto asignar(CrearExamenTematicaDto request) {
        if (examenTematicaJpaRepository.existsByExamenIdAndTematicaId(request.getExamenId(), request.getTematicaId())) {
            throw new IllegalArgumentException("La temática ya está asignada a este examen");
        }
        ExamenTematicaEntity asignacion = examenTematicaMapper.toEntity(request);
        return examenTematicaMapper.toDto(examenTematicaJpaRepository.save(asignacion));
    }

    @Override
    public List<ExamenTematicaDto> listarPorExamen(Long examenId) {
        return examenTematicaMapper.toDtoList(examenTematicaJpaRepository.findByExamenId(examenId));
    }

    @Override
    public void eliminar(Long examenId, Long tematicaId) {
        examenTematicaJpaRepository.findByExamenIdAndTematicaId(examenId, tematicaId)
                .ifPresentOrElse(examenTematicaJpaRepository::delete,
                        () -> { throw new IllegalArgumentException("La asignación no existe"); });
    }
}
