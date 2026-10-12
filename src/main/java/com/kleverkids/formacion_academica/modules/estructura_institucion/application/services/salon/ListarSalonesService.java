package com.kleverkids.formacion_academica.modules.estructura_institucion.application.services.salon;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.salon.ListarSalonesUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.salon.SalonRepositoryPort;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Salon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarSalonesService implements ListarSalonesUseCase {

    private final SalonRepositoryPort salonRepositoryPort;

    @Override
    public List<Salon> listar() {
        return salonRepositoryPort.listar();
    }
}
