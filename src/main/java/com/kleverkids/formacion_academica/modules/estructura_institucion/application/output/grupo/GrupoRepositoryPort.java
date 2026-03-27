package com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.grupo;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grupo.ActualizarGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grupo.CrearGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Grupo;

import java.util.List;

public interface GrupoRepositoryPort {

    Grupo guardar(CrearGrupoDto request);

    Grupo actualizar(ActualizarGrupoDto request);

    boolean existePorCodigo(String codigo);

    Grupo obtenerPorId(Long id);

    List<Grupo> listar();

    void eliminar(Long id);
}
