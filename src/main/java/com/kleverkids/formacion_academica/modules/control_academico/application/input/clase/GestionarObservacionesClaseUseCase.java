package com.kleverkids.formacion_academica.modules.control_academico.application.input.clase;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.RegistrarObservacionClaseDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.Clase;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.ObservacionClase;

import java.util.List;

/** Bitácora de observaciones de una clase. */
public interface GestionarObservacionesClaseUseCase {

    Clase agregarObservacion(Long claseId, RegistrarObservacionClaseDto request);

    List<ObservacionClase> listarObservaciones(Long claseId);

    Clase eliminarObservacion(Long claseId, Long observacionId);
}
