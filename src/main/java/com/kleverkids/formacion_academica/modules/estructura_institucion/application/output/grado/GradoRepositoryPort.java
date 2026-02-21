package com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.grado;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grado.ActualizarGradoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grado.CrearGradoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grado.GradoDto;

import java.util.List;
import java.util.UUID;

public interface GradoRepositoryPort {

    GradoDto guardar(CrearGradoDto request);

    GradoDto actualizar(ActualizarGradoDto request);

    boolean existePorNombreYNivel(String nombre, String nivelEducativo);

    GradoDto obtenerPorId(UUID id);

    List<GradoDto> listar();

    void eliminar(UUID id);
}
