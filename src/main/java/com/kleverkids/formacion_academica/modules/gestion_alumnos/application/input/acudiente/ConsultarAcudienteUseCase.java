package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.acudiente;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.acudiente.AcudienteDto;

public interface ConsultarAcudienteUseCase {

    AcudienteDto consultarPorId(Long acudienteId);
}
