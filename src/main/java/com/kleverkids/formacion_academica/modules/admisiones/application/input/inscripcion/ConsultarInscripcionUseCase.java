package com.kleverkids.formacion_academica.modules.admisiones.application.input.inscripcion;

import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion.InscripcionDto;

import java.util.UUID;

public interface ConsultarInscripcionUseCase {

    InscripcionDto consultarPorId(UUID inscripcionId);
}
