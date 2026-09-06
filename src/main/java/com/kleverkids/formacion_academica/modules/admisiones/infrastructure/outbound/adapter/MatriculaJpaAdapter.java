package com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.admisiones.application.output.matricula.MatriculaRepositoryPort;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.matricula.ActualizarMatriculaDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.matricula.CrearMatriculaDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.model.Matricula;
import com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.mappers.MatriculaMapper;
import com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.persistence.mysql.entity.MatriculaEntity;
import com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.persistence.mysql.repository.MatriculaJpaRepository;
import com.kleverkids.formacion_academica.modules.estados.application.input.contexto.ConsultarEstadoContextoUseCase;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MatriculaJpaAdapter implements MatriculaRepositoryPort {

    /** Contexto con el que este recurso está registrado en el catálogo central. */
    public static final String CONTEXTO = "formacion_academica.admisiones.matricula";

    private final MatriculaJpaRepository matriculaJpaRepository;
    private final MatriculaMapper matriculaMapper;
    private final ConsultarEstadoContextoUseCase estadosDelContexto;

    public MatriculaJpaAdapter(MatriculaJpaRepository matriculaJpaRepository,
            MatriculaMapper matriculaMapper,
            ConsultarEstadoContextoUseCase estadosDelContexto) {
        this.matriculaJpaRepository = matriculaJpaRepository;
        this.matriculaMapper = matriculaMapper;
        this.estadosDelContexto = estadosDelContexto;
    }

    /** El estado inicial sale del catálogo, no de un id fijo en el mapper. */
    @Override
    public Matricula registrar(CrearMatriculaDto request) {
        MatriculaEntity entity = matriculaMapper.toEntity(request);
        entity.setEstadoId(estadosDelContexto.requerirEstadoInicial(CONTEXTO, null).intValue());
        return matriculaMapper.toDomainModel(matriculaJpaRepository.save(entity));
    }

    /**
     * Actualización parcial. No toca el estado: eso se hace por su propia operación
     * validando contra el catálogo del contexto.
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
