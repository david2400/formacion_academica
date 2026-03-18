package com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.grado;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grado.ActualizarGradoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grado.CrearGradoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Grado;

import java.util.List;

public interface GradoRepositoryPort {

    Grado guardar(CrearGradoDto request);

    Grado actualizar(ActualizarGradoDto request);

    boolean existePorNombreYNivel(String nombre, String nivelEducativo);

    Grado obtenerPorId(Long id);

    List<Grado> listar();

    void eliminar(Long id);
}
