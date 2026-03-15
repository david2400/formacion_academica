package com.kleverkids.formacion_academica.modules.control_academico.application.input.intento;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.IntentoExamenDto;

import java.util.List;


public interface ListarIntentosPorEstudianteUseCase {

    List<IntentoExamenDto> listar(Long examenId, Long estudianteId);
}
