package com.kleverkids.formacion_academica.modules.admisiones.application.services.matricula;

import com.kleverkids.formacion_academica.modules.admisiones.application.input.matricula.ActualizarMatriculaUseCase;
import com.kleverkids.formacion_academica.modules.admisiones.application.output.matricula.MatriculaRepositoryPort;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.matricula.ActualizarMatriculaDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.model.Matricula;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActualizarMatriculaService implements ActualizarMatriculaUseCase {

    private final MatriculaRepositoryPort matriculaRepositoryPort;

    @Override
    public Matricula actualizar(ActualizarMatriculaDto request) {
        return matriculaRepositoryPort.actualizar(request);
    }
}
