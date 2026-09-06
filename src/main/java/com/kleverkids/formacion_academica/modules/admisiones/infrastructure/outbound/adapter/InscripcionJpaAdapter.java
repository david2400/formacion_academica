package com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.admisiones.application.output.inscripcion.InscripcionRepositoryPort;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion.ActualizarEstadoInscripcionDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion.CrearInscripcionDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.model.Inscripcion;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion.ListarInscripcionesFiltroDto;
import com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.mappers.InscripcionMapper;
import com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.persistence.mysql.entity.InscripcionEntity;
import com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.persistence.mysql.repository.InscripcionJpaRepository;
import com.kleverkids.formacion_academica.modules.estados.application.input.contexto.ConsultarEstadoContextoUseCase;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class InscripcionJpaAdapter implements InscripcionRepositoryPort {

    /** Contexto con el que este recurso está registrado en el catálogo central. */
    public static final String CONTEXTO = "formacion_academica.admisiones.inscripcion";

    private final InscripcionJpaRepository inscripcionJpaRepository;
    private final InscripcionMapper inscripcionMapper;
    private final ConsultarEstadoContextoUseCase estadosDelContexto;

    public InscripcionJpaAdapter(InscripcionJpaRepository inscripcionJpaRepository,
            InscripcionMapper inscripcionMapper,
            ConsultarEstadoContextoUseCase estadosDelContexto) {
        this.inscripcionJpaRepository = inscripcionJpaRepository;
        this.inscripcionMapper = inscripcionMapper;
        this.estadosDelContexto = estadosDelContexto;
    }

    /** El estado inicial sale del catálogo, no de un id fijo en el mapper. */
    @Override
    public Inscripcion registrar(CrearInscripcionDto request) {
        InscripcionEntity entity = inscripcionMapper.toEntity(request);
        entity.setEstadoId(estadosDelContexto.requerirEstadoInicial(CONTEXTO, null).intValue());
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
            return inscripcionMapper
                    .toDomainModelList(inscripcionJpaRepository.findByPeriodoAcademico(filtro.periodoAcademico()));
        }

        if (filtro.estado() != null && !filtro.estado().isBlank()) {
            try {
                Integer estadoId = Integer.parseInt(filtro.estado());
                return inscripcionMapper.toDomainModelList(inscripcionJpaRepository.findByEstadoId(estadoId));
            } catch (NumberFormatException e) {
                return List.of(); // El filtro no es un id de estado válido
            }
        }

        return inscripcionMapper.toDomainModelList(inscripcionJpaRepository.findAll());
    }

    /**
     * Cambia el estado validando contra la parametrización del contexto.
     *
     * <p>Antes este método guardaba la entidad sin tocarla: el estado nunca cambiaba.
     */
    @Override
    public Inscripcion actualizarEstado(ActualizarEstadoInscripcionDto request) {
        if (!estadosDelContexto.estaRegistrado(CONTEXTO, request.getNuevoEstadoId(), request.getIdEmpresa())) {
            throw new IllegalArgumentException("El estado " + request.getNuevoEstadoId()
                    + " no está habilitado para el contexto '" + CONTEXTO + "'");
        }

        InscripcionEntity entity = inscripcionJpaRepository.findById(request.getInscripcionId())
                .orElseThrow(() -> new IllegalArgumentException("Inscripción no encontrada"));

        entity.setEstadoId(request.getNuevoEstadoId().intValue());
        return inscripcionMapper.toDomainModel(inscripcionJpaRepository.save(entity));
    }

    @Override
    public void eliminar(Long inscripcionId) {
        inscripcionJpaRepository.deleteById(inscripcionId);
    }
}
