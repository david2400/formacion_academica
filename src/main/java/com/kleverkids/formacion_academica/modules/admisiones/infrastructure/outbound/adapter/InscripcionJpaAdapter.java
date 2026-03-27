package com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.admisiones.application.output.inscripcion.InscripcionRepositoryPort;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion.ActualizarEstadoInscripcionDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion.CrearInscripcionDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.model.Inscripcion;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion.ListarInscripcionesFiltroDto;
import com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.mappers.InscripcionMapper;
import com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.persistence.mysql.entity.InscripcionEntity;
import com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.persistence.mysql.repository.InscripcionJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class InscripcionJpaAdapter implements InscripcionRepositoryPort {

    private final InscripcionJpaRepository inscripcionJpaRepository;
    private final InscripcionMapper inscripcionMapper;

    public InscripcionJpaAdapter(InscripcionJpaRepository inscripcionJpaRepository, InscripcionMapper inscripcionMapper) {
        this.inscripcionJpaRepository = inscripcionJpaRepository;
        this.inscripcionMapper = inscripcionMapper;
    }

    @Override
    public Inscripcion registrar(CrearInscripcionDto request) {
        InscripcionEntity entity = inscripcionMapper.toEntity(request);
        return inscripcionMapper.toDomainModel(inscripcionJpaRepository.save(entity));
    }

    @Override
    public Optional<Inscripcion> obtenerPorId(Long inscripcionId) {
        return inscripcionJpaRepository.findById(inscripcionId).map(inscripcionMapper::toDomainModel);
    }

    @Override
    public List<Inscripcion> listar(ListarInscripcionesFiltroDto filtro) {
        if (filtro == null) {
            return inscripcionMapper.toDomainModelList(inscripcionJpaRepository.findAll());
        }

        if (filtro.periodoAcademico() != null && !filtro.periodoAcademico().isBlank()) {
            return inscripcionMapper.toDomainModelList(inscripcionJpaRepository.findByPeriodoAcademico(filtro.periodoAcademico()));
        }

        if (filtro.estado() != null && !filtro.estado().isBlank()) {
            return inscripcionMapper.toDomainModelList(inscripcionJpaRepository.findByEstado(filtro.estado()));
        }

        return inscripcionMapper.toDomainModelList(inscripcionJpaRepository.findAll());
    }

    @Override
    public Inscripcion actualizarEstado(ActualizarEstadoInscripcionDto request) {
        InscripcionEntity entity = inscripcionJpaRepository.findById(request.getInscripcionId())
                .orElseThrow(() -> new IllegalArgumentException("Inscripción no encontrada"));
        // Actualizar solo los campos que existen en la entidad

        return inscripcionMapper.toDomainModel(inscripcionJpaRepository.save(entity));
    }

    @Override
    public void eliminar(Long inscripcionId) {
        inscripcionJpaRepository.deleteById(inscripcionId);
    }
}
