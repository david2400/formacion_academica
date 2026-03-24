package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.actividad.Actividad;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.actividad.valueobject.*;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.actividad.CrearActividadDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.actividad.ActualizarActividadDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.ActividadEntity;

import org.mapstruct.*;

import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring")
public interface ActividadMapper {

    ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    // =========================
    // ENTITY → DOMAIN
    // =========================

    @Mapping(target = "tipo", expression = "java(mapTipoActividad(entity.getTipo()))")
    @Mapping(target = "estado", expression = "java(mapEstadoActividad(entity.getEstado()))")
    Actividad toDomainModel(ActividadEntity entity);

    List<Actividad> toDomainModelList(List<ActividadEntity> entities);

    // =========================
    // DTO → ENTITY (CREAR)
    // =========================

    @Mapping(target = "tipo", expression = "java(mapTipoActividadEntity(dto.getTipo()))")
    @Mapping(target = "estado", expression = "java(ActividadEntity.EstadoActividadEntity.BORRADOR)")
    @Mapping(target = "configuracion", expression = "java(mapConfiguracion(dto))")
    ActividadEntity toEntity(CrearActividadDto dto);

    // =========================
    // DTO → ENTITY (ACTUALIZAR)
    // =========================

    @Mapping(target = "tipo", expression = "java(mapTipoActividadEntity(dto.getTipo()))")
    @Mapping(target = "estado", expression = "java(dto.getEstado() != null ? mapEstadoActividadEntity(dto.getEstado()) : null)")
    @Mapping(target = "configuracion", expression = "java(mapConfiguracion(dto))")
    ActividadEntity toEntity(ActualizarActividadDto dto);

    // =========================
    // UPDATE PARCIAL (PRO)
    // =========================

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "tipo", expression = "java(dto.getTipo() != null ? mapTipoActividadEntity(dto.getTipo()) : entity.getTipo())")
    @Mapping(target = "estado", expression = "java(dto.getEstado() != null ? mapEstadoActividadEntity(dto.getEstado()) : entity.getEstado())")
    void updateEntityFromDto(ActualizarActividadDto dto, @MappingTarget ActividadEntity entity);

    // =========================
    // CONFIGURACIÓN
    // =========================

    default ActividadEntity.ConfiguracionActividadEntity mapConfiguracion(CrearActividadDto dto) {
        ActividadEntity.ConfiguracionActividadEntity config = new ActividadEntity.ConfiguracionActividadEntity();
        config.setObligatoria(dto.getObligatoria());
        config.setIntentosPermitidos(dto.getIntentosPermitidos());
        config.setMostrarRetroalimentacion(dto.getMostrarRetroalimentacion());
        config.setPermiteRevisar(dto.getPermiteRevisar());
        return config;
    }

    default ActividadEntity.ConfiguracionActividadEntity mapConfiguracion(ActualizarActividadDto dto) {
        ActividadEntity.ConfiguracionActividadEntity config = new ActividadEntity.ConfiguracionActividadEntity();
        config.setObligatoria(dto.getObligatoria());
        config.setIntentosPermitidos(dto.getIntentosPermitidos());
        config.setMostrarRetroalimentacion(dto.getMostrarRetroalimentacion());
        config.setPermiteRevisar(dto.getPermiteRevisar());
        return config;
    }

    // =========================
    // MAPEO GENERICO (SOLUCIÓN ERROR)
    // =========================

    default Map<String, Object> map(Object value) {
        if (value == null) {
            return null;
        }
        return OBJECT_MAPPER.convertValue(value, Map.class);
    }

    // =========================
    // ENUMS
    // =========================

    default TipoActividad mapTipoActividad(ActividadEntity.TipoActividadEntity tipo) {
        if (tipo == null) {
            return TipoActividad.LECTURA;
        }
        return TipoActividad.valueOf(tipo.name());
    }

    default ActividadEntity.TipoActividadEntity mapTipoActividadEntity(String tipo) {
        if (tipo == null) {
            return ActividadEntity.TipoActividadEntity.LECTURA;
        }
        try {
            return ActividadEntity.TipoActividadEntity.valueOf(tipo.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ActividadEntity.TipoActividadEntity.LECTURA;
        }
    }

    default EstadoActividad mapEstadoActividad(ActividadEntity.EstadoActividadEntity estado) {
        if (estado == null) {
            return EstadoActividad.BORRADOR;
        }
        return EstadoActividad.valueOf(estado.name());
    }

    default ActividadEntity.EstadoActividadEntity mapEstadoActividadEntity(String estado) {
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