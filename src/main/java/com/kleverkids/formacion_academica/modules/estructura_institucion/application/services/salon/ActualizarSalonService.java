package com.kleverkids.formacion_academica.modules.estructura_institucion.application.services.salon;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.salon.ActualizarSalonUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.salon.SalonRepositoryPort;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.salon.ActualizarSalonDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Salon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActualizarSalonService implements ActualizarSalonUseCase {

    private final SalonRepositoryPort salonRepositoryPort;
    private final SalonSoporte salonSoporte;

    @Override
    public Salon actualizar(ActualizarSalonDto request) {
        salonSoporte.validarExistencia(request.getId());
        salonSoporte.validarCodigoDisponible(request.getCodigo(), request.getId());
        return salonRepositoryPort.actualizar(request);
    }
}
