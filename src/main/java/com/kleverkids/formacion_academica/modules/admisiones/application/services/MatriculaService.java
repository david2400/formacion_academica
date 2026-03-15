package com.kleverkids.formacion_academica.modules.admisiones.application.services;

import com.kleverkids.formacion_academica.modules.admisiones.application.input.matricula.ConsultarMatriculaUseCase;
import com.kleverkids.formacion_academica.modules.admisiones.application.input.matricula.EliminarMatriculaUseCase;
import com.kleverkids.formacion_academica.modules.admisiones.application.input.matricula.ListarMatriculasUseCase;
import com.kleverkids.formacion_academica.modules.admisiones.application.input.matricula.RegistrarMatriculaUseCase;
import com.kleverkids.formacion_academica.modules.admisiones.application.output.inscripcion.InscripcionRepositoryPort;
import com.kleverkids.formacion_academica.modules.admisiones.application.output.matricula.MatriculaRepositoryPort;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.matricula.CrearMatriculaDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.matricula.MatriculaDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatriculaService implements RegistrarMatriculaUseCase,
        ConsultarMatriculaUseCase,
        ListarMatriculasUseCase,
        EliminarMatriculaUseCase {

    private final MatriculaRepositoryPort matriculaRepositoryPort;
    private final InscripcionRepositoryPort inscripcionRepositoryPort;

    public MatriculaService(MatriculaRepositoryPort matriculaRepositoryPort,
                            InscripcionRepositoryPort inscripcionRepositoryPort) {
        this.matriculaRepositoryPort = matriculaRepositoryPort;
        this.inscripcionRepositoryPort = inscripcionRepositoryPort;
    }

    @Override
    public MatriculaDto registrar(CrearMatriculaDto request) {

        return matriculaRepositoryPort.registrar(request);
    }

    @Override
    public MatriculaDto consultarPorId(Long matriculaId) {
        return matriculaRepositoryPort.obtenerPorId(matriculaId)
                .orElseThrow(() -> new IllegalArgumentException("Matrícula no encontrada"));
    }

    @Override
    public List<MatriculaDto> listarPorEstudiante(Long estudianteId) {
        return matriculaRepositoryPort.listarPorEstudiante(estudianteId);
    }

    @Override
    public void eliminar(Long matriculaId) {
        consultarPorId(matriculaId);
        matriculaRepositoryPort.eliminar(matriculaId);
    }
}
