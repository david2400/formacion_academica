package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.nivel.NivelEducativoRepositoryPort;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.nivel.ActualizarNivelEducativoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.nivel.CrearNivelEducativoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.nivel.NivelEducativoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.nivel.NivelEducativoEstadisticasDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.mappers.NivelEducativoMapper;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity.NivelEducativoEntity;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.repository.NivelEducativoJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class NivelEducativoJpaAdapter implements NivelEducativoRepositoryPort {

    private final NivelEducativoJpaRepository repository;
    private final NivelEducativoMapper mapper;

    @Override
    public NivelEducativoDto crear(CrearNivelEducativoDto dto) {
        if (existePorCodigo(dto.codigo())) {
            throw new IllegalArgumentException("Ya existe un nivel educativo con el código: " + dto.codigo());
        }
        
        NivelEducativoEntity entity = mapper.toEntity(dto);
        
        if (dto.nivelSuperiorId() != null) {
            repository.findById(dto.nivelSuperiorId())
                    .ifPresent(entity::setNivelSuperior);
        }
        
        NivelEducativoEntity saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    public Optional<NivelEducativoDto> buscarPorId(Long id) {
        return repository.findById(id)
                .map(mapper::toDto);
    }

    @Override
    public Optional<NivelEducativoDto> buscarPorCodigo(String codigo) {
        return repository.findByCodigo(codigo)
                .map(mapper::toDto);
    }

    @Override
    public List<NivelEducativoDto> listarTodos() {
        return mapper.toDtoList(repository.findAll());
    }

    @Override
    public List<NivelEducativoDto> listarPorCategoria(String categoria) {
        return mapper.toDtoList(repository.findByCategoria(categoria));
    }

    @Override
    public List<NivelEducativoDto> listarPorNivelSuperior(Long nivelSuperiorId) {
        return mapper.toDtoList(repository.findByNivelSuperiorId(nivelSuperiorId));
    }

    @Override
    public Optional<NivelEducativoDto> actualizar(Long id, ActualizarNivelEducativoDto dto) {
        return repository.findById(id)
                .map(entity -> {
                    mapper.updateEntity(entity, dto);
                    
                    if (dto.nivelSuperiorId() != null) {
                        repository.findById(dto.nivelSuperiorId())
                                .ifPresent(entity::setNivelSuperior);
                    } else {
                        entity.setNivelSuperior(null);
                    }
                    
                    NivelEducativoEntity updated = repository.save(entity);
                    return mapper.toDto(updated);
                });
    }

    @Override
    public boolean eliminar(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean existePorCodigo(String codigo) {
        return repository.existsByCodigo(codigo);
    }

    @Override
    public List<NivelEducativoDto> listarActivos() {
        return mapper.toDtoList(repository.findByActivoTrue());
    }

    @Override
    public NivelEducativoEstadisticasDto obtenerEstadisticas() {
        List<NivelEducativoDto> todos = listarTodos();
        List<NivelEducativoDto> activos = listarActivos();
        
        long totalPreescolar = todos.stream().filter(n -> "PREESCOLAR".equals(n.categoria())).count();
        long totalBasica = todos.stream().filter(n -> "BASICA".equals(n.categoria())).count();
        long totalMedia = todos.stream().filter(n -> "MEDIA".equals(n.categoria())).count();
        long totalSuperior = todos.stream().filter(n -> "SUPERIOR".equals(n.categoria())).count();
        
        List<NivelEducativoEstadisticasDto.NivelEducativoResumenDto> porCategoria = List.of(
            new NivelEducativoEstadisticasDto.NivelEducativoResumenDto("PREESCOLAR", totalPreescolar),
            new NivelEducativoEstadisticasDto.NivelEducativoResumenDto("BASICA", totalBasica),
            new NivelEducativoEstadisticasDto.NivelEducativoResumenDto("MEDIA", totalMedia),
            new NivelEducativoEstadisticasDto.NivelEducativoResumenDto("SUPERIOR", totalSuperior)
        );
        
        return new NivelEducativoEstadisticasDto(
            (long) todos.size(),
            (long) activos.size(),
            (long) (todos.size() - activos.size()),
            totalPreescolar,
            totalBasica,
            totalMedia,
            totalSuperior,
            porCategoria,
            List.of()
        );
    }

    @Override
    public List<NivelEducativoDto> listarPorCategoriaActivos(String categoria) {
        return mapper.toDtoList(repository.findByCategoriaAndActivoTrueOrderByOrdenAsc(categoria));
    }

    @Override
    public List<NivelEducativoDto> listarNivelesPrincipales() {
        return mapper.toDtoList(repository.findNivelesPrincipalesActivos());
    }
}
