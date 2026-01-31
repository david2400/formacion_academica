package com.kleverkids.formacion_academica.modules.admisiones.application.services;

import com.kleverkids.formacion_academica.modules.admisiones.application.input.matricula.ConsultarMatriculaUseCase;
import com.kleverkids.formacion_academica.modules.admisiones.application.input.matricula.ListarMatriculasUseCase;
import com.kleverkids.formacion_academica.modules.admisiones.application.input.matricula.RegistrarMatriculaUseCase;
import com.kleverkids.formacion_academica.modules.admisiones.application.output.inscripcion.InscripcionRepositoryPort;
import com.kleverkids.formacion_academica.modules.admisiones.application.output.matricula.MatriculaRepositoryPort;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.matricula.CrearMatriculaDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.matricula.MatriculaDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MatriculaService implements RegistrarMatriculaUseCase,
        ConsultarMatriculaUseCase,
        ListarMatriculasUseCase {

    private final MatriculaRepositoryPort matriculaRepositoryPort;
    private final InscripcionRepositoryPort inscripcionRepositoryPort;

    public MatriculaService(MatriculaRepositoryPort matriculaRepositoryPort,
                            InscripcionRepositoryPort inscripcionRepositoryPort) {
        this.matriculaRepositoryPort = matriculaRepositoryPort;
        this.inscripcionRepositoryPort = inscripcionRepositoryPort;
    }

    @Override
    public MatriculaDto registrar(CrearMatriculaDto request) {
        inscripcionRepositoryPort.obtenerPorId(request.inscripcionId())
                .orElseThrow(() -> new IllegalArgumentException("Inscripción no encontrada"));
        return matriculaRepositoryPort.registrar(request);
    }

    @Override
    public MatriculaDto consultarPorId(UUID matriculaId) {
        return matriculaRepositoryPort.obtenerPorId(matriculaId)
                .orElseThrow(() -> new IllegalArgumentException("Matrícula no encontrada"));
    }

    @Override
    public List<MatriculaDto> listarPorEstudiante(UUID estudianteId) {
        return matriculaRepositoryPort.listarPorEstudiante(estudianteId);
    }
}
