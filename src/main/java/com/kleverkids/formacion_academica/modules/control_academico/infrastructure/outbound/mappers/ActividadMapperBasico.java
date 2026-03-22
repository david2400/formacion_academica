package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.actividad.Actividad;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.actividad.valueobject.*;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.actividad.CrearActividadDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.actividad.ActualizarActividadDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.ActividadEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ActividadMapperBasico {

    // Mapeo de Entity a Domain Model
    public Actividad toDomainModel(ActividadEntity entity) {
        if (entity == null) {
            return null;
        }

        return Actividad.actividadBuilder()
            .titulo(entity.getTitulo())
            .descripcion(entity.getDescripcion())
            .instrucciones(entity.getInstrucciones())
            .tipo(mapTipoActividad(entity.getTipo()))
            .estado(mapEstadoActividad(entity.getEstado()))
            .cursoId(entity.getCursoId())
            .moduloId(entity.getModuloId())
            .orden(entity.getOrden())
            .actividadesDependientes(entity.getActividadesDependientes())
            .fechaInicio(entity.getFechaInicio())
            .fechaFin(entity.getFechaFin())
            .build();
    }

    public List<Actividad> toDomainModelList(List<ActividadEntity> entities) {
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
        
        // Configuración básica
        ActividadEntity.ConfiguracionActividadEntity config = new ActividadEntity.ConfiguracionActividadEntity();
        config.setObligatoria(dto.getObligatoria());
        config.setIntentosPermitidos(dto.getIntentosPermitidos());
        config.setMostrarRetroalimentacion(dto.getMostrarRetroalimentacion());
        config.setPermiteRevisar(dto.getPermiteRevisar());
        entity.setConfiguracion(config);
        
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
        
        // Configuración básica
        ActividadEntity.ConfiguracionActividadEntity config = new ActividadEntity.ConfiguracionActividadEntity();
        config.setObligatoria(dto.getObligatoria());
        config.setIntentosPermitidos(dto.getIntentosPermitidos());
        config.setMostrarRetroalimentacion(dto.getMostrarRetroalimentacion());
        config.setPermiteRevisar(dto.getPermiteRevisar());
        entity.setConfiguracion(config);
        
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

        // Actualizar configuración
        if (entity.getConfiguracion() == null) {
            entity.setConfiguracion(new ActividadEntity.ConfiguracionActividadEntity());
        }
        
        ActividadEntity.ConfiguracionActividadEntity config = entity.getConfiguracion();
        if (dto.getObligatoria() != null) {
            config.setObligatoria(dto.getObligatoria());
        }
        if (dto.getIntentosPermitidos() != null) {
            config.setIntentosPermitidos(dto.getIntentosPermitidos());
        }
        if (dto.getMostrarRetroalimentacion() != null) {
            config.setMostrarRetroalimentacion(dto.getMostrarRetroalimentacion());
        }
        if (dto.getPermiteRevisar() != null) {
            config.setPermiteRevisar(dto.getPermiteRevisar());
        }

        // Actualizar timestamp
        // entity.setUpdatedAt(LocalDateTime.now()); // Comentado porque no existe este campo en ActividadEntity
        
        return entity;
    }

    // Métodos de mapeo para enums
    private TipoActividad mapTipoActividad(ActividadEntity.TipoActividadEntity tipo) {
        if (tipo == null) {
            return TipoActividad.LECTURA; // valor por defecto
        }
        return TipoActividad.valueOf(tipo.name());
    }

    private ActividadEntity.TipoActividadEntity mapTipoActividadEntity(String tipo) {
        if (tipo == null) {
            return ActividadEntity.TipoActividadEntity.LECTURA; // valor por defecto
        }
        try {
            return ActividadEntity.TipoActividadEntity.valueOf(tipo.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ActividadEntity.TipoActividadEntity.LECTURA; // valor por defecto
        }
    }

    private EstadoActividad mapEstadoActividad(ActividadEntity.EstadoActividadEntity estado) {
        if (estado == null) {
            return EstadoActividad.BORRADOR; // valor por defecto
        }
        return EstadoActividad.valueOf(estado.name());
    }

    private ActividadEntity.EstadoActividadEntity mapEstadoActividadEntity(String estado) {
        if (estado == null) {
            return ActividadEntity.EstadoActividadEntity.BORRADOR; // valor por defecto
        }
        try {
            return ActividadEntity.EstadoActividadEntity.valueOf(estado.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ActividadEntity.EstadoActividadEntity.BORRADOR; // valor por defecto
        }
    }
}
