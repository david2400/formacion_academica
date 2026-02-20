package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.grado.GradoRepositoryPort;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grado.ActualizarGradoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grado.CrearGradoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grado.GradoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.mappers.GradoMapper;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity.GradoEntity;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.repository.GradoJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class GradoJpaAdapter implements GradoRepositoryPort {

    private final GradoJpaRepository gradoJpaRepository;
    private final GradoMapper gradoMapper;

    public GradoJpaAdapter(GradoJpaRepository gradoJpaRepository,
                           GradoMapper gradoMapper) {
        this.gradoJpaRepository = gradoJpaRepository;
        this.gradoMapper = gradoMapper;
    }

    @Override
    public GradoDto guardar(CrearGradoDto request) {
        return gradoMapper.toDto(gradoJpaRepository.save(gradoMapper.toEntity(request)));
    }

    @Override
    public GradoDto actualizar(ActualizarGradoDto request) {
        GradoEntity entity = gradoJpaRepository.findById(request.getId())
                .orElseThrow(() -> new IllegalArgumentException("Grado no encontrado"));

        return gradoMapper.toDto(gradoJpaRepository.save(entity));
    }

    @Override
    public boolean existePorNombreYNivel(String nombre, String nivelEducativo) {
        return gradoJpaRepository.existsByNombreAndNivelEducativo(nombre, nivelEducativo);
    }

    @Override
    public GradoDto obtenerPorId(java.util.UUID id) {
        return gradoJpaRepository.findById(id)
                .map(gradoMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Grado no encontrado"));
    }
}
