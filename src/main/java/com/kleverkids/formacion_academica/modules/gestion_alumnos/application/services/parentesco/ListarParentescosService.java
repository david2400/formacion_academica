package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.services.parentesco;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.parentesco.ListarParentescosUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.parentesco.ParentescoRepositoryPort;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model.Parentesco;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarParentescosService implements ListarParentescosUseCase {

    private final ParentescoRepositoryPort parentescoRepositoryPort;

    @Override
    public List<Parentesco> listarTodos() {
        return parentescoRepositoryPort.listarTodos();
    }

    @Override
    public List<Parentesco> listarActivos() {
        return parentescoRepositoryPort.listarActivos();
    }
}
