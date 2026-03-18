package com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.salon;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.salon.ActualizarSalonDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.salon.CrearSalonDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Salon;

import java.util.List;

public interface SalonRepositoryPort {

    Salon guardar(CrearSalonDto request);

    Salon actualizar(ActualizarSalonDto request);

    List<Salon> listar();

    Salon obtenerPorId(Long salonId);

    void eliminar(Long salonId);

    boolean existePorId(Long salonId);

    boolean existePorCodigo(String codigo);
}
