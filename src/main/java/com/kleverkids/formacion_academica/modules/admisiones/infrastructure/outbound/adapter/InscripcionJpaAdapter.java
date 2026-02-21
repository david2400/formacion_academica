package com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.admisiones.application.output.inscripcion.InscripcionRepositoryPort;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion.ActualizarEstadoInscripcionDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion.CrearInscripcionDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion.InscripcionDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion.ListarInscripcionesFiltroDto;
import com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.mappers.InscripcionMapper;
import com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.persistence.mysql.entity.InscripcionEntity;
import com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.persistence.mysql.repository.InscripcionJpaRepository;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.mappers.GrupoMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class InscripcionJpaAdapter implements InscripcionRepositoryPort {

    private final InscripcionJpaRepository inscripcionJpaRepository;
    private final InscripcionMapper inscripcionMapper;

    public InscripcionJpaAdapter(InscripcionJpaRepository inscripcionJpaRepository, InscripcionMapper inscripcionMapper) {
        this.inscripcionJpaRepository = inscripcionJpaRepository;
        this.inscripcionMapper = inscripcionMapper;
    }

    @Override
    public InscripcionDto registrar(CrearInscripcionDto request) {
        InscripcionEntity entity = inscripcionMapper.toEntity(request);
        return inscripcionMapper.toDto(inscripcionJpaRepository.save(entity));
    }

    @Override
    public Optional<InscripcionDto> obtenerPorId(UUID inscripcionId) {
        return inscripcionJpaRepository.findById(inscripcionId).map(inscripcionMapper::toDto);
    }

    @Override
    public List<InscripcionDto> listar(ListarInscripcionesFiltroDto filtro) {
        if (filtro == null) {
            return inscripcionMapper.toDtoList(inscripcionJpaRepository.findAll());
        }

        if (filtro.periodoAcademico() != null && !filtro.periodoAcademico().isBlank()) {
            return inscripcionMapper.toDtoList(inscripcionJpaRepository.findByPeriodoAcademico(filtro.periodoAcademico()));
        }

        if (filtro.estado() != null && !filtro.estado().isBlank()) {
            return inscripcionMapper.toDtoList(inscripcionJpaRepository.findByEstado(filtro.estado()));
        }

        return inscripcionMapper.toDtoList(inscripcionJpaRepository.findAll());
    }

    @Override
    public InscripcionDto actualizarEstado(ActualizarEstadoInscripcionDto request) {
        InscripcionEntity entity = inscripcionJpaRepository.findById(request.getInscripcionId())
                .orElseThrow(() -> new IllegalArgumentException("Inscripción no encontrada"));
        inscripcionMapper.applyEstado(entity, request);
        return inscripcionMapper.toDto(inscripcionJpaRepository.save(entity));
    }

    @Override
    public void eliminar(UUID inscripcionId) {
        inscripcionJpaRepository.deleteById(inscripcionId);
    }
}
