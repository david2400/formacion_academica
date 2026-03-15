package com.kleverkids.formacion_academica.modules.admisiones.application.output.inscripcion;

import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion.ActualizarEstadoInscripcionDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion.CrearInscripcionDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion.InscripcionDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion.ListarInscripcionesFiltroDto;

import java.util.List;
import java.util.Optional;

public interface InscripcionRepositoryPort {

    InscripcionDto registrar(CrearInscripcionDto request);

    Optional<InscripcionDto> obtenerPorId(Long inscripcionId);

    List<InscripcionDto> listar(ListarInscripcionesFiltroDto filtro);

    InscripcionDto actualizarEstado(ActualizarEstadoInscripcionDto request);

    void eliminar(Long inscripcionId);
}
