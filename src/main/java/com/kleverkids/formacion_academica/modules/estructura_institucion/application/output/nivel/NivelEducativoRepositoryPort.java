package com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.nivel;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.nivel.ActualizarNivelEducativoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.nivel.CrearNivelEducativoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.nivel.NivelEducativoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.nivel.NivelEducativoEstadisticasDto;

import java.util.List;
import java.util.Optional;

public interface NivelEducativoRepositoryPort {
    
    NivelEducativoDto crear(CrearNivelEducativoDto dto);
    
    Optional<NivelEducativoDto> buscarPorId(Long id);
    
    Optional<NivelEducativoDto> buscarPorCodigo(String codigo);
    
    List<NivelEducativoDto> listarTodos();
    
    List<NivelEducativoDto> listarPorCategoria(String categoria);
    
    List<NivelEducativoDto> listarPorNivelSuperior(Long nivelSuperiorId);
    
    Optional<NivelEducativoDto> actualizar(Long id, ActualizarNivelEducativoDto dto);
    
    boolean eliminar(Long id);
    
    boolean existePorCodigo(String codigo);
    
    List<NivelEducativoDto> listarActivos();
    
    // Métodos de estadísticas
    NivelEducativoEstadisticasDto obtenerEstadisticas();
    
    List<NivelEducativoDto> listarPorCategoriaActivos(String categoria);
    
    List<NivelEducativoDto> listarNivelesPrincipales();
}
