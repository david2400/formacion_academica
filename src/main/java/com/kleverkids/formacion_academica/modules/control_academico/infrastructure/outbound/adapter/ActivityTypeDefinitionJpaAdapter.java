package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.activity_type.ActivityTypeDefinitionRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity_type.ActualizarActivityTypeDefinitionDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity_type.CrearActivityTypeDefinitionDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_type.ActivityTypeDefinition;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers.ActivityTypeDefinitionMapper;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.activity.ActivityTypeDefinitionEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository.ActivityTypeDefinitionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class ActivityTypeDefinitionJpaAdapter implements ActivityTypeDefinitionRepositoryPort {

    private final ActivityTypeDefinitionJpaRepository activityTypeDefinitionJpaRepository;
    private final ActivityTypeDefinitionMapper activityTypeDefinitionMapper;

    @Override
    public ActivityTypeDefinition guardar(CrearActivityTypeDefinitionDto dto) {
        ActivityTypeDefinitionEntity entity = activityTypeDefinitionMapper.toEntity(dto);
        if (dto.getActivo() != null) {
            entity.setActivo(dto.getActivo());
        }
        return activityTypeDefinitionMapper.toDomainModel(activityTypeDefinitionJpaRepository.save(entity));
    }

    @Override
    public ActivityTypeDefinition actualizar(ActualizarActivityTypeDefinitionDto dto) {
        ActivityTypeDefinitionEntity existing = buscarEntidad(dto.getId());
        existing.setNombre(dto.getNombre());
        existing.setDescripcion(dto.getDescripcion());
        existing.setIcono(dto.getIcono());
        if (dto.getCategoria() != null) {
            existing.setCategoria(dto.getCategoria());
        }
        if (dto.getConfiguracionSchema() != null) {
            existing.setConfiguracionSchema(dto.getConfiguracionSchema());
        }
        if (dto.getCapacidades() != null) {
            existing.setCapacidades(dto.getCapacidades());
        }
        if (dto.getActivo() != null) {
            existing.setActivo(dto.getActivo());
        }
        return activityTypeDefinitionMapper.toDomainModel(activityTypeDefinitionJpaRepository.save(existing));
    }

    @Override
    public Optional<ActivityTypeDefinition> obtenerPorId(Long id) {
        return activityTypeDefinitionJpaRepository.findById(id).map(activityTypeDefinitionMapper::toDomainModel);
    }

    @Override
    public Optional<ActivityTypeDefinition> obtenerPorTipo(String type) {
        return activityTypeDefinitionJpaRepository.findByType(type).map(activityTypeDefinitionMapper::toDomainModel);
    }

    @Override
    public List<ActivityTypeDefinition> listarActivos() {
        return activityTypeDefinitionMapper.toDomainModelList(activityTypeDefinitionJpaRepository.findByActivoTrue());
    }

    @Override
    public List<ActivityTypeDefinition> listarTodos() {
        return activityTypeDefinitionMapper.toDomainModelList(activityTypeDefinitionJpaRepository.findAll());
    }

    @Override
    public boolean existePorTipo(String type) {
        return activityTypeDefinitionJpaRepository.existsByType(type);
    }

    private ActivityTypeDefinitionEntity buscarEntidad(Long id) {
        return activityTypeDefinitionJpaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tipo de actividad no encontrado"));
    }
}
