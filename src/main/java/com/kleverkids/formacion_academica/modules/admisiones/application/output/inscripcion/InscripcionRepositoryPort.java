package com.kleverkids.formacion_academica.modules.admisiones.application.output.inscripcion;

import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion.ActualizarEstadoInscripcionDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion.CrearInscripcionDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.model.Inscripcion;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion.ListarInscripcionesFiltroDto;

import java.util.List;
import java.util.Optional;

public interface InscripcionRepositoryPort {

    Inscripcion registrar(CrearInscripcionDto request);

    Optional<Inscripcion> obtenerPorId(Long inscripcionId);

    List<Inscripcion> listar(ListarInscripcionesFiltroDto filtro);

    Inscripcion actualizarEstado(ActualizarEstadoInscripcionDto request);

    void eliminar(Long inscripcionId);
}
