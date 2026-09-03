package com.kleverkids.formacion_academica.modules.control_academico.application.services;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.clase.ActualizarClaseUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.clase.ConsultarClaseUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.clase.CrearClaseUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.clase.CrearClasesMasivasUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.clase.EliminarClaseUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.clase.ListarClasesUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.clase.RegistrarSeguimientoClaseUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.RegistrarSeguimientoClaseDto;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.clase.ClaseRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.Clase;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.ActualizarClaseDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.CrearClaseDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.CrearClasesMasivasDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.ResultadoClasesMasivasDto;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ClaseService implements CrearClaseUseCase,
        CrearClasesMasivasUseCase,
        ConsultarClaseUseCase,
        ListarClasesUseCase,
        ActualizarClaseUseCase,
        RegistrarSeguimientoClaseUseCase,
        EliminarClaseUseCase {

    private final ClaseRepositoryPort claseRepositoryPort;

    @Override
    public Clase crearClase(CrearClaseDto request) {
        return claseRepositoryPort.guardar(request);
    }

    @Override
    public ResultadoClasesMasivasDto crearClases(CrearClasesMasivasDto request) {
        List<Clase> creadas = claseRepositoryPort.guardarTodas(request.getClases());
        return new ResultadoClasesMasivasDto(request.getClases().size(), creadas.size(), creadas);
    }

    @Override
    public Optional<Clase> consultarPorId(Long id) {
        return claseRepositoryPort.obtenerPorId(id);
    }

    @Override
    public List<Clase> listarTodas() {
        return claseRepositoryPort.listarTodas();
    }

    @Override
    public Clase actualizar(ActualizarClaseDto request) {
        return claseRepositoryPort.actualizar(request);
    }

    @Override
    public Clase registrarSeguimiento(Long id, RegistrarSeguimientoClaseDto request) {
        return claseRepositoryPort.registrarSeguimiento(id, request);
    }

    @Override
    public void eliminar(Long id) {
        claseRepositoryPort.eliminar(id);
    }
}
