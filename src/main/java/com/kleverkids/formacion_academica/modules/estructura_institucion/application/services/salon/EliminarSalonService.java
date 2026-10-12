package com.kleverkids.formacion_academica.modules.estructura_institucion.application.services.salon;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.salon.EliminarSalonUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.salon.SalonRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EliminarSalonService implements EliminarSalonUseCase {

    private final SalonRepositoryPort salonRepositoryPort;
    private final SalonSoporte salonSoporte;

    @Override
    public void eliminar(Long salonId) {
        salonSoporte.validarExistencia(salonId);
        salonRepositoryPort.eliminar(salonId);
    }
}
