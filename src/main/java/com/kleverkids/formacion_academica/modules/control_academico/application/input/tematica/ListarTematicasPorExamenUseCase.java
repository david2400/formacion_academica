package com.kleverkids.formacion_academica.modules.control_academico.application.input.tematica;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tematica.TematicaExamenDto;

import java.util.List;
import java.util.UUID;

public interface ListarTematicasPorExamenUseCase {

    List<TematicaExamenDto> listar(UUID examenId);
}
