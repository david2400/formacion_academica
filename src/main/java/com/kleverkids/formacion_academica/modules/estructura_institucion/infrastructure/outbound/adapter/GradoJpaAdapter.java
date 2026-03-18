package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.grado.GradoRepositoryPort;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grado.ActualizarGradoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grado.CrearGradoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Grado;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.mappers.GradoMapper;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity.GradoEntity;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.repository.GradoJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

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
    public Grado guardar(CrearGradoDto request) {
        return gradoMapper.toDomainModel(gradoJpaRepository.save(gradoMapper.toEntity(request)));
    }

    @Override
    public Grado actualizar(ActualizarGradoDto request) {
        GradoEntity entity = gradoJpaRepository.findById(request.getId())
                .orElseThrow(() -> new IllegalArgumentException("Grado no encontrado"));
        gradoMapper.updateEntityFromDto(request, entity);
        return gradoMapper.toDomainModel(gradoJpaRepository.save(entity));
    }

    @Override
    public boolean existePorNombreYNivel(String nombre, String nivelEducativo) {
        return gradoJpaRepository.existsByNombreAndNivelEducativo(nombre, nivelEducativo);
    }

    @Override
    public Grado obtenerPorId(Long id) {
        return gradoJpaRepository.findById(id)
                .map(gradoMapper::toDomainModel)
                .orElseThrow(() -> new IllegalArgumentException("Grado no encontrado"));
    }

    @Override
    public List<Grado> listar() {
        return gradoJpaRepository.findAll().stream().map(gradoMapper::toDomainModel).toList();
    }

    @Override
    public void eliminar(Long id) {
        gradoJpaRepository.deleteById(id);
    }
}
