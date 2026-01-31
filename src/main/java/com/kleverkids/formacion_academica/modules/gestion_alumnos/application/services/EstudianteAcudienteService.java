package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.services;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante_acudiente.ActualizarEstudianteAcudienteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante_acudiente.ConsultarEstudianteAcudienteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante_acudiente.CrearEstudianteAcudienteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante_acudiente.ListarPorAcudienteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante_acudiente.ListarPorEstudianteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.acudiente.AcudienteRepositoryPort;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.estudiante.EstudianteRepositoryPort;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.estudiante_acudiente.EstudianteAcudienteRepositoryPort;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante_acudiente.ActualizarEstudianteAcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante_acudiente.CrearEstudianteAcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante_acudiente.EstudianteAcudienteDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EstudianteAcudienteService implements CrearEstudianteAcudienteUseCase,
        ActualizarEstudianteAcudienteUseCase,
        ConsultarEstudianteAcudienteUseCase,
        ListarPorEstudianteUseCase,
        ListarPorAcudienteUseCase {

    private final EstudianteAcudienteRepositoryPort relacionRepositoryPort;
    private final EstudianteRepositoryPort estudianteRepositoryPort;
    private final AcudienteRepositoryPort acudienteRepositoryPort;

    public EstudianteAcudienteService(EstudianteAcudienteRepositoryPort relacionRepositoryPort,
                                      EstudianteRepositoryPort estudianteRepositoryPort,
                                      AcudienteRepositoryPort acudienteRepositoryPort) {
        this.relacionRepositoryPort = relacionRepositoryPort;
        this.estudianteRepositoryPort = estudianteRepositoryPort;
        this.acudienteRepositoryPort = acudienteRepositoryPort;
    }

    @Override
    public EstudianteAcudienteDto crear(CrearEstudianteAcudienteDto request) {
        validarExistenciaEstudiante(request.estudianteId());
        validarExistenciaAcudiente(request.acudienteId());
        validarPrincipalUnico(request.estudianteId(), null, request.esPrincipal());
        return relacionRepositoryPort.crear(request);
    }

    @Override
    public EstudianteAcudienteDto actualizar(ActualizarEstudianteAcudienteDto request) {
        EstudianteAcudienteDto actual = consultarPorId(request.relacionId());
        if (Boolean.TRUE.equals(request.esPrincipal())) {
            validarPrincipalUnico(actual.estudianteId(), request.relacionId(), true);
        }
        return relacionRepositoryPort.actualizar(request);
    }

    @Override
    public EstudianteAcudienteDto consultarPorId(UUID relacionId) {
        return relacionRepositoryPort.obtenerPorId(relacionId)
                .orElseThrow(() -> new IllegalArgumentException("Relación estudiante-acudiente no encontrada"));
    }

    @Override
    public List<EstudianteAcudienteDto> listarPorEstudiante(UUID estudianteId) {
        validarExistenciaEstudiante(estudianteId);
        return relacionRepositoryPort.listarPorEstudiante(estudianteId);
    }

    @Override
    public List<EstudianteAcudienteDto> listarPorAcudiente(UUID acudienteId) {
        validarExistenciaAcudiente(acudienteId);
        return relacionRepositoryPort.listarPorAcudiente(acudienteId);
    }

    private void validarExistenciaEstudiante(UUID estudianteId) {
        estudianteRepositoryPort.obtenerPorId(estudianteId)
                .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado"));
    }

    private void validarExistenciaAcudiente(UUID acudienteId) {
        acudienteRepositoryPort.obtenerPorId(acudienteId)
                .orElseThrow(() -> new IllegalArgumentException("Acudiente no encontrado"));
    }

    private void validarPrincipalUnico(UUID estudianteId, UUID excluirRelacionId, boolean esPrincipal) {
        if (esPrincipal && relacionRepositoryPort.existeRelacionPrincipal(estudianteId, excluirRelacionId)) {
            throw new IllegalArgumentException("El estudiante ya tiene un acudiente principal registrado");
        }
    }
}
