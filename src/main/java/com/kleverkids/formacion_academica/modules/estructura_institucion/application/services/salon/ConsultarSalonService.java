package com.kleverkids.formacion_academica.modules.estructura_institucion.application.services.salon;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.salon.ConsultarSalonUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.salon.SalonRepositoryPort;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Salon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsultarSalonService implements ConsultarSalonUseCase {

    private final SalonRepositoryPort salonRepositoryPort;

    @Override
    public Salon consultarPorId(Long salonId) {
        return salonRepositoryPort.obtenerPorId(salonId);
    }
}
