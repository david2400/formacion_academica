package com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.nivel;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.nivel_academico.ActualizarNivelEducativoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.nivel_academico.CrearNivelEducativoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.NivelEducativo;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.nivel_academico.NivelEducativoEstadisticasDto;

import java.util.List;
import java.util.Optional;

public interface NivelEducativoRepositoryPort {
    
    NivelEducativo crear(CrearNivelEducativoDto dto);
    
    Optional<NivelEducativo> buscarPorId(Long id);
    
    Optional<NivelEducativo> buscarPorCodigo(String codigo);
    
    List<NivelEducativo> listarTodos();
    
//    List<NivelEducativo> listarPorCategoria(String categoria);
    
    List<NivelEducativo> listarPorNivelSuperior(Long nivelSuperiorId);
    
    Optional<NivelEducativo> actualizar(Long id, ActualizarNivelEducativoDto dto);
    
    boolean eliminar(Long id);
    
    boolean existePorCodigo(String codigo);
    
    List<NivelEducativo> listarActivos();
    
    // Métodos de estadísticas
//    NivelEducativoEstadisticasDto obtenerEstadisticas();
    
//    List<NivelEducativo> listarPorCategoriaActivos(String categoria);
    
    List<NivelEducativo> listarNivelesPrincipales();
}
