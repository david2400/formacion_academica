package com.kleverkids.formacion_academica.modules.estructura_institucion.application.services.sede;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.sede.*;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.sede.SedeRepositoryPort;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.sedes.CrearSedeDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Sede;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CrearSedeService implements CrearSedeUseCase {

    private final SedeRepositoryPort sedeRepositoryPort;

    @Override
    public Sede crear(CrearSedeDto request) {
        // Validar que no exista una sede con el mismo código
//        if (sedeRepositoryPort.existePorCodigo(request.getCodigo())) {
//            throw new IllegalArgumentException("Ya existe una sede con el código: " + request.getCodigo());
//        }
        return sedeRepositoryPort.registrar(request);
    }
}
