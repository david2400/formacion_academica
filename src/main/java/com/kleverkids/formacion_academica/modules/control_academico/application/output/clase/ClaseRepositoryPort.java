package com.kleverkids.formacion_academica.modules.control_academico.application.output.clase;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.Clase;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.CrearClaseDto;

import java.util.List;


public interface ClaseRepositoryPort {

    Clase guardar(CrearClaseDto clase);

    List<Clase> guardarTodas(List<CrearClaseDto> clases);

    Clase getClaseById(Long id);
}
