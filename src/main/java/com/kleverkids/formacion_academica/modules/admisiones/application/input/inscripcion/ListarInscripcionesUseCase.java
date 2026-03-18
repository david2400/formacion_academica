package com.kleverkids.formacion_academica.modules.admisiones.application.input.inscripcion;

import com.kleverkids.formacion_academica.modules.admisiones.domain.model.Inscripcion;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion.ListarInscripcionesFiltroDto;

import java.util.List;

public interface ListarInscripcionesUseCase {

    List<Inscripcion> listar(ListarInscripcionesFiltroDto filtro);
}
