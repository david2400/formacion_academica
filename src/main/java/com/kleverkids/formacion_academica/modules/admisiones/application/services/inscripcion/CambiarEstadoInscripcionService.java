package com.kleverkids.formacion_academica.modules.admisiones.application.services.inscripcion;

import com.kleverkids.formacion_academica.modules.admisiones.application.input.inscripcion.CambiarEstadoInscripcionUseCase;
import com.kleverkids.formacion_academica.modules.admisiones.application.output.inscripcion.InscripcionRepositoryPort;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion.ActualizarEstadoInscripcionDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.model.Inscripcion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CambiarEstadoInscripcionService implements CambiarEstadoInscripcionUseCase {

    private final InscripcionRepositoryPort inscripcionRepositoryPort;

    @Override
    public Inscripcion cambiarEstado(ActualizarEstadoInscripcionDto request) {
        return inscripcionRepositoryPort.actualizarEstado(request);
    }
}
