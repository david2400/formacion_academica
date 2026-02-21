package com.kleverkids.formacion_academica.modules.estructura_institucion.application.services;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.grado.ActualizarGradoUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.grado.ConsultarGradoUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.grado.CrearGradoUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.grado.EliminarGradoUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.grado.ListarGradosUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.grado.GradoRepositoryPort;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grado.ActualizarGradoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grado.CrearGradoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grado.GradoDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GradoService implements CrearGradoUseCase, ActualizarGradoUseCase,
        ConsultarGradoUseCase, ListarGradosUseCase, EliminarGradoUseCase {

    private final GradoRepositoryPort gradoRepositoryPort;

    public GradoService(GradoRepositoryPort gradoRepositoryPort) {
        this.gradoRepositoryPort = gradoRepositoryPort;
    }

    @Override
    public GradoDto crear(CrearGradoDto request) {
        return gradoRepositoryPort.guardar(request);
    }

    @Override
    public GradoDto actualizar(ActualizarGradoDto request) {
        return gradoRepositoryPort.actualizar(request);
    }

    @Override
    public GradoDto consultarPorId(UUID gradoId) {
        return gradoRepositoryPort.obtenerPorId(gradoId);
    }

    @Override
    public List<GradoDto> listar() {
        return gradoRepositoryPort.listar();
    }

    @Override
    public void eliminar(UUID gradoId) {
        gradoRepositoryPort.obtenerPorId(gradoId);
        gradoRepositoryPort.eliminar(gradoId);
    }

    private void validarNombreYNivel(String nombre, String nivel) {
        if (gradoRepositoryPort.existePorNombreYNivel(nombre, nivel)) {
            throw new IllegalArgumentException("Ya existe un grado para el nivel indicado");
        }
    }
}
