package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.services;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante.ActualizarEstudianteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante.ConsultarEstudianteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante.CrearEstudianteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante.ListarEstudiantesUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.estudiante.EstudianteRepositoryPort;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante.CrearEstudianteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante.EstudianteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante.UpdateEstudianteDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EstudianteService implements CrearEstudianteUseCase,
        ActualizarEstudianteUseCase,
        ConsultarEstudianteUseCase,
        ListarEstudiantesUseCase {

    private final EstudianteRepositoryPort repositoryPort;

    public EstudianteService(EstudianteRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public EstudianteDto crear(CrearEstudianteDto request) {
        validarDocumentoUnico(request.tipoDocumento(), request.numeroDocumento());
        return repositoryPort.guardar(request);
    }

    @Override
    public EstudianteDto actualizar(UpdateEstudianteDto request) {
        return repositoryPort.actualizar(request);
    }

    @Override
    public EstudianteDto consultarPorId(UUID estudianteId) {
        return repositoryPort.obtenerPorId(estudianteId)
                .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado"));
    }

    @Override
    public List<EstudianteDto> listar() {
        return repositoryPort.listar();
    }

    private void validarDocumentoUnico(String tipoDocumento, String numeroDocumento) {
        if (repositoryPort.existePorDocumento(tipoDocumento, numeroDocumento)) {
            throw new IllegalArgumentException("Ya existe un estudiante con el documento indicado");
        }
    }
}
