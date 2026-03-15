package com.kleverkids.formacion_academica.modules.control_academico.application.output.clase;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.ClaseDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.CrearClaseDto;

import java.util.List;


public interface ClaseRepositoryPort {

    ClaseDto guardar(CrearClaseDto clase);

    List<ClaseDto> guardarTodas(List<CrearClaseDto> clases);

    ClaseDto getClaseById(Long id);
}
