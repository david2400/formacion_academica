package com.kleverkids.formacion_academica.modules.control_academico.application.input.intento;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.IntentoExamenDto;

import java.util.List;
import java.util.UUID;

public interface ListarIntentosPorEstudianteUseCase {

    List<IntentoExamenDto> listar(UUID examenId, UUID estudianteId);
}
