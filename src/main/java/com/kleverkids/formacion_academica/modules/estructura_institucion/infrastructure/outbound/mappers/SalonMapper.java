package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.salon.CrearSalonDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Salon;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity.SalonEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SalonMapper {
    
    SalonMapper INSTANCE = Mappers.getMapper(SalonMapper.class);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usrCrea", ignore = true)
    @Mapping(target = "usrMod", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "activo", ignore = true)
    default SalonEntity toEntity(CrearSalonDto dto) {
        if (dto == null) {
            return null;
        }
        
        SalonEntity entity = new SalonEntity();
        entity.setCodigo(dto.codigo());
        entity.setNombre(dto.nombre());
        entity.setDescripcion(dto.descripcion());
        entity.setCapacidadMaxima(dto.capacidadMaxima());
        entity.setNumeroPiso(dto.numeroPiso());
        entity.setTieneProyector(dto.tieneProyector());
        entity.setTienePizarronBlanco(dto.tienePizarronBlanco());
        entity.setTieneAireAcondicionado(dto.tieneAireAcondicionado());
        entity.setNombreEdificio(dto.nombreEdificio());
        
        return entity;
    }
    
    Salon toDomainModel(SalonEntity entity);
    
    List<Salon> toDomainModelList(List<SalonEntity> entities);
    
    default void updateEntityFromDomain(Salon domain, @MappingTarget SalonEntity entity) {
        if (domain == null || entity == null) {
            return;
        }
        
        entity.setCodigo(domain.codigo());
        entity.setNombre(domain.nombre());
        entity.setDescripcion(domain.descripcion());
        entity.setCapacidadMaxima(domain.capacidadMaxima());
        entity.setNumeroPiso(domain.numeroPiso());
        entity.setTieneProyector(domain.tieneProyector());
        entity.setTienePizarronBlanco(domain.tienePizarronBlanco());
        entity.setTieneAireAcondicionado(domain.tieneAireAcondicionado());
        entity.setNombreEdificio(domain.nombreEdificio());
    }
}
