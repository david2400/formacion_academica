package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.tipo_clase.TipoClaseRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tipo_clase.ActualizarTipoClaseDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tipo_clase.CrearTipoClaseDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.TipoClase;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers.TipoClaseMapper;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.TipoClaseEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository.TipoClaseJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class TipoClaseJpaAdapter implements TipoClaseRepositoryPort {

    private final TipoClaseJpaRepository tipoClaseJpaRepository;
    private final TipoClaseMapper tipoClaseMapper;

    @Override
    public TipoClase guardar(CrearTipoClaseDto tipo) {
        TipoClaseEntity entity = tipoClaseMapper.toEntity(tipo);
        entity.setNombre(entity.getNombre().trim());

        return tipoClaseMapper.toDomainModel(tipoClaseJpaRepository.save(entity));
    }

    @Override
    public Optional<TipoClase> obtenerPorId(Long id) {
        return tipoClaseJpaRepository.findById(id).map(tipoClaseMapper::toDomainModel);
    }

    @Override
    public List<TipoClase> listar() {
        return tipoClaseMapper.toDomainModelList(tipoClaseJpaRepository.findAll());
    }

    @Override
    public TipoClase actualizar(Long id, ActualizarTipoClaseDto tipo) {
        TipoClaseEntity existing = tipoClaseJpaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tipo de clase no encontrado"));

        existing.setNombre(tipo.getNombre().trim());
        existing.setDescripcion(tipo.getDescripcion());
        existing.setColor(tipo.getColor());

        return tipoClaseMapper.toDomainModel(tipoClaseJpaRepository.save(existing));
    }

    @Override
    public void eliminar(Long id) {
        TipoClaseEntity existing = tipoClaseJpaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tipo de clase no encontrado"));

        // Borrado lógico: AuditInfo aplica @SoftDelete sobre la columna eliminado.
        tipoClaseJpaRepository.delete(existing);
    }

    @Override
    public boolean existePorNombre(String nombre) {
        return nombre != null && tipoClaseJpaRepository.existsByNombreIgnoreCase(nombre.trim());
    }
}
