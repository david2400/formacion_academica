package com.kleverkids.formacion_academica.modules.admisiones.application.input.inscripcion;

import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion.InscripcionDto;

public interface ConsultarInscripcionUseCase {

    InscripcionDto consultarPorId(Long inscripcionId);
}
