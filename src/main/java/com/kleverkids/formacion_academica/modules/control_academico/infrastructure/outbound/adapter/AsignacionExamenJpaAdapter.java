package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.asignacion_examen.AsignacionExamenRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.asignacion_examen.AsignacionExamenEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository.AsignacionExamenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AsignacionExamenJpaAdapter implements AsignacionExamenRepositoryPort {
    
    private final AsignacionExamenRepository repository;
    
    @Override
    public AsignacionExamenEntity save(AsignacionExamenEntity entity) {
        return repository.save(entity);
    }
    
    @Override
    public Optional<AsignacionExamenEntity> findById(Long id) {
        return repository.findById(id);
    }
    
    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
    
    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }
    
    @Override
    public Page<AsignacionExamenEntity> findAll(Specification<AsignacionExamenEntity> spec, Pageable pageable) {
        return repository.findAll(spec, pageable);
    }
    
    @Override
    public boolean existsByExamenIdAndClaseIdAndEstado(Long examenId, Long claseId, String estado) {
        return repository.existsByExamenIdAndClaseIdAndEstado(examenId, claseId, estado);
    }
}
