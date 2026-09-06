package com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.admisiones.application.output.matricula.MatriculaRepositoryPort;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.matricula.ActualizarMatriculaDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.matricula.CrearMatriculaDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.model.Matricula;
import com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.mappers.MatriculaMapper;
import com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.persistence.mysql.entity.MatriculaEntity;
import com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.persistence.mysql.repository.MatriculaJpaRepository;
import com.kleverkids.formacion_academica.modules.estados.application.output.MotorEstadosPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MatriculaJpaAdapter implements MatriculaRepositoryPort {

    /** Máquina que gobierna el ciclo de vida de la matrícula en el motor de estados. */
    public static final String MAQUINA = "MATRICULA_LIFECYCLE";

    /** Tipo de entidad con el que el motor identifica este recurso. */
    public static final String TIPO_ENTIDAD = "MATRICULA";

    private final MatriculaJpaRepository matriculaJpaRepository;
    private final MatriculaMapper matriculaMapper;
    private final MotorEstadosPort motorEstados;

    public MatriculaJpaAdapter(MatriculaJpaRepository matriculaJpaRepository,
            MatriculaMapper matriculaMapper,
            MotorEstadosPort motorEstados) {
        this.matriculaJpaRepository = matriculaJpaRepository;
        this.matriculaMapper = matriculaMapper;
        this.motorEstados = motorEstados;
    }

    /**
     * El estado inicial lo decide el motor, no un id fijo en el mapper.
     *
     * <p>El ciclo se arranca después de guardar porque el motor necesita el id
     * definitivo de la matrícula.
     */
    @Override
    public Matricula registrar(CrearMatriculaDto request) {
        MatriculaEntity entity = matriculaMapper.toEntity(request);
        entity.setEstadoId(motorEstados.estadoInicial(MAQUINA).intValue());

        MatriculaEntity guardada = matriculaJpaRepository.save(entity);
        motorEstados.iniciarCiclo(MAQUINA, TIPO_ENTIDAD, guardada.getId());

        return matriculaMapper.toDomainModel(guardada);
    }

    /**
     * Actualización parcial. No toca el estado: eso se hace por su propia operación
     * pasando por el motor de estados.
     */
    @Override
    public Matricula actualizar(ActualizarMatriculaDto request) {
        MatriculaEntity entity = matriculaJpaRepository.findById(request.getMatriculaId())
                .orElseThrow(() -> new IllegalArgumentException("Matrícula no encontrada"));
        matriculaMapper.updateEntityFromDto(request, entity);
        return matriculaMapper.toDomainModel(matriculaJpaRepository.save(entity));
    }

    @Override
    public Optional<Matricula> obtenerPorId(Long matriculaId) {
        return matriculaJpaRepository.findById(matriculaId).map(matriculaMapper::toDomainModel);
    }

    @Override
    public List<Matricula> listarPorEstudiante(Long estudianteId) {
        return matriculaMapper.toDomainModelList(matriculaJpaRepository.findByEstudianteId(estudianteId));
    }

    @Override
    public void eliminar(Long matriculaId) {
        matriculaJpaRepository.deleteById(matriculaId);
    }
}
