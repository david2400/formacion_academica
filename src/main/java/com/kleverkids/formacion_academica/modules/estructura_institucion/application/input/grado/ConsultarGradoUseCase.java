package com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.grado;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grado.GradoDto;

import java.util.UUID;

public interface ConsultarGradoUseCase {

    GradoDto consultarPorId(UUID gradoId);
}
