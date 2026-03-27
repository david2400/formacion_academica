package com.kleverkids.formacion_academica.modules.admisiones.application.input.matricula;

import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.matricula.ActualizarMatriculaDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.model.Matricula;

public interface ActualizarMatriculaUseCase {
    Matricula actualizar(ActualizarMatriculaDto request);
}
