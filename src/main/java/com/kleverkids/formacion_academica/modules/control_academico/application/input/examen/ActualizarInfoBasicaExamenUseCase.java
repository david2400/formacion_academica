package com.kleverkids.formacion_academica.modules.control_academico.application.input.examen;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.ActualizarInfoBasicaExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen.Examen;

public interface ActualizarInfoBasicaExamenUseCase {

    Examen actualizarInfoBasica(Long id, ActualizarInfoBasicaExamenDto request);
}
