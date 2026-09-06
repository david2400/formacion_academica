package com.kleverkids.formacion_academica.modules.estados.application.services;

import com.kleverkids.formacion_academica.modules.estados.application.input.catalogo.AdministrarCatalogoEstadosUseCase;
import com.kleverkids.formacion_academica.modules.estados.application.output.catalogo.CatalogoEstadosAdminPort;
import com.kleverkids.formacion_academica.modules.estados.domain.dto.catalogo.ActualizarParametrizacionDto;
import com.kleverkids.formacion_academica.modules.estados.domain.dto.catalogo.CrearEstadoDto;
import com.kleverkids.formacion_academica.modules.estados.domain.dto.catalogo.HabilitarEstadoDto;
import com.kleverkids.formacion_academica.modules.estados.domain.dto.catalogo.RegistrarContextoDto;
import com.kleverkids.formacion_academica.modules.estados.domain.model.ContextoEstado;
import com.kleverkids.formacion_academica.modules.estados.domain.model.Estado;
import com.kleverkids.formacion_academica.modules.estados.domain.model.EstadoContexto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CatalogoEstadosAdminService implements AdministrarCatalogoEstadosUseCase {

    private final CatalogoEstadosAdminPort adminPort;

    @Override
    public List<Estado> listarEstados() {
        return adminPort.listarEstados();
    }

    @Override
    public Optional<Estado> consultarEstado(Long id) {
        return adminPort.buscarEstadoPorId(id);
    }

    @Override
    public Optional<Estado> consultarEstadoPorCodigo(String codigo) {
        return adminPort.buscarEstadoPorCodigo(codigo);
    }

    @Override
    public Estado crearEstado(CrearEstadoDto request) {
        return adminPort.crearEstado(request);
    }

    @Override
    public Estado actualizarEstado(Long id, CrearEstadoDto request) {
        return adminPort.actualizarEstado(id, request);
    }

    @Override
    public void eliminarEstado(Long id) {
        adminPort.eliminarEstado(id);
    }

    @Override
    public List<ContextoEstado> listarContextos() {
        return adminPort.listarContextos();
    }

    @Override
    public ContextoEstado registrarContexto(RegistrarContextoDto request) {
        return adminPort.registrarContexto(request);
    }

    @Override
    public EstadoContexto habilitarEstado(String codigoContexto, HabilitarEstadoDto request) {
        return adminPort.habilitarEstado(codigoContexto, request);
    }

    @Override
    public EstadoContexto actualizarParametrizacion(Long id, ActualizarParametrizacionDto request) {
        return adminPort.actualizarParametrizacion(id, request);
    }

    @Override
    public void deshabilitarEstado(Long id) {
        adminPort.deshabilitarEstado(id);
    }
}
