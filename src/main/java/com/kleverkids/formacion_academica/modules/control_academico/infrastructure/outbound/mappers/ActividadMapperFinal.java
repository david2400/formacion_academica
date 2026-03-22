package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.actividad.ActividadSimple;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.actividad.CrearActividadDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.actividad.ActualizarActividadDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.ActividadEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ActividadMapperFinal {

    // Mapeo de Entity a Domain Model Simple
    public ActividadSimple toDomainModel(ActividadEntity entity) {
        if (entity == null) {
            return null;
        }

        return ActividadSimple.builder()
            .id(entity.getId())
            .titulo(entity.getTitulo())
            .descripcion(entity.getDescripcion())
            .instrucciones(entity.getInstrucciones())
            .tipo(entity.getTipo() != null ? entity.getTipo().name() : "LECTURA")
            .estado(entity.getEstado() != null ? entity.getEstado().name() : "BORRADOR")
            .cursoId(entity.getCursoId())
            .moduloId(entity.getModuloId())
            .orden(entity.getOrden())
            .fechaInicio(entity.getFechaInicio())
            .fechaFin(entity.getFechaFin())
            .build();
    }

    public List<ActividadSimple> toDomainModelList(List<ActividadEntity> entities) {
        return entities.stream()
            .map(this::toDomainModel)
            .collect(Collectors.toList());
    }

    // Mapeo de DTO a Entity
    public ActividadEntity toEntity(CrearActividadDto dto) {
        if (dto == null) {
            return null;
        }

        ActividadEntity entity = new ActividadEntity();
        entity.setTitulo(dto.getTitulo());
        entity.setDescripcion(dto.getDescripcion());
        entity.setInstrucciones(dto.getInstrucciones());
        entity.setTipo(mapTipoActividadEntity(dto.getTipo()));
        entity.setEstado(ActividadEntity.EstadoActividadEntity.BORRADOR);
        entity.setCursoId(dto.getCursoId());
        entity.setModuloId(dto.getModuloId());
        entity.setOrden(dto.getOrden());
        entity.setFechaInicio(dto.getFechaInicio());
        entity.setFechaFin(dto.getFechaFin());
        
        return entity;
    }

    public ActividadEntity toEntity(ActualizarActividadDto dto) {
        if (dto == null) {
            return null;
        }

        ActividadEntity entity = new ActividadEntity();
        entity.setId(dto.getId());
        entity.setTitulo(dto.getTitulo());
        entity.setDescripcion(dto.getDescripcion());
        entity.setInstrucciones(dto.getInstrucciones());
        entity.setTipo(mapTipoActividadEntity(dto.getTipo()));
        
        if (dto.getEstado() != null) {
            entity.setEstado(mapEstadoActividadEntity(dto.getEstado()));
        }
        
        entity.setCursoId(dto.getCursoId());
        entity.setModuloId(dto.getModuloId());
        entity.setOrden(dto.getOrden());
        entity.setFechaInicio(dto.getFechaInicio());
        entity.setFechaFin(dto.getFechaFin());
        
        return entity;
    }

    // Método de actualización
    public ActividadEntity updateEntityFromDto(ActualizarActividadDto dto, ActividadEntity entity) {
        if (dto == null || entity == null) {
            return entity;
        }

        // Actualizar campos básicos
        if (dto.getTitulo() != null) {
            entity.setTitulo(dto.getTitulo());
        }
        if (dto.getDescripcion() != null) {
            entity.setDescripcion(dto.getDescripcion());
        }
        if (dto.getInstrucciones() != null) {
            entity.setInstrucciones(dto.getInstrucciones());
        }
        if (dto.getTipo() != null) {
            entity.setTipo(mapTipoActividadEntity(dto.getTipo()));
        }
        if (dto.getEstado() != null) {
            entity.setEstado(mapEstadoActividadEntity(dto.getEstado()));
        }
        if (dto.getCursoId() != null) {
            entity.setCursoId(dto.getCursoId());
        }
        if (dto.getModuloId() != null) {
            entity.setModuloId(dto.getModuloId());
        }
        if (dto.getOrden() != null) {
            entity.setOrden(dto.getOrden());
        }
        if (dto.getFechaInicio() != null) {
            entity.setFechaInicio(dto.getFechaInicio());
        }
        if (dto.getFechaFin() != null) {
            entity.setFechaFin(dto.getFechaFin());
        }
        
        return entity;
    }

    // Métodos de mapeo para enums
    private ActividadEntity.TipoActividadEntity mapTipoActividadEntity(String tipo) {
        if (tipo == null) {
            return ActividadEntity.TipoActividadEntity.LECTURA;
        }
        try {
            return ActividadEntity.TipoActividadEntity.valueOf(tipo.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ActividadEntity.TipoActividadEntity.LECTURA;
        }
    }

    private ActividadEntity.EstadoActividadEntity mapEstadoActividadEntity(String estado) {
        if (estado == null) {
            return ActividadEntity.EstadoActividadEntity.BORRADOR;
        }
        try {
            return ActividadEntity.EstadoActividadEntity.valueOf(estado.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ActividadEntity.EstadoActividadEntity.BORRADOR;
        }
    }
}
