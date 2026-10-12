package com.kleverkids.formacion_academica.modules.admisiones.application.services.inscripcion;

import com.kleverkids.formacion_academica.modules.admisiones.application.input.inscripcion.RegistrarInscripcionUseCase;
import com.kleverkids.formacion_academica.modules.admisiones.application.output.inscripcion.InscripcionRepositoryPort;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion.CrearInscripcionDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.model.Inscripcion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrarInscripcionService implements RegistrarInscripcionUseCase {

    private final InscripcionRepositoryPort inscripcionRepositoryPort;

    @Override
    public Inscripcion registrar(CrearInscripcionDto request) {
        return inscripcionRepositoryPort.registrar(request);
    }
}
