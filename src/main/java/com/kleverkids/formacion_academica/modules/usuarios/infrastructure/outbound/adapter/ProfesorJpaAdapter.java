package com.kleverkids.formacion_academica.modules.usuarios.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.usuarios.application.output.profesor.ProfesorRepositoryPort;
import com.kleverkids.formacion_academica.modules.usuarios.domain.model.Profesor;
import com.kleverkids.formacion_academica.modules.usuarios.infrastructure.outbound.mappers.ProfesorMapper;
import com.kleverkids.formacion_academica.modules.usuarios.infrastructure.outbound.persistence.mysql.entity.ProfesorEntity;
import com.kleverkids.formacion_academica.modules.usuarios.infrastructure.outbound.persistence.mysql.repository.ProfesorJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProfesorJpaAdapter implements ProfesorRepositoryPort {

    private final ProfesorJpaRepository repository;
    private final ProfesorMapper mapper;

    @Override
    public Profesor guardar(Profesor profesor) {
        ProfesorEntity entity = profesor.getId() == null
                ? mapper.toEntity(profesor)
                : repository.findById(profesor.getId()).map(e -> {
                    // Solo lo editable: usuario y empresa no cambian.
                    e.setActivo(profesor.isActivo());
                    e.setFechaVinculacion(profesor.getFechaVinculacion());
                    e.setObservaciones(profesor.getObservaciones());
                    return e;
                }).orElseThrow();
        return mapper.toDomain(repository.saveAndFlush(entity));
    }

    @Override
    public Optional<Profesor> buscarPorId(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public boolean existe(Long usuarioId, Long empresaId) {
        return repository.existsByUsuarioIdAndEmpresaId(usuarioId, empresaId);
    }

    @Override
    public Optional<Long> restaurarEliminado(Profesor datos) {
        return repository.idEliminado(datos.getUsuarioId(), datos.getEmpresaId()).map(id -> {
            repository.restaurar(id, datos.getFechaVinculacion(), datos.getObservaciones());
            return id;
        });
    }

    @Override
    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
