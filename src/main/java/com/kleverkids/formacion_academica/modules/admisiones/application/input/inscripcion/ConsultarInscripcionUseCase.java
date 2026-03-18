package com.kleverkids.formacion_academica.modules.admisiones.application.input.inscripcion;

import com.kleverkids.formacion_academica.modules.admisiones.domain.model.Inscripcion;

public interface ConsultarInscripcionUseCase {

    Inscripcion consultarPorId(Long inscripcionId);
}
