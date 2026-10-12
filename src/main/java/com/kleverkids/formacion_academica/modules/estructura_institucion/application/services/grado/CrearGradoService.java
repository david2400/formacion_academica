package com.kleverkids.formacion_academica.modules.estructura_institucion.application.services.grado;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.grado.CrearGradoUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.grado.GradoRepositoryPort;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grado.CrearGradoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Grado;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CrearGradoService implements CrearGradoUseCase {

    private final GradoRepositoryPort gradoRepositoryPort;

    @Override
    public Grado crear(CrearGradoDto request) {
        return gradoRepositoryPort.guardar(request);
    }

    private void validarNombreYNivel(String nombre, String nivel) {
        if (gradoRepositoryPort.existePorNombreYNivel(nombre, nivel)) {
            throw new IllegalArgumentException("Ya existe un grado para el nivel indicado");
        }
    }
}
