package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.services;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.acudiente.ActualizarAcudienteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.acudiente.ConsultarAcudienteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.acudiente.CrearAcudienteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.acudiente.EliminarAcudienteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.acudiente.ListarAcudientesPorEstudianteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.acudiente.AcudienteRepositoryPort;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.estudiante.EstudianteRepositoryPort;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model.Acudiente;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.acudiente.ActualizarAcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.acudiente.CrearAcudienteDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AcudienteService implements CrearAcudienteUseCase,
        ActualizarAcudienteUseCase,
        ConsultarAcudienteUseCase,
        ListarAcudientesPorEstudianteUseCase,
        EliminarAcudienteUseCase {

    private final AcudienteRepositoryPort acudienteRepositoryPort;
    private final EstudianteRepositoryPort estudianteRepositoryPort;

    public AcudienteService(AcudienteRepositoryPort acudienteRepositoryPort,
                            EstudianteRepositoryPort estudianteRepositoryPort) {
        this.acudienteRepositoryPort = acudienteRepositoryPort;
        this.estudianteRepositoryPort = estudianteRepositoryPort;
    }

    @Override
    public Acudiente crear(CrearAcudienteDto request) {
        return acudienteRepositoryPort.guardar(request);
    }

    @Override
    public Acudiente actualizar(ActualizarAcudienteDto request) {
        Acudiente existente = consultarPorId(request.getId());

        return acudienteRepositoryPort.actualizar(request);
    }

    @Override
    public Acudiente consultarPorId(Long acudienteId) {
        return acudienteRepositoryPort.obtenerPorId(acudienteId)
                .orElseThrow(() -> new IllegalArgumentException("Acudiente no encontrado"));
    }

    @Override
    public List<Acudiente> listar(Long estudianteId) {
        validarExistenciaEstudiante(estudianteId);
        return acudienteRepositoryPort.listarPorEstudiante(estudianteId);
    }

    @Override
    public void eliminar(Long acudienteId) {
        consultarPorId(acudienteId);
        acudienteRepositoryPort.eliminar(acudienteId);
    }

    private void validarExistenciaEstudiante(Long estudianteId) {
        estudianteRepositoryPort.obtenerPorId(estudianteId)
                .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado"));
    }

    private void validarPrincipalUnico(Long estudianteId, Long excluirAcudienteId, Boolean esPrincipal) {
        if (Boolean.TRUE.equals(esPrincipal) &&
                acudienteRepositoryPort.existePrincipalParaEstudiante(estudianteId, excluirAcudienteId)) {
            throw new IllegalArgumentException("El estudiante ya tiene un acudiente principal");
        }
    }
}
