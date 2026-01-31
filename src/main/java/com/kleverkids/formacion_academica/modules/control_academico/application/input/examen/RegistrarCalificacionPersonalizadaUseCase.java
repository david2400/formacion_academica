package com.kleverkids.formacion_academica.modules.control_academico.application.input.examen;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.CalificacionPersonalizadaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.RegistrarCalificacionPersonalizadaDto;

public interface RegistrarCalificacionPersonalizadaUseCase {

    CalificacionPersonalizadaDto registrar(RegistrarCalificacionPersonalizadaDto request);
}
