package com.kleverkids.formacion_academica.modules.admisiones.application.services.matricula;

import com.kleverkids.formacion_academica.modules.admisiones.application.input.matricula.ConsultarMatriculaUseCase;
import com.kleverkids.formacion_academica.modules.admisiones.application.output.matricula.MatriculaRepositoryPort;
import com.kleverkids.formacion_academica.modules.admisiones.domain.model.Matricula;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConsultarMatriculaService implements ConsultarMatriculaUseCase {

    private final MatriculaRepositoryPort matriculaRepositoryPort;

    @Override
    public Optional<Matricula> consultarPorId(Long matriculaId) {
        return matriculaRepositoryPort.obtenerPorId(matriculaId);
    }
}
