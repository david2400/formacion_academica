package com.kleverkids.formacion_academica.modules.estados.application.input.estado;

import com.kleverkids.formacion_academica.modules.estados.domain.dto.EstadoDto;

import java.util.List;
import java.util.Optional;

public interface ConsultarEstadoUseCase {
    Optional<EstadoDto> consultarPorId(Long id);
    Optional<EstadoDto> consultarPorCodigo(String codigo);
    List<EstadoDto> listarPorModulo(Long idModulo);
    List<EstadoDto> listarPorModuloYEmpresa(Long idModulo, Long idEmpresa);
    List<EstadoDto> listarIniciales(Long idModulo);
    List<EstadoDto> listarFinales(Long idModulo);
}
