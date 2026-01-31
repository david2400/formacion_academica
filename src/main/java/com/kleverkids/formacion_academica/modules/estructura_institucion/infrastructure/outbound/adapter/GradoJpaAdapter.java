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

    public GradoJpaAdapter(GradoJpaRepository gradoJpaRepository) {
        this.gradoJpaRepository = gradoJpaRepository;
    }

    @Override
    public GradoDto guardar(CrearGradoDto request) {
        return GradoMapper.toDto(gradoJpaRepository.save(GradoMapper.toEntity(request)));
    }

    @Override
    public GradoDto actualizar(ActualizarGradoDto request) {
        GradoEntity entity = gradoJpaRepository.findById(request.id())
                .orElseThrow(() -> new IllegalArgumentException("Grado no encontrado"));
        if (request.nombre() != null) {
            entity.setNombre(request.nombre());
        }
        if (request.nivelEducativo() != null) {
            entity.setNivelEducativo(request.nivelEducativo());
        }
        if (request.orden() != null) {
            entity.setOrden(request.orden());
        }
        return GradoMapper.toDto(gradoJpaRepository.save(entity));
    }

    @Override
    public boolean existePorNombreYNivel(String nombre, String nivelEducativo) {
        return gradoJpaRepository.existsByNombreAndNivelEducativo(nombre, nivelEducativo);
    }

    @Override
    public GradoDto obtenerPorId(java.util.UUID id) {
        return gradoJpaRepository.findById(id)
                .map(GradoMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Grado no encontrado"));
    }
}
