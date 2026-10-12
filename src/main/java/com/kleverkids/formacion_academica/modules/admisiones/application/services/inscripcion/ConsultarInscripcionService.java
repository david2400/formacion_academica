package com.kleverkids.formacion_academica.modules.admisiones.application.services.inscripcion;

import com.kleverkids.formacion_academica.modules.admisiones.application.input.inscripcion.ConsultarInscripcionUseCase;
import com.kleverkids.formacion_academica.modules.admisiones.application.output.inscripcion.InscripcionRepositoryPort;
import com.kleverkids.formacion_academica.modules.admisiones.domain.model.Inscripcion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsultarInscripcionService implements ConsultarInscripcionUseCase {

    private final InscripcionRepositoryPort inscripcionRepositoryPort;

    @Override
    public Inscripcion consultarPorId(Long inscripcionId) {
        return inscripcionRepositoryPort.obtenerPorId(inscripcionId)
                .orElseThrow(() -> new IllegalArgumentException("Inscripción no encontrada"));
    }
}
