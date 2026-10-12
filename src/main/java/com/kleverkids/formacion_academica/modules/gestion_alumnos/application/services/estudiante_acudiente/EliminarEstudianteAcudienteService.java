package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.services.estudiante_acudiente;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante_acudiente.ConsultarEstudianteAcudienteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante_acudiente.EliminarEstudianteAcudienteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.estudiante_acudiente.EstudianteAcudienteRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EliminarEstudianteAcudienteService implements EliminarEstudianteAcudienteUseCase {

    private final EstudianteAcudienteRepositoryPort relacionRepositoryPort;
    private final ConsultarEstudianteAcudienteUseCase consultarEstudianteAcudienteUseCase;

    @Override
    public void eliminar(Long relacionId) {
        consultarEstudianteAcudienteUseCase.consultarPorId(relacionId);
        relacionRepositoryPort.eliminar(relacionId);
    }
}
