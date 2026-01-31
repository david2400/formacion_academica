package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.acudiente;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.acudiente.AcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.acudiente.ActualizarAcudienteDto;

public interface ActualizarAcudienteUseCase {

    AcudienteDto actualizar(ActualizarAcudienteDto request);
}
