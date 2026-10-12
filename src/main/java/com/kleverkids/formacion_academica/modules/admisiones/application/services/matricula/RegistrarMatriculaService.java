package com.kleverkids.formacion_academica.modules.admisiones.application.services.matricula;

import com.kleverkids.formacion_academica.modules.admisiones.application.input.matricula.RegistrarMatriculaUseCase;
import com.kleverkids.formacion_academica.modules.admisiones.application.output.matricula.MatriculaRepositoryPort;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.matricula.CrearMatriculaDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.model.Matricula;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrarMatriculaService implements RegistrarMatriculaUseCase {

    private final MatriculaRepositoryPort matriculaRepositoryPort;

    @Override
    public Matricula registrar(CrearMatriculaDto request) {
        return matriculaRepositoryPort.registrar(request);
    }
}
