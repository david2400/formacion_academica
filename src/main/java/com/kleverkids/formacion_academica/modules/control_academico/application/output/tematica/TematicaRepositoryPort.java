package com.kleverkids.formacion_academica.modules.control_academico.application.output.tematica;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tematica.ActualizarTematicaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tematica.CrearTematicaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tematica.TematicaDto;

import java.util.List;

public interface TematicaRepositoryPort {

    TematicaDto guardar(CrearTematicaDto request);

    TematicaDto actualizar(ActualizarTematicaDto request);

    List<TematicaDto> listarPorExamen(Long examenId);

    TematicaDto obtenerPorId(Long tematicaId);

    void eliminar(Long tematicaId);
}
