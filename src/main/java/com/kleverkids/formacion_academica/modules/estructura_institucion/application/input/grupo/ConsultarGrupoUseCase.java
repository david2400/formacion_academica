package com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.grupo;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grupo.GrupoDto;

import java.util.UUID;

public interface ConsultarGrupoUseCase {

    GrupoDto consultarPorId(UUID grupoId);
}
