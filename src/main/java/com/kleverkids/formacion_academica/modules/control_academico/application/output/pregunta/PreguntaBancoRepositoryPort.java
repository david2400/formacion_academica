package com.kleverkids.formacion_academica.modules.control_academico.application.output.pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.ActualizarPreguntaBancoDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.CrearPreguntaBancoDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.PreguntaBancoDto;

import java.util.List;
import java.util.UUID;

public interface PreguntaBancoRepositoryPort {

    PreguntaBancoDto guardar(CrearPreguntaBancoDto request);

    PreguntaBancoDto actualizar(ActualizarPreguntaBancoDto request);

    List<PreguntaBancoDto> listarPorTematica(UUID tematicaId);

    PreguntaBancoDto obtenerPorId(UUID preguntaId);

    void eliminar(UUID preguntaId);
}
