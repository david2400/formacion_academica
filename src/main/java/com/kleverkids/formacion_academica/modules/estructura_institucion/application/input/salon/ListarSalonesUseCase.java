package com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.salon;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Salon;

import java.util.List;

public interface ListarSalonesUseCase {
    List<Salon> listar();
}
