package com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.grado;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Grado;

public interface ConsultarGradoUseCase {

    Grado consultarPorId(Long gradoId);
}
