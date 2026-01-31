package com.kleverkids.formacion_academica.modules.admisiones.application.input.matricula;

import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.matricula.MatriculaDto;

import java.util.UUID;

public interface ConsultarMatriculaUseCase {

    MatriculaDto consultarPorId(UUID matriculaId);
}
