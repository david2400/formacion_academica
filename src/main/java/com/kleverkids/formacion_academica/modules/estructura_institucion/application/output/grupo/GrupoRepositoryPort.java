package com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.grupo;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grupo.ActualizarGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grupo.CrearGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grupo.GrupoDto;

import java.util.List;

public interface GrupoRepositoryPort {

    GrupoDto guardar(CrearGrupoDto request);

    GrupoDto actualizar(ActualizarGrupoDto request);

    boolean existePorCodigo(String codigo);

    GrupoDto obtenerPorId(Long id);

    List<GrupoDto> listar();

    void eliminar(Long id);
}
