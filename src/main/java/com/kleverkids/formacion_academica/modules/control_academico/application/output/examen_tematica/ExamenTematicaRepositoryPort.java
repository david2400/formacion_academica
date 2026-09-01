package com.kleverkids.formacion_academica.modules.control_academico.application.output.examen_tematica;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_tematica.CrearExamenTematicaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_tematica.ExamenTematicaDto;

import java.util.List;

public interface ExamenTematicaRepositoryPort {

    ExamenTematicaDto asignar(CrearExamenTematicaDto request);

    List<ExamenTematicaDto> listarPorExamen(Long examenId);

    void eliminar(Long examenId, Long tematicaId);
}
