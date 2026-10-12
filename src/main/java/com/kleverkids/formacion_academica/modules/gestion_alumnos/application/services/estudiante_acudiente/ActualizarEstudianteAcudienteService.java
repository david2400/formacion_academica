package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.services.estudiante_acudiente;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante_acudiente.ActualizarEstudianteAcudienteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.estudiante_acudiente.EstudianteAcudienteRepositoryPort;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante_acudiente.ActualizarEstudianteAcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model.EstudianteAcudiente;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActualizarEstudianteAcudienteService implements ActualizarEstudianteAcudienteUseCase {

    private final EstudianteAcudienteRepositoryPort relacionRepositoryPort;

    @Override
    public EstudianteAcudiente actualizar(ActualizarEstudianteAcudienteDto request) {
//        EstudianteAcudiente actual = consultarPorId(request.relacionId());
//        Long estudianteId = request.estudianteId() != null ? request.estudianteId() : actual.estudianteId();
//        if (request.estudianteId() != null && !request.getEstudianteId().equals(actual.estudianteId())) {
//            validarExistenciaEstudiante(request.estudianteId());
//        }
//        validarPrincipalUnico(estudianteId, request.relacionId(), request.esPrincipal());
        return relacionRepositoryPort.actualizar(request);
    }
}
