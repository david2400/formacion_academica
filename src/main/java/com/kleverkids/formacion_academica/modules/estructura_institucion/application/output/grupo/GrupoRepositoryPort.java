package com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.grupo;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grupo.ActualizarGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grupo.CrearGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grupo.GrupoDto;

import java.util.List;
import java.util.UUID;

public interface GrupoRepositoryPort {

    GrupoDto guardar(CrearGrupoDto request);

    GrupoDto actualizar(ActualizarGrupoDto request);

    boolean existePorCodigo(String codigo);

    GrupoDto obtenerPorId(UUID id);

    List<GrupoDto> listar();

    void eliminar(UUID id);
}
