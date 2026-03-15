package com.kleverkids.formacion_academica.modules.control_academico.application.input.tematica;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tematica.TematicaDto;

public interface ConsultarTematicaUseCase {

    TematicaDto consultarPorId(Long tematicaId);
}
