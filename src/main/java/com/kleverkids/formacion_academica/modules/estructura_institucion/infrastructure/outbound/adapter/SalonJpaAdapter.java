package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.salon.SalonRepositoryPort;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.salon.ActualizarSalonDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.salon.CrearSalonDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Salon;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.mappers.SalonMapper;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity.SalonEntity;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.repository.SalonJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SalonJpaAdapter implements SalonRepositoryPort {

    private final SalonJpaRepository salonJpaRepository;
    private final SalonMapper salonMapper;

    public SalonJpaAdapter(SalonJpaRepository salonJpaRepository, SalonMapper salonMapper) {
        this.salonJpaRepository = salonJpaRepository;
        this.salonMapper = salonMapper;
    }

    @Override
    public Salon guardar(CrearSalonDto request) {
        SalonEntity entity = salonMapper.toEntity(request);
        return salonMapper.toDomainModel(salonJpaRepository.save(entity));
    }

    @Override
    public Salon actualizar(ActualizarSalonDto request) {
        SalonEntity entity = salonJpaRepository.findById(request.getId())
                .orElseThrow(() -> new IllegalArgumentException("Salón no encontrado"));

        return salonMapper.toDomainModel(salonJpaRepository.save(entity));
    }

    @Override
    public List<Salon> listar() {
        return salonMapper.toDomainModelList(salonJpaRepository.findAllByOrderByNombreAsc());
    }

    @Override
    public Salon obtenerPorId(Long salonId) {
        return salonMapper.toDomainModel(
                salonJpaRepository.findById(salonId)
                        .orElseThrow(() -> new IllegalArgumentException("Salón no encontrado"))
        );
    }

    @Override
    public void eliminar(Long salonId) {
        salonJpaRepository.deleteById(salonId);
    }

    @Override
    public boolean existePorId(Long salonId) {
        return salonJpaRepository.existsById(salonId);
    }

    @Override
    public boolean existePorCodigo(String codigo) {
        return salonJpaRepository.existsByCodigo(codigo);
    }
}
