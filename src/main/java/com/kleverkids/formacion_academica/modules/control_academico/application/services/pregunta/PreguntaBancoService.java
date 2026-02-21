package com.kleverkids.formacion_academica.modules.control_academico.application.services.pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.pregunta.ActualizarPreguntaBancoUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.pregunta.ConsultarPreguntaBancoUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.pregunta.CrearPreguntaBancoUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.pregunta.EliminarPreguntaBancoUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.pregunta.ListarPreguntasPorTematicaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.pregunta.PreguntaBancoRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.ActualizarPreguntaBancoDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.CrearPreguntaBancoDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.PreguntaBancoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PreguntaBancoService implements CrearPreguntaBancoUseCase,
        ActualizarPreguntaBancoUseCase,
        ListarPreguntasPorTematicaUseCase,
        ConsultarPreguntaBancoUseCase,
        EliminarPreguntaBancoUseCase {

    private final PreguntaBancoRepositoryPort repositoryPort;

    @Override
    public PreguntaBancoDto crear(CrearPreguntaBancoDto request) {
        return repositoryPort.guardar(request);
    }

    @Override
    public PreguntaBancoDto actualizar(ActualizarPreguntaBancoDto request) {
        return repositoryPort.actualizar(request);
    }

    @Override
    public List<PreguntaBancoDto> listar(UUID tematicaId) {
        return repositoryPort.listarPorTematica(tematicaId);
    }

    @Override
    public PreguntaBancoDto consultarPorId(UUID preguntaId) {
        return repositoryPort.obtenerPorId(preguntaId);
    }

    @Override
    public void eliminar(UUID preguntaId) {
        repositoryPort.obtenerPorId(preguntaId);
        repositoryPort.eliminar(preguntaId);
    }
}
