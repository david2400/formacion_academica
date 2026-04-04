package com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.sede;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Sede;
import java.util.List;

public interface ListarSedesUseCase {
    List<Sede> listarTodas();
    List<Sede> listarActivas();
//    List<Sede> listarPorCiudad(String ciudad);
}
