package com.kleverkids.formacion_academica.modules.estados.application.input.estado;

import com.kleverkids.formacion_academica.modules.estados.domain.dto.EstadoDto;

public interface CrearEstadoUseCase {
    EstadoDto crear(EstadoDto estado);
}
