package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.acudiente;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model.Acudiente;

public interface ConsultarAcudienteUseCase {

    Acudiente consultarPorId(Long acudienteId);
}
