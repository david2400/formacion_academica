package com.kleverkids.formacion_academica.modules.estructura_institucion.application.services.aula;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.aula.ListarAulasUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.aula.AulaRepositoryPort;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Aula;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarAulasService implements ListarAulasUseCase {

    private final AulaRepositoryPort aulaRepositoryPort;

    @Override
    public List<Aula> listar() {
        return aulaRepositoryPort.listar();
    }
}
