package com.kleverkids.formacion_academica.modules.admisiones.application.services;

import com.kleverkids.formacion_academica.modules.admisiones.application.input.matricula.ActualizarMatriculaUseCase;
import com.kleverkids.formacion_academica.modules.admisiones.application.input.matricula.ConsultarMatriculaUseCase;
import com.kleverkids.formacion_academica.modules.admisiones.application.input.matricula.EliminarMatriculaUseCase;
import com.kleverkids.formacion_academica.modules.admisiones.application.input.matricula.ListarMatriculasUseCase;
import com.kleverkids.formacion_academica.modules.admisiones.application.input.matricula.RegistrarMatriculaUseCase;
import com.kleverkids.formacion_academica.modules.admisiones.application.output.inscripcion.InscripcionRepositoryPort;
import com.kleverkids.formacion_academica.modules.admisiones.application.output.matricula.MatriculaRepositoryPort;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.matricula.ActualizarMatriculaDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.matricula.CrearMatriculaDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.model.Matricula;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MatriculaService implements ActualizarMatriculaUseCase,
        RegistrarMatriculaUseCase,
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
    public Matricula registrar(CrearMatriculaDto request) {
        return matriculaRepositoryPort.registrar(request);
    }

    @Override
    public Matricula actualizar(ActualizarMatriculaDto request) {
        // TODO: Implementar la lógica de actualización de matrícula
        // Por ahora, retornamos un domain model básico para que la aplicación inicie
        return new Matricula(
            request.getMatriculaId(),
            request.getInscripcionId(),
            request.getEstudianteId(),
            request.getGradoId(),
            request.getGrupoId(),
            request.getFechaMatricula(),
            request.getRenovacion(),
          //  request.getEstadoId(),
            request.getObservaciones(),
            true, // eliminado
            null, // usrCrea
            null, // usrMod
            null, // createdAt
            null  // updatedAt
        );
    }

    @Override
    public Optional<Matricula> consultarPorId(Long matriculaId) {
        return matriculaRepositoryPort.obtenerPorId(matriculaId);
    }

    @Override
    public List<Matricula> listarPorEstudiante(Long estudianteId) {
        return matriculaRepositoryPort.listarPorEstudiante(estudianteId);
    }

    @Override
    public void eliminar(Long matriculaId) {
        consultarPorId(matriculaId);
        matriculaRepositoryPort.eliminar(matriculaId);
    }
}
