package com.kleverkids.formacion_academica.modules.admisiones.application.services.inscripcion;

import com.kleverkids.formacion_academica.modules.admisiones.application.input.inscripcion.ConsultarInscripcionUseCase;
import com.kleverkids.formacion_academica.modules.admisiones.application.input.inscripcion.EliminarInscripcionUseCase;
import com.kleverkids.formacion_academica.modules.admisiones.application.output.inscripcion.InscripcionRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EliminarInscripcionService implements EliminarInscripcionUseCase {

    private final InscripcionRepositoryPort inscripcionRepositoryPort;
    private final ConsultarInscripcionUseCase consultarInscripcionUseCase;

    @Override
    public void eliminar(Long inscripcionId) {
        consultarInscripcionUseCase.consultarPorId(inscripcionId);
        inscripcionRepositoryPort.eliminar(inscripcionId);
    }
}
