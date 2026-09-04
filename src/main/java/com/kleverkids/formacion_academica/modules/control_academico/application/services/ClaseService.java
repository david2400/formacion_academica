package com.kleverkids.formacion_academica.modules.control_academico.application.services;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.clase.ActualizarClaseUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.clase.ConsultarClaseUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.clase.CrearClaseUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.clase.CrearClasesMasivasUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.clase.EliminarClaseUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.clase.ListarClasesUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.clase.GestionarObservacionesClaseUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.clase.RegistrarSeguimientoClaseUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.FiltroClasesDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.RegistrarObservacionClaseDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.RegistrarSeguimientoClaseDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.ObservacionClase;
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
        GestionarObservacionesClaseUseCase,
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
    public List<Clase> buscar(FiltroClasesDto filtro) {
        if (filtro == null || filtro.estaVacio()) {
            return claseRepositoryPort.listarTodas();
        }
        return claseRepositoryPort.buscar(filtro);
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
    public Clase agregarObservacion(Long claseId, RegistrarObservacionClaseDto request) {
        return claseRepositoryPort.agregarObservacion(claseId, request);
    }

    @Override
    public List<ObservacionClase> listarObservaciones(Long claseId) {
        return claseRepositoryPort.listarObservaciones(claseId);
    }

    @Override
    public Clase eliminarObservacion(Long claseId, Long observacionId) {
        return claseRepositoryPort.eliminarObservacion(claseId, observacionId);
    }

    @Override
    public void eliminar(Long id) {
        claseRepositoryPort.eliminar(id);
    }
}
