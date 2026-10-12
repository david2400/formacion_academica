package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.services.estudiante_acudiente;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante_acudiente.CrearEstudianteAcudienteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.estudiante_acudiente.EstudianteAcudienteRepositoryPort;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante_acudiente.CrearEstudianteAcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model.EstudianteAcudiente;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CrearEstudianteAcudienteService implements CrearEstudianteAcudienteUseCase {

    private final EstudianteAcudienteRepositoryPort relacionRepositoryPort;
    private final EstudianteAcudienteSoporte estudianteAcudienteSoporte;

    @Override
    public EstudianteAcudiente crear(CrearEstudianteAcudienteDto request) {
        estudianteAcudienteSoporte.validarExistenciaEstudiante(request.getEstudianteId());
        estudianteAcudienteSoporte.validarExistenciaAcudiente(request.getAcudienteId());
        validarPrincipalUnico(request.getEstudianteId(), null, request.isEsPrincipal());
        return relacionRepositoryPort.crear(request);
    }

    private void validarPrincipalUnico(Long estudianteId, Long excluirRelacionId, boolean esPrincipal) {
        if (esPrincipal && relacionRepositoryPort.existeRelacionPrincipal(estudianteId, excluirRelacionId)) {
            throw new IllegalArgumentException("El estudiante ya tiene un acudiente principal registrado");
        }
    }
}
