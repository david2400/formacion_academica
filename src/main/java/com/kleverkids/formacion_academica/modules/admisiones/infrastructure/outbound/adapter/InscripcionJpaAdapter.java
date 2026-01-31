package com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.admisiones.application.output.inscripcion.InscripcionRepositoryPort;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion.ActualizarEstadoInscripcionDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion.CrearInscripcionDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion.InscripcionDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion.ListarInscripcionesFiltroDto;
import com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.mappers.InscripcionMapper;
import com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.persistence.mysql.entity.InscripcionEntity;
import com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.persistence.mysql.repository.InscripcionJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class InscripcionJpaAdapter implements InscripcionRepositoryPort {

    private final InscripcionJpaRepository inscripcionJpaRepository;

    public InscripcionJpaAdapter(InscripcionJpaRepository inscripcionJpaRepository) {
        this.inscripcionJpaRepository = inscripcionJpaRepository;
    }

    @Override
    public InscripcionDto registrar(CrearInscripcionDto request) {
        InscripcionEntity entity = InscripcionMapper.toEntity(request);
        return InscripcionMapper.toDto(inscripcionJpaRepository.save(entity));
    }

    @Override
    public Optional<InscripcionDto> obtenerPorId(UUID inscripcionId) {
        return inscripcionJpaRepository.findById(inscripcionId).map(InscripcionMapper::toDto);
    }

    @Override
    public List<InscripcionDto> listar(ListarInscripcionesFiltroDto filtro) {
        if (filtro == null) {
            return InscripcionMapper.toDtoList(inscripcionJpaRepository.findAll());
        }

        if (filtro.periodoAcademico() != null && !filtro.periodoAcademico().isBlank()) {
            return InscripcionMapper.toDtoList(inscripcionJpaRepository.findByPeriodoAcademico(filtro.periodoAcademico()));
        }

        if (filtro.estado() != null && !filtro.estado().isBlank()) {
            return InscripcionMapper.toDtoList(inscripcionJpaRepository.findByEstado(filtro.estado()));
        }

        return InscripcionMapper.toDtoList(inscripcionJpaRepository.findAll());
    }

    @Override
    public InscripcionDto actualizarEstado(ActualizarEstadoInscripcionDto request) {
        InscripcionEntity entity = inscripcionJpaRepository.findById(request.inscripcionId())
                .orElseThrow(() -> new IllegalArgumentException("Inscripción no encontrada"));
        InscripcionMapper.applyEstado(entity, request);
        return InscripcionMapper.toDto(inscripcionJpaRepository.save(entity));
    }
}
