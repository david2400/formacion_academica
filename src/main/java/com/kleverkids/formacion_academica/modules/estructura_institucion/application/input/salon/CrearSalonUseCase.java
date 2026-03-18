package com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.salon;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.salon.CrearSalonDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Salon;

public interface CrearSalonUseCase {
    Salon crear(CrearSalonDto request);
}
