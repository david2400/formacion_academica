package com.kleverkids.formacion_academica.modules.control_academico.application.output.pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.ActualizarPreguntaBancoDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.CrearPreguntaBancoDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.PreguntaBancoDto;

import java.util.List;

public interface PreguntaBancoRepositoryPort {

    PreguntaBancoDto guardar(CrearPreguntaBancoDto request);

    PreguntaBancoDto actualizar(ActualizarPreguntaBancoDto request);

    List<PreguntaBancoDto> listarPorTematica(Long tematicaId);

    PreguntaBancoDto obtenerPorId(Long preguntaId);

    void eliminar(Long preguntaId);
}
