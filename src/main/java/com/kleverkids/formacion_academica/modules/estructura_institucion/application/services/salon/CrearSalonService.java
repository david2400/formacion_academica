package com.kleverkids.formacion_academica.modules.estructura_institucion.application.services.salon;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.salon.CrearSalonUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.salon.SalonRepositoryPort;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.salon.CrearSalonDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Salon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CrearSalonService implements CrearSalonUseCase {

    private final SalonRepositoryPort salonRepositoryPort;
    private final SalonSoporte salonSoporte;

    @Override
    public Salon crear(CrearSalonDto request) {
        salonSoporte.validarCodigoDisponible(request.getCodigo(), null);
        return salonRepositoryPort.guardar(request);
    }
}
