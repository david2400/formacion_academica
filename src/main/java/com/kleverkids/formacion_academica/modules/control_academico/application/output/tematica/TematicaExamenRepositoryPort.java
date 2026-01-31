package com.kleverkids.formacion_academica.modules.control_academico.application.output.tematica;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tematica.ActualizarTematicaExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tematica.CrearTematicaExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tematica.TematicaExamenDto;

import java.util.List;
import java.util.UUID;

public interface TematicaExamenRepositoryPort {

    TematicaExamenDto guardar(CrearTematicaExamenDto request);

    TematicaExamenDto actualizar(ActualizarTematicaExamenDto request);

    List<TematicaExamenDto> listarPorExamen(UUID examenId);
}
