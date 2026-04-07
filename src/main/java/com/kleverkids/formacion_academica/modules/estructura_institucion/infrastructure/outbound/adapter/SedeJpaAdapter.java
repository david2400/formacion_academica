package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.sede.SedeRepositoryPort;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.sedes.ActualizarSedeDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.sedes.CrearSedeDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Sede;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.mappers.SedeMapper;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity.SedeEntity;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.repository.SedeJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SedeJpaAdapter implements SedeRepositoryPort {

    private final SedeJpaRepository sedeJpaRepository;
    private final SedeMapper sedeMapper;

    public SedeJpaAdapter(SedeJpaRepository sedeJpaRepository, SedeMapper sedeMapper) {
        this.sedeJpaRepository = sedeJpaRepository;
        this.sedeMapper = sedeMapper;
    }

    @Override
    public Sede registrar(CrearSedeDto request) {
        SedeEntity entity = sedeMapper.toEntity(request);
        SedeEntity savedEntity = sedeJpaRepository.save(entity);
        return sedeMapper.toDomainModel(savedEntity);
    }

    @Override
    public Optional<Sede> obtenerPorId(Long id) {
        return sedeJpaRepository.findById(id)
                .map(sedeMapper::toDomainModel);
    }

//    @Override
//    public Optional<Sede> obtenerPorCodigo(String codigo) {
//        return sedeJpaRepository.findByCodigo(codigo)
//                .map(sedeMapper::toDomainModel);
//    }

    @Override
    public List<Sede> listarTodas() {
        List<SedeEntity> entities = sedeJpaRepository.findAll();
        return sedeMapper.toDomainModelList(entities);
    }

    @Override
    public List<Sede> listarActivas() {
        List<SedeEntity> entities = sedeJpaRepository.findByEliminadoFalse();
        return sedeMapper.toDomainModelList(entities);
    }

//    @Override
//    public List<Sede> listarPorCiudad(String ciudad) {
//        List<SedeEntity> entities = sedeJpaRepository.findActivasByCiudad(ciudad);
//        return sedeMapper.toDomainModelList(entities);
//    }

    @Override
    public Optional<Sede> actualizar(Long id, ActualizarSedeDto request) {
        return sedeJpaRepository.findById(id)
                .map(entity -> {
                    sedeMapper.updateEntityFromDto(request, entity);
                    SedeEntity updatedEntity = sedeJpaRepository.save(entity);
                    return sedeMapper.toDomainModel(updatedEntity);
                });
    }

    @Override
    public boolean eliminar(Long id) {
        if (sedeJpaRepository.existsById(id)) {
            sedeJpaRepository.deleteById(id);
            return true;
        }
        return false;
    }

//    @Override
//    public boolean existePorCodigo(String codigo) {
//        return sedeJpaRepository.existsByCodigo(codigo);
//    }
}
