package com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.admisiones.application.output.matricula.MatriculaRepositoryPort;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.matricula.CrearMatriculaDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.matricula.MatriculaDto;
import com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.mappers.InscripcionMapper;
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
    private final MatriculaMapper matriculaMapper;

    public MatriculaJpaAdapter(MatriculaJpaRepository matriculaJpaRepository, MatriculaMapper matriculaMapper) {
        this.matriculaJpaRepository = matriculaJpaRepository;
        this.matriculaMapper = matriculaMapper;
    }

    @Override
    public MatriculaDto registrar(CrearMatriculaDto request) {
        MatriculaEntity entity = matriculaMapper.toEntity(request);
        return matriculaMapper.toDto(matriculaJpaRepository.save(entity));
    }

    @Override
    public Optional<MatriculaDto> obtenerPorId(UUID matriculaId) {
        return matriculaJpaRepository.findById(matriculaId).map(matriculaMapper::toDto);
    }

    @Override
    public List<MatriculaDto> listarPorEstudiante(UUID estudianteId) {
        return matriculaMapper.toDtoList(matriculaJpaRepository.findByEstudianteId(estudianteId));
    }
}
