package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.services;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.acudiente.ActualizarAcudienteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.acudiente.ConsultarAcudienteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.acudiente.CrearAcudienteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.acudiente.ListarAcudientesPorEstudianteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.acudiente.AcudienteRepositoryPort;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.estudiante.EstudianteRepositoryPort;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.acudiente.AcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.acudiente.ActualizarAcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.acudiente.CrearAcudienteDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AcudienteService implements CrearAcudienteUseCase,
        ActualizarAcudienteUseCase,
        ConsultarAcudienteUseCase,
        ListarAcudientesPorEstudianteUseCase {

    private final AcudienteRepositoryPort acudienteRepositoryPort;
    private final EstudianteRepositoryPort estudianteRepositoryPort;

    public AcudienteService(AcudienteRepositoryPort acudienteRepositoryPort,
                            EstudianteRepositoryPort estudianteRepositoryPort) {
        this.acudienteRepositoryPort = acudienteRepositoryPort;
        this.estudianteRepositoryPort = estudianteRepositoryPort;
    }

    @Override
    public AcudienteDto crear(CrearAcudienteDto request) {
        validarExistenciaEstudiante(request.estudianteId());
        validarPrincipalUnico(request.estudianteId(), null, request.esPrincipal());
        return acudienteRepositoryPort.guardar(request);
    }

    @Override
    public AcudienteDto actualizar(ActualizarAcudienteDto request) {
        AcudienteDto existente = consultarPorId(request.acudienteId());
        UUID estudianteId = request.estudianteId() != null ? request.estudianteId() : existente.estudianteId();
        if (request.estudianteId() != null && !request.estudianteId().equals(existente.estudianteId())) {
            validarExistenciaEstudiante(request.estudianteId());
        }
        validarPrincipalUnico(estudianteId, request.acudienteId(), request.esPrincipal());
        return acudienteRepositoryPort.actualizar(request);
    }

    @Override
    public AcudienteDto consultarPorId(UUID acudienteId) {
        return acudienteRepositoryPort.obtenerPorId(acudienteId)
                .orElseThrow(() -> new IllegalArgumentException("Acudiente no encontrado"));
    }

    @Override
    public List<AcudienteDto> listar(UUID estudianteId) {
        validarExistenciaEstudiante(estudianteId);
        return acudienteRepositoryPort.listarPorEstudiante(estudianteId);
    }

    private void validarExistenciaEstudiante(UUID estudianteId) {
        estudianteRepositoryPort.obtenerPorId(estudianteId)
                .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado"));
    }

    private void validarPrincipalUnico(UUID estudianteId, UUID excluirAcudienteId, Boolean esPrincipal) {
        if (Boolean.TRUE.equals(esPrincipal) &&
                acudienteRepositoryPort.existePrincipalParaEstudiante(estudianteId, excluirAcudienteId)) {
            throw new IllegalArgumentException("El estudiante ya tiene un acudiente principal");
        }
    }
}
