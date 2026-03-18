package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.aula.AulaRepositoryPort;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.aula.ActualizarAulaDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Aula;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.aula.CrearAulaDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.mappers.AulaMapper;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity.AulaEntity;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.repository.AulaJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AulaJpaAdapter implements AulaRepositoryPort {

    private final AulaJpaRepository aulaJpaRepository;
    private final AulaMapper aulaMapper;

    public AulaJpaAdapter(AulaJpaRepository aulaJpaRepository,
                          AulaMapper aulaMapper) {
        this.aulaJpaRepository = aulaJpaRepository;
        this.aulaMapper = aulaMapper;
    }

    @Override
    public Aula guardar(CrearAulaDto request) {
        AulaEntity entity = aulaMapper.toEntity(request);
        return aulaMapper.toDomainModel(aulaJpaRepository.save(entity));
    }

    @Override
    public Aula actualizar(ActualizarAulaDto request) {
        AulaEntity entity = aulaJpaRepository.findById(request.getId())
                .orElseThrow(() -> new IllegalArgumentException("Aula no encontrada"));
        aulaMapper.updateEntityFromDomain(request, entity);
        return aulaMapper.toDomainModel(aulaJpaRepository.save(entity));
    }

    @Override
    public boolean existePorNombre(String nombre) {
        return nombre != null && aulaJpaRepository.existsByNombreIgnoreCase(nombre);
    }

    @Override
    public boolean existePorId(Long id) {
        return aulaJpaRepository.existsById(id);
    }

    @Override
    public Aula obtenerPorId(Long id) {
        return aulaJpaRepository.findById(id)
                .map(aulaMapper::toDomainModel)
                .orElseThrow(() -> new IllegalArgumentException("Aula no encontrada"));
    }

    @Override
    public List<Aula> listar() {
        return aulaMapper.toDomainModelList(aulaJpaRepository.findAll());
    }

    @Override
    public void eliminar(Long id) {
        aulaJpaRepository.deleteById(id);
    }
}
