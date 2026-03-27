package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.nivel.NivelEducativoRepositoryPort;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.nivel_academico.ActualizarNivelEducativoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.nivel_academico.CrearNivelEducativoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.NivelEducativo;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.nivel_academico.NivelEducativoEstadisticasDto;
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
    public NivelEducativo crear(CrearNivelEducativoDto dto) {
        if (existePorCodigo(dto.codigo())) {
            throw new IllegalArgumentException("Ya existe un nivel educativo con el código: " + dto.codigo());
        }
        
        NivelEducativoEntity entity = mapper.toEntity(dto);
        
        if (dto.nivelSuperiorId() != null) {
            repository.findById(dto.nivelSuperiorId())
                    .ifPresent(entity::setNivelSuperior);
        }
        
        NivelEducativoEntity saved = repository.save(entity);
        return mapper.toDomainModel(saved);
    }

    @Override
    public Optional<NivelEducativo> buscarPorId(Long id) {
        return repository.findById(id)
                .map(mapper::toDomainModel);
    }

    @Override
    public Optional<NivelEducativo> buscarPorCodigo(String codigo) {
        return repository.findByCodigo(codigo)
                .map(mapper::toDomainModel);
    }

    @Override
    public List<NivelEducativo> listarTodos() {
        return mapper.toDomainModelList(repository.findAll());
    }

//    @Override
//    public List<NivelEducativo> listarPorCategoria(String categoria) {
//        return mapper.toDomainModelList(repository.findByCategoria(categoria));
//    }

    @Override
    public List<NivelEducativo> listarPorNivelSuperior(Long nivelSuperiorId) {
        return mapper.toDomainModelList(repository.findByNivelSuperiorId(nivelSuperiorId));
    }

    @Override
    public Optional<NivelEducativo> actualizar(Long id, ActualizarNivelEducativoDto dto) {
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
                    return mapper.toDomainModel(updated);
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
    public List<NivelEducativo> listarActivos() {
        return mapper.toDomainModelList(repository.findByActivoTrue());
    }

//    @Override
//    public NivelEducativoEstadisticasDto obtenerEstadisticas() {
//        List<NivelEducativo> todos = listarTodos();
//        List<NivelEducativo> activos = listarActivos();
//        List<NivelEducativo> inactivos = todos.stream()
//                .filter(n -> !n.activo())
//                .toList();
//
//        long totalPreescolar = todos.stream().filter(n -> "PREESCOLAR".equals(n.categoria())).count();
//        long totalBasica = todos.stream().filter(n -> "BASICA".equals(n.categoria())).count();
//        long totalMedia = todos.stream().filter(n -> "MEDIA".equals(n.categoria())).count();
//        long totalSuperior = todos.stream().filter(n -> "SUPERIOR".equals(n.categoria())).count();
//
//        List<NivelEducativoEstadisticasDto.NivelEducativoResumenDto> porCategoria = List.of(
//            new NivelEducativoEstadisticasDto.NivelEducativoResumenDto("PREESCOLAR", totalPreescolar),
//            new NivelEducativoEstadisticasDto.NivelEducativoResumenDto("BASICA", totalBasica),
//            new NivelEducativoEstadisticasDto.NivelEducativoResumenDto("MEDIA", totalMedia),
//            new NivelEducativoEstadisticasDto.NivelEducativoResumenDto("SUPERIOR", totalSuperior)
//        );
//
//        return new NivelEducativoEstadisticasDto(
//            (long) todos.size(),
//            (long) activos.size(),
//            (long) inactivos.size(),
//            totalPreescolar,
//            totalBasica,
//            totalMedia,
//            totalSuperior,
//            porCategoria,
//            List.of()
//        );
//    }

//    @Override
//    public List<NivelEducativo> listarPorCategoriaActivos(String categoria) {
//        return mapper.toDomainModelList(repository.findByCategoriaAndActivoTrueOrderByOrdenAsc(categoria));
//    }

    @Override
    public List<NivelEducativo> listarNivelesPrincipales() {
        return mapper.toDomainModelList(repository.findNivelesPrincipalesActivos());
    }
}
