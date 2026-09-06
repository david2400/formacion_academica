package com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.estudiante_acudiente.EstudianteAcudienteRepositoryPort;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante_acudiente.ActualizarEstudianteAcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante_acudiente.CrearEstudianteAcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model.EstudianteAcudiente;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.mappers.RelacionEstudianteAcudienteMapper;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.persistence.mysql.entity.EstudianteAcudienteEntity;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.persistence.mysql.repository.EstudianteAcudienteJpaRepository;
import lombok.RequiredArgsConstructor;
import com.kleverkids.formacion_academica.modules.estados.application.output.MotorEstadosPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class EstudianteAcudienteJpaAdapter implements EstudianteAcudienteRepositoryPort {

    /** Máquina que gobierna el ciclo de vida del vínculo en el motor de estados. */
    public static final String MAQUINA = "VINCULO_ACUDIENTE_LIFECYCLE";

    /** Tipo de entidad con el que el motor identifica este recurso. */
    public static final String TIPO_ENTIDAD = "ESTUDIANTE_ACUDIENTE";

    private final EstudianteAcudienteJpaRepository relacionJpaRepository;
    private final RelacionEstudianteAcudienteMapper relacionMapper;
    private final MotorEstadosPort motorEstados;

    /**
     * El estado inicial lo decide el motor, no un id fijo en el mapper.
     *
     * <p>El ciclo se arranca después de guardar porque el motor necesita el id
     * definitivo del vínculo.
     */
    @Override
    public EstudianteAcudiente crear(CrearEstudianteAcudienteDto request) {
        EstudianteAcudienteEntity entity = relacionMapper.toEntity(request);
        entity.setEstadoId(motorEstados.estadoInicial(MAQUINA).intValue());

        EstudianteAcudienteEntity guardado = relacionJpaRepository.save(entity);
        motorEstados.iniciarCiclo(MAQUINA, TIPO_ENTIDAD, guardado.getId());

        return relacionMapper.toDomain(guardado);
    }

    @Override
    public EstudianteAcudiente actualizar(ActualizarEstudianteAcudienteDto request) {
        EstudianteAcudienteEntity entity = relacionJpaRepository.findById(request.getId())
                .orElseThrow(() -> new IllegalArgumentException("Relación no encontrada"));
        relacionMapper.updateEntityFromDto(request, entity);
        return relacionMapper.toDomain(relacionJpaRepository.save(entity));
    }

    @Override
    public Optional<EstudianteAcudiente> obtenerPorId(Long relacionId) {
        return relacionJpaRepository.findById(relacionId).map(relacionMapper::toDomain);
    }

    @Override
    public List<EstudianteAcudiente> listarPorEstudiante(Long estudianteId) {
        return relacionMapper.toDomainList(relacionJpaRepository.findByEstudianteId(estudianteId));
    }

    @Override
    public List<EstudianteAcudiente> listarPorAcudiente(Long acudienteId) {
        return relacionMapper.toDomainList(relacionJpaRepository.findByAcudienteId(acudienteId));
    }

    @Override
    public boolean existeRelacionPrincipal(Long estudianteId, Long excluirRelacionId) {
        if (excluirRelacionId == null) {
            return relacionJpaRepository.existsByEstudianteIdAndEsPrincipalIsTrue(estudianteId);
        }
        return relacionJpaRepository.existsByEstudianteIdAndEsPrincipalIsTrueAndIdNot(estudianteId, excluirRelacionId);
    }

    @Override
    public void eliminar(Long relacionId) {
        relacionJpaRepository.deleteById(relacionId);
    }
}
