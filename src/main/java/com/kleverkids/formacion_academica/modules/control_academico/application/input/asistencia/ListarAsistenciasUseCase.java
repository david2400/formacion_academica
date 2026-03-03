package com.kleverkids.formacion_academica.modules.control_academico.application.input.asistencia;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia.AsistenciaDto;
import java.util.List;

public interface ListarAsistenciasUseCase {
    List<AsistenciaDto> listar();
}
