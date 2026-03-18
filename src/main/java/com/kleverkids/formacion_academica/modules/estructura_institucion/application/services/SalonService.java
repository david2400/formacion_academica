package com.kleverkids.formacion_academica.modules.estructura_institucion.application.services;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.salon.ActualizarSalonUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.salon.ConsultarSalonUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.salon.CrearSalonUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.salon.EliminarSalonUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.salon.ListarSalonesUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.salon.SalonRepositoryPort;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.salon.ActualizarSalonDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.salon.CrearSalonDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Salon;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalonService implements CrearSalonUseCase, ActualizarSalonUseCase, ListarSalonesUseCase,
        ConsultarSalonUseCase, EliminarSalonUseCase {

    private final SalonRepositoryPort salonRepositoryPort;

    public SalonService(SalonRepositoryPort salonRepositoryPort) {
        this.salonRepositoryPort = salonRepositoryPort;
    }

    @Override
    public Salon crear(CrearSalonDto request) {
        validarCodigoDisponible(request.codigo(), null);
        return salonRepositoryPort.guardar(request);
    }

    @Override
    public Salon actualizar(ActualizarSalonDto request) {
        validarExistencia(request.id());
        validarCodigoDisponible(request.codigo(), request.id());
        return salonRepositoryPort.actualizar(request);
    }

    @Override
    public List<Salon> listar() {
        return salonRepositoryPort.listar();
    }

    @Override
    public Salon consultarPorId(Long salonId) {
        return salonRepositoryPort.obtenerPorId(salonId);
    }

    @Override
    public void eliminar(Long salonId) {
        validarExistencia(salonId);
        salonRepositoryPort.eliminar(salonId);
    }

    private void validarCodigoDisponible(String codigo, Long salonId) {
        if (codigo == null || codigo.isBlank()) {
            return;
        }
        if (!salonRepositoryPort.existePorCodigo(codigo)) {
            return;
        }
        if (salonId == null) {
            throw new IllegalArgumentException("Ya existe un salón con el código " + codigo);
        }
        Salon actual = salonRepositoryPort.obtenerPorId(salonId);
        if (!actual.codigo().equalsIgnoreCase(codigo)) {
            throw new IllegalArgumentException("Ya existe un salón con el código " + codigo);
        }
    }

    private void validarExistencia(Long salonId) {
        if (!salonRepositoryPort.existePorId(salonId)) {
            throw new IllegalArgumentException("El salón no existe");
        }
    }
}
