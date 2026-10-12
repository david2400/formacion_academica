package com.kleverkids.formacion_academica.modules.control_academico.application.services.tematica;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.tematica.ListarTematicasUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.tematica.TematicaRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.Tematica;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarTematicasService implements ListarTematicasUseCase {

    private final TematicaRepositoryPort repositoryPort;

    @Override
    public List<Tematica> listar() {
        return repositoryPort.listar();
    }
}
