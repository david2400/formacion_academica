package com.kleverkids.formacion_academica.modules.estructura_institucion.application.services.grado;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.grado.ListarGradosUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.grado.GradoRepositoryPort;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Grado;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarGradosService implements ListarGradosUseCase {

    private final GradoRepositoryPort gradoRepositoryPort;

    @Override
    public List<Grado> listar() {
        return gradoRepositoryPort.listar();
    }
}
