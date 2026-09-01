package com.kleverkids.formacion_academica.modules.control_academico.application.input.examen_tematica;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_tematica.ExamenTematicaDto;

import java.util.List;

public interface ListarExamenTematicasPorExamenUseCase {

    List<ExamenTematicaDto> listarPorExamen(Long examenId);
}
