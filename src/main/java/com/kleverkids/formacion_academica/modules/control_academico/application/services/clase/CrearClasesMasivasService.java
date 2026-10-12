package com.kleverkids.formacion_academica.modules.control_academico.application.services.clase;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.clase.CrearClasesMasivasUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.clase.ClaseRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.CrearClasesMasivasDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.ResultadoClasesMasivasDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.Clase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CrearClasesMasivasService implements CrearClasesMasivasUseCase {

    private final ClaseRepositoryPort claseRepositoryPort;

    @Override
    public ResultadoClasesMasivasDto crearClases(CrearClasesMasivasDto request) {
        List<Clase> creadas = claseRepositoryPort.guardarTodas(request.getClases());
        return new ResultadoClasesMasivasDto(request.getClases().size(), creadas.size(), creadas);
    }
}
