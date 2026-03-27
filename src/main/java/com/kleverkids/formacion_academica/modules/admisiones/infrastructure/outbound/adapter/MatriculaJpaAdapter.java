package com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.admisiones.application.output.matricula.MatriculaRepositoryPort;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.matricula.CrearMatriculaDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.model.Matricula;
import com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.mappers.MatriculaMapper;
import com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.persistence.mysql.entity.MatriculaEntity;
import com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.persistence.mysql.repository.MatriculaJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MatriculaJpaAdapter implements MatriculaRepositoryPort {

    private final MatriculaJpaRepository matriculaJpaRepository;
    private final MatriculaMapper matriculaMapper;

    public MatriculaJpaAdapter(MatriculaJpaRepository matriculaJpaRepository, MatriculaMapper matriculaMapper) {
        this.matriculaJpaRepository = matriculaJpaRepository;
        this.matriculaMapper = matriculaMapper;
    }

    @Override
    public Matricula registrar(CrearMatriculaDto request) {
        MatriculaEntity entity = matriculaMapper.toEntity(request);
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
