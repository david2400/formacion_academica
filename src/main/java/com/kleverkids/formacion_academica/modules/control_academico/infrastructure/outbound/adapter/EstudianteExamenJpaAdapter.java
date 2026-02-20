package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.estudiante_examen.EstudianteExamenRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.estudiante_examen.EstudianteExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.estudiante_examen.RegistrarEstudianteExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers.EstudianteExamenMapper;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.EstudianteExamenEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository.EstudianteExamenJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class EstudianteExamenJpaAdapter implements EstudianteExamenRepositoryPort {

    private final EstudianteExamenJpaRepository repository;
    private final EstudianteExamenMapper mapper;

    @Override
    public EstudianteExamenDto registrar(RegistrarEstudianteExamenDto request) {
        EstudianteExamenEntity entity = mapper.toEntity(request);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public List<EstudianteExamenDto> listarPorExamen(UUID examenId) {
        return mapper.toDtoList(repository.findByExamenIdOrderByAsignadoEnAsc(examenId));
    }

    @Override
    public Optional<EstudianteExamenDto> buscarPorExamenYEstudiante(UUID examenId, UUID estudianteId) {
        return repository.findByExamenIdAndEstudianteId(examenId, estudianteId)
                .map(mapper::toDto);
    }
}
