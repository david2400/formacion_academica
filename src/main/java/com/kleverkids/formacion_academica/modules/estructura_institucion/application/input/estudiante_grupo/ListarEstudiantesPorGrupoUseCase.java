package com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.estudiante_grupo;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.estudiante_grupo.EstudianteGrupoDto;

import java.util.List;
import java.util.UUID;

public interface ListarEstudiantesPorGrupoUseCase {

    List<EstudianteGrupoDto> listar(UUID grupoId);
}
