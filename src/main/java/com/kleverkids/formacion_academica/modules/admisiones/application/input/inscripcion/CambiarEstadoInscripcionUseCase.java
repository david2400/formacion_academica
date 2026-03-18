package com.kleverkids.formacion_academica.modules.admisiones.application.input.inscripcion;

import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion.ActualizarEstadoInscripcionDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.model.Inscripcion;

public interface CambiarEstadoInscripcionUseCase {

    Inscripcion cambiarEstado(ActualizarEstadoInscripcionDto request);
}
