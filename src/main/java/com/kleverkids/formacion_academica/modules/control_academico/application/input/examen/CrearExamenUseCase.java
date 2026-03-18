package com.kleverkids.formacion_academica.modules.control_academico.application.input.examen;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.CrearExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen.Examen;

public interface CrearExamenUseCase {

    Examen crear(CrearExamenDto request);
}
