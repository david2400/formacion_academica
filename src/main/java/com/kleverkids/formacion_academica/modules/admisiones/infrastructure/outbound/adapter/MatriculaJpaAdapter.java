package com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.admisiones.application.output.matricula.MatriculaRepositoryPort;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.matricula.CrearMatriculaDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.matricula.MatriculaDto;
import com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.mappers.MatriculaMapper;
import com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.persistence.mysql.entity.MatriculaEntity;
import com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.persistence.mysql.repository.MatriculaJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class MatriculaJpaAdapter implements MatriculaRepositoryPort {

    private final MatriculaJpaRepository matriculaJpaRepository;

    public MatriculaJpaAdapter(MatriculaJpaRepository matriculaJpaRepository) {
        this.matriculaJpaRepository = matriculaJpaRepository;
    }

    @Override
    public MatriculaDto registrar(CrearMatriculaDto request) {
        MatriculaEntity entity = MatriculaMapper.toEntity(request);
        return MatriculaMapper.toDto(matriculaJpaRepository.save(entity));
    }

    @Override
    public Optional<MatriculaDto> obtenerPorId(UUID matriculaId) {
        return matriculaJpaRepository.findById(matriculaId).map(MatriculaMapper::toDto);
    }

    @Override
    public List<MatriculaDto> listarPorEstudiante(UUID estudianteId) {
        return MatriculaMapper.toDtoList(matriculaJpaRepository.findByEstudianteId(estudianteId));
    }
}
