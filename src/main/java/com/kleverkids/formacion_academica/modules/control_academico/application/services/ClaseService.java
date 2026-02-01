package com.kleverkids.formacion_academica.modules.control_academico.application.services;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.clase.CrearClaseUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.clase.CrearClasesMasivasUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.clase.ClaseRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.ClaseDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.CrearClaseDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.CrearClasesMasivasDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.ResultadoClasesMasivasDto;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ClaseService implements CrearClaseUseCase, CrearClasesMasivasUseCase {

    private final ClaseRepositoryPort claseRepositoryPort;


    @Override
    public ClaseDto crearClase(CrearClaseDto request) {
        return claseRepositoryPort.guardar(request);
    }

    @Override
    public ResultadoClasesMasivasDto crearClases(CrearClasesMasivasDto request) {
        List<ClaseDto> creadas = claseRepositoryPort.guardarTodas(request.clases());
        return new ResultadoClasesMasivasDto(request.clases().size(), creadas.size(), creadas);
    }

    private void validarCodigoUnico(CrearClaseDto request) {
        validarCodigoUnico(request.codigo());
    }

    private void validarCodigoUnico(String codigo) {
        if (claseRepositoryPort.existePorCodigo(codigo)) {
            throw new IllegalArgumentException("Ya existe una clase con código " + codigo);
        }
    }
}
