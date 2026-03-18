package com.kleverkids.formacion_academica.modules.control_academico.application.output.pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.ActualizarPreguntaBancoDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.CrearPreguntaBancoDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.pregunta.PreguntaBanco;

import java.util.List;

public interface PreguntaBancoRepositoryPort {

    PreguntaBanco guardar(CrearPreguntaBancoDto request);

    PreguntaBanco actualizar(ActualizarPreguntaBancoDto request);

    List<PreguntaBanco> listarPorTematica(Long tematicaId);

    PreguntaBanco obtenerPorId(Long preguntaId);

    void eliminar(Long preguntaId);
}
