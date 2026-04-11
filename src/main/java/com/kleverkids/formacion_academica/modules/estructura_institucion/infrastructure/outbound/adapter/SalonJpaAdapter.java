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
        try {
            return salonMapper.toDomainModelList(salonJpaRepository.findAllByOrderByNombreAsc());
        } catch (Exception e) {
            // Log the error and return empty list if table doesn't exist
            System.err.println("Error accessing salones table: " + e.getMessage());
            return List.of(); // Return empty list to prevent application crash
        }
    }

    @Override
    public Salon obtenerPorId(Long salonId) {
        try {
            return salonMapper.toDomainModel(
                    salonJpaRepository.findById(salonId)
                            .orElseThrow(() -> new IllegalArgumentException("Salón no encontrado"))
            );
        } catch (Exception e) {
            // Log the error and rethrow if it's not found, or return null if table doesn't exist
            if (e.getMessage() != null && e.getMessage().contains("doesn't exist")) {
                System.err.println("Error accessing salones table: " + e.getMessage());
                return null;
            }
            throw e; // Rethrow other exceptions
        }
    }

    @Override
    public void eliminar(Long salonId) {
        salonJpaRepository.deleteById(salonId);
    }

    @Override
    public boolean existePorId(Long salonId) {
        try {
            return salonJpaRepository.existsById(salonId);
        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().contains("doesn't exist")) {
                System.err.println("Error accessing salones table: " + e.getMessage());
                return false;
            }
            throw e; // Rethrow other exceptions
        }
    }

    @Override
    public boolean existePorCodigo(String codigo) {
        try {
            return salonJpaRepository.existsByCodigo(codigo);
        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().contains("doesn't exist")) {
                System.err.println("Error accessing salones table: " + e.getMessage());
                return false;
            }
            throw e; // Rethrow other exceptions
        }
    }
}
