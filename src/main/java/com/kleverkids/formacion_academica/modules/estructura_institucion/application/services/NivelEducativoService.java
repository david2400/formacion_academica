package com.kleverkids.formacion_academica.modules.estructura_institucion.application.services;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.nivel.NivelEducativoRepositoryPort;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.nivel_academico.ActualizarNivelEducativoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.nivel_academico.CrearNivelEducativoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.NivelEducativo;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.nivel_academico.NivelEducativoEstadisticasDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class NivelEducativoService {

    private final NivelEducativoRepositoryPort repository;

    @Transactional
    public NivelEducativo crear(CrearNivelEducativoDto dto) {
        validarCodigoUnico(dto.getCodigo());
        validarNivelSuperior(dto.getNivelSuperiorId());
        
        return repository.crear(dto);
    }

    @Transactional(readOnly = true)
    public Optional<NivelEducativo> buscarPorId(Long id) {
        return repository.buscarPorId(id);
    }

    @Transactional(readOnly = true)
    public Optional<NivelEducativo> buscarPorCodigo(String codigo) {
        return repository.buscarPorCodigo(codigo);
    }

    @Transactional(readOnly = true)
    public List<NivelEducativo> listarTodos() {
        return repository.listarTodos();
    }

//    @Transactional(readOnly = true)
//    public List<NivelEducativo> listarPorCategoria(String categoria) {
//        return repository.listarPorCategoria(categoria);
//    }

    @Transactional(readOnly = true)
    public List<NivelEducativo> listarPorNivelSuperior(Long nivelSuperiorId) {
        return repository.listarPorNivelSuperior(nivelSuperiorId);
    }

    @Transactional(readOnly = true)
    public List<NivelEducativo> listarActivos() {
        return repository.listarActivos();
    }

//    @Transactional(readOnly = true)
//    public NivelEducativoEstadisticasDto obtenerEstadisticas() {
//        return repository.obtenerEstadisticas();
//    }

//    @Transactional(readOnly = true)
//    public List<NivelEducativo> listarPorCategoriaActivos(String categoria) {
//        return repository.listarPorCategoriaActivos(categoria);
//    }

    @Transactional(readOnly = true)
    public List<NivelEducativo> listarNivelesPrincipales() {
        return repository.listarNivelesPrincipales();
    }

    @Transactional
    public Optional<NivelEducativo> actualizar(Long id, ActualizarNivelEducativoDto dto) {
        if (dto.getNivelSuperiorId() != null) {
            validarNivelSuperior(dto.getNivelSuperiorId());
        }
        
        return repository.actualizar(id, dto);
    }

    @Transactional
    public boolean eliminar(Long id) {
        Optional<NivelEducativo> nivel = repository.buscarPorId(id);
        if (nivel.isPresent()) {
            // Verificar si tiene niveles dependientes
            List<NivelEducativo> dependientes = repository.listarPorNivelSuperior(id);
            if (!dependientes.isEmpty()) {
                throw new IllegalStateException("No se puede eliminar el nivel educativo porque tiene niveles dependientes");
            }
            
            return repository.eliminar(id);
        }
        return false;
    }

    private void validarCodigoUnico(String codigo) {
        if (repository.existePorCodigo(codigo)) {
            throw new IllegalArgumentException("Ya existe un nivel educativo con el código: " + codigo);
        }
    }

    private void validarNivelSuperior(Long nivelSuperiorId) {
        if (nivelSuperiorId != null) {
            Optional<NivelEducativo> nivelSuperior = repository.buscarPorId(nivelSuperiorId);
            if (nivelSuperior.isEmpty()) {
                throw new IllegalArgumentException("El nivel superior especificado no existe");
            }
        }
    }
}
