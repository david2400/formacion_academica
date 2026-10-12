package com.kleverkids.formacion_academica.modules.admisiones.application.services.matricula;

import com.kleverkids.formacion_academica.modules.admisiones.application.input.matricula.ConsultarMatriculaUseCase;
import com.kleverkids.formacion_academica.modules.admisiones.application.input.matricula.EliminarMatriculaUseCase;
import com.kleverkids.formacion_academica.modules.admisiones.application.output.matricula.MatriculaRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EliminarMatriculaService implements EliminarMatriculaUseCase {

    private final MatriculaRepositoryPort matriculaRepositoryPort;
    private final ConsultarMatriculaUseCase consultarMatriculaUseCase;

    @Override
    public void eliminar(Long matriculaId) {
        consultarMatriculaUseCase.consultarPorId(matriculaId);
        matriculaRepositoryPort.eliminar(matriculaId);
    }
}
