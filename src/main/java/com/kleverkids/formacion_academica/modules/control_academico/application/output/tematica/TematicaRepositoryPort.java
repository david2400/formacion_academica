package com.kleverkids.formacion_academica.modules.control_academico.application.output.tematica;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tematica.ActualizarTematicaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tematica.CrearTematicaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.Tematica;

import java.util.List;

public interface TematicaRepositoryPort {

    Tematica guardar(CrearTematicaDto request);

    Tematica actualizar(ActualizarTematicaDto request);

    List<Tematica> listarPorExamen(Long examenId);

    Tematica obtenerPorId(Long tematicaId);

    void eliminar(Long tematicaId);
}
