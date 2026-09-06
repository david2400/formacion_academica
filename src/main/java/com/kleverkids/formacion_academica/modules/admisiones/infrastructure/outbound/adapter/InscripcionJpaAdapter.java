package com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.admisiones.application.output.inscripcion.InscripcionRepositoryPort;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion.ActualizarEstadoInscripcionDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion.CrearInscripcionDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.model.Inscripcion;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion.ListarInscripcionesFiltroDto;
import com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.mappers.InscripcionMapper;
import com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.persistence.mysql.entity.InscripcionEntity;
import com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.persistence.mysql.repository.InscripcionJpaRepository;
import com.kleverkids.formacion_academica.modules.estados.application.output.MotorEstadosPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class InscripcionJpaAdapter implements InscripcionRepositoryPort {

    /** Máquina que gobierna el ciclo de vida de la inscripción en el motor de estados. */
    public static final String MAQUINA = "INSCRIPCION_LIFECYCLE";

    /** Tipo de entidad con el que el motor identifica este recurso. */
    public static final String TIPO_ENTIDAD = "INSCRIPCION";

    private final InscripcionJpaRepository inscripcionJpaRepository;
    private final InscripcionMapper inscripcionMapper;
    private final MotorEstadosPort motorEstados;

    public InscripcionJpaAdapter(InscripcionJpaRepository inscripcionJpaRepository,
            InscripcionMapper inscripcionMapper,
            MotorEstadosPort motorEstados) {
        this.inscripcionJpaRepository = inscripcionJpaRepository;
        this.inscripcionMapper = inscripcionMapper;
        this.motorEstados = motorEstados;
    }

    /**
     * El estado inicial lo decide el motor, no un id fijo en el mapper.
     *
     * <p>El ciclo se arranca después de guardar porque el motor necesita el id
     * definitivo de la inscripción.
     */
    @Override
    public Inscripcion registrar(CrearInscripcionDto request) {
        InscripcionEntity entity = inscripcionMapper.toEntity(request);
        entity.setEstadoId(motorEstados.estadoInicial(MAQUINA).intValue());

        InscripcionEntity guardada = inscripcionJpaRepository.save(entity);
        motorEstados.iniciarCiclo(MAQUINA, TIPO_ENTIDAD, guardada.getId());

        return inscripcionMapper.toDomainModel(guardada);
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
     * Cambia el estado a través del motor.
     *
     * <p>Antes este método guardaba la entidad sin tocarla: el estado nunca cambiaba.
     * Ahora, además de cambiar de verdad, el motor valida que la transición sea
     * posible desde el estado actual y exige motivo al rechazar una inscripción.
     */
    @Override
    public Inscripcion actualizarEstado(ActualizarEstadoInscripcionDto request) {
        InscripcionEntity entity = inscripcionJpaRepository.findById(request.getInscripcionId())
                .orElseThrow(() -> new IllegalArgumentException("Inscripción no encontrada"));

        Long estadoResultante = motorEstados.moverAEstado(
                MAQUINA, TIPO_ENTIDAD, entity.getId(),
                request.getNuevoEstadoId(), request.getMotivo());

        entity.setEstadoId(estadoResultante.intValue());
        return inscripcionMapper.toDomainModel(inscripcionJpaRepository.save(entity));
    }

    @Override
    public void eliminar(Long inscripcionId) {
        inscripcionJpaRepository.deleteById(inscripcionId);
    }
}
