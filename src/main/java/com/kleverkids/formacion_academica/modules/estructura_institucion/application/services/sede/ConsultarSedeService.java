package com.kleverkids.formacion_academica.modules.estructura_institucion.application.services.sede;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.sede.*;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.sede.SedeRepositoryPort;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Sede;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConsultarSedeService implements ConsultarSedeUseCase {

    private final SedeRepositoryPort sedeRepositoryPort;

    @Override
    public Optional<Sede> buscarPorId(Long id) {
        return sedeRepositoryPort.obtenerPorId(id);
    }
}
