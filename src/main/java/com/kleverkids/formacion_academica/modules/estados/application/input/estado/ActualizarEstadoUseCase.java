package com.kleverkids.formacion_academica.modules.estados.application.input.estado;

import com.kleverkids.formacion_academica.modules.estados.domain.dto.EstadoDto;

import java.util.Optional;

public interface ActualizarEstadoUseCase {
    Optional<EstadoDto> actualizar(Long id, EstadoDto estado);
}
