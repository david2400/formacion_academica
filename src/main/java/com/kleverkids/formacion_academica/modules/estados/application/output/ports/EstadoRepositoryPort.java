package com.kleverkids.formacion_academica.modules.estados.application.output.ports;

import com.kleverkids.formacion_academica.modules.estados.domain.dto.EstadoDto;
import com.kleverkids.formacion_academica.modules.estados.domain.dto.CambioEstadoRequest;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida para el repositorio de Estados
 * Sigue el patrón Hexagonal/Clean Architecture
 */
public interface EstadoRepositoryPort {
    
    // Gestión de Estados
    EstadoDto crear(EstadoDto estado);
    Optional<EstadoDto> buscarPorId(Long id);
    Optional<EstadoDto> buscarPorCodigo(String codigo);
    List<EstadoDto> listarTodos();
    List<EstadoDto> listarPorModulo(Long idModulo);
    List<EstadoDto> listarPorModuloYEmpresa(Long idModulo, Long idEmpresa);
    Optional<EstadoDto> actualizar(Long id, EstadoDto estado);
    boolean eliminar(Long id);
    boolean existePorCodigo(String codigo);
    
    // Gestión de Transiciones
    boolean esTransicionValida(Long estadoOrigenId, Long estadoDestinoId, Long idModulo);
    boolean esTransicionValida(Long estadoOrigenId, Long estadoDestinoId, Long idModulo, Long idEmpresa);
    
    // Gestión de Estados de Entidades
    void asignarEstadoInicial(String entidadTipo, Long entidadId, Long idModulo, Long idUsuarioCambio);
    void cambiarEstado(String entidadTipo, Long entidadId, Long nuevoEstadoId, Long idUsuarioCambio, String motivo);
    Optional<EstadoDto> obtenerEstadoActual(String entidadTipo, Long entidadId);
    List<EstadoDto> listarHistorialEstados(String entidadTipo, Long entidadId);
    
    // Consultas especializadas
    List<EstadoDto> listarEstadosIniciales(Long idModulo);
    List<EstadoDto> listarEstadosFinales(Long idModulo);
    List<EstadoDto> listarEntidadesConEstado(Long estadoId);
    Long contarEntidadesConEstado(Long estadoId);
}
