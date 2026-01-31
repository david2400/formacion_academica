package com.kleverkids.formacion_academica.modules.admisiones.application.input.inscripcion;

import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion.InscripcionDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion.ListarInscripcionesFiltroDto;

import java.util.List;

public interface ListarInscripcionesUseCase {

    List<InscripcionDto> listar(ListarInscripcionesFiltroDto filtro);
}
