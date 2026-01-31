package com.kleverkids.formacion_academica.modules.control_academico.application.output.clase;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.ClaseDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.CrearClaseDto;

import java.util.List;
import java.util.UUID;

public interface ClaseRepositoryPort {

    ClaseDto guardar(CrearClaseDto clase);

    List<ClaseDto> guardarTodas(List<CrearClaseDto> clases);

    boolean existePorCodigo(String codigo);

    ClaseDto obtenerPorId(UUID id);
}
