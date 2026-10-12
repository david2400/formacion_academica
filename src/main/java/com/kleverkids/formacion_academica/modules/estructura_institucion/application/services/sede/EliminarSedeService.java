package com.kleverkids.formacion_academica.modules.estructura_institucion.application.services.sede;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.sede.*;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.sede.SedeRepositoryPort;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Sede;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EliminarSedeService implements EliminarSedeUseCase {

    private final SedeRepositoryPort sedeRepositoryPort;

//    @Override
//    public List<Sede> listarPorCiudad(String ciudad) {
//        return sedeRepositoryPort.listarPorCiudad(ciudad);
//    }

    @Override
    public boolean eliminar(Long id) {
        // Validar que la sede exista
        Optional<Sede> sedeExistente = sedeRepositoryPort.obtenerPorId(id);
        if (sedeExistente.isEmpty()) {
            throw new IllegalArgumentException("No se encontró la sede con ID: " + id);
        }
        
        return sedeRepositoryPort.eliminar(id);
    }
}
