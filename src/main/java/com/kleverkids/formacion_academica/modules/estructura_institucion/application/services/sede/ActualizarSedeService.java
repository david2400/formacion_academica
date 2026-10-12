package com.kleverkids.formacion_academica.modules.estructura_institucion.application.services.sede;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.sede.*;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.sede.SedeRepositoryPort;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.sedes.ActualizarSedeDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Sede;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ActualizarSedeService implements ActualizarSedeUseCase {

    private final SedeRepositoryPort sedeRepositoryPort;

    @Override
    public Sede actualizar(ActualizarSedeDto request) {
        // Validar que la sede exista
        Optional<Sede> sedeExistente = sedeRepositoryPort.obtenerPorId(request.getId());
        if (sedeExistente.isEmpty()) {
            throw new IllegalArgumentException("No se encontró la sede con ID: " + request.getId());
        }
        
        return sedeRepositoryPort.actualizar(request.getId(), request)
                .orElseThrow(() -> new IllegalArgumentException("No se pudo actualizar la sede"));
    }
}
