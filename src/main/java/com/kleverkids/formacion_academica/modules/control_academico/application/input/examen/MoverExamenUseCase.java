package com.kleverkids.formacion_academica.modules.control_academico.application.input.examen;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.MoverExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen.Examen;

public interface MoverExamenUseCase {

    Examen mover(MoverExamenDto request);
}
