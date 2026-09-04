package com.kleverkids.formacion_academica.modules.control_academico.application.input.clase;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.FiltroClasesDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.Clase;

import java.util.List;

public interface ListarClasesUseCase {

    List<Clase> listarTodas();

    /** Lista aplicando los criterios informados; si vienen vacíos, devuelve todas. */
    List<Clase> buscar(FiltroClasesDto filtro);
}
