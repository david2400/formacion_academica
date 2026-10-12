package com.kleverkids.formacion_academica.modules.control_academico.application.services.clase;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.clase.GestionarObservacionesClaseUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.clase.ClaseRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.RegistrarObservacionClaseDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.Clase;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.ObservacionClase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GestionarObservacionesClaseService implements GestionarObservacionesClaseUseCase {

    private final ClaseRepositoryPort claseRepositoryPort;

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
}
