package com.kleverkids.formacion_academica.modules.estructura_institucion.application.services;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.sede.*;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.sede.SedeRepositoryPort;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.sedes.ActualizarSedeDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.sedes.CrearSedeDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Sede;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SedeService implements ActualizarSedeUseCase,
        CrearSedeUseCase,
        ConsultarSedeUseCase,
        ListarSedesUseCase,
        EliminarSedeUseCase {

    private final SedeRepositoryPort sedeRepositoryPort;

    public SedeService(SedeRepositoryPort sedeRepositoryPort) {
        this.sedeRepositoryPort = sedeRepositoryPort;
    }

    @Override
    public Sede crear(CrearSedeDto request) {
        // Validar que no exista una sede con el mismo código
//        if (sedeRepositoryPort.existePorCodigo(request.getCodigo())) {
//            throw new IllegalArgumentException("Ya existe una sede con el código: " + request.getCodigo());
//        }
        return sedeRepositoryPort.registrar(request);
    }

    @Override
    public Sede actualizar(ActualizarSedeDto request) {
        // Validar que la sede exista
        Optional<Sede> sedeExistente = sedeRepositoryPort.obtenerPorId(request.getId());
        if (sedeExistente.isEmpty()) {
            throw new IllegalArgumentException("No se encontró la sede con ID: " + request.getId());
        }
        
        return sedeRepositoryPort.actualizar(request.getId(), request)
                .orElseThrow(() -> new IllegalArgumentException("No se pudo actualizar la sede"));
    }

    @Override
    public Optional<Sede> buscarPorId(Long id) {
        return sedeRepositoryPort.obtenerPorId(id);
    }

//    @Override
//    public Optional<Sede> buscarPorCodigo(String codigo) {
//        return sedeRepositoryPort.obtenerPorCodigo(codigo);
//    }

    @Override
    public List<Sede> listarTodas() {
        return sedeRepositoryPort.listarTodas();
    }

    @Override
    public List<Sede> listarActivas() {
        return sedeRepositoryPort.listarActivas();
    }

//    @Override
//    public List<Sede> listarPorCiudad(String ciudad) {
//        return sedeRepositoryPort.listarPorCiudad(ciudad);
//    }

    @Override
    public boolean eliminar(Long id) {
        // Validar que la sede exista
        Optional<Sede> sedeExistente = sedeRepositoryPort.obtenerPorId(id);
        if (sedeExistente.isEmpty()) {
            throw new IllegalArgumentException("No se encontró la sede con ID: " + id);
        }
        
        return sedeRepositoryPort.eliminar(id);
    }
}
