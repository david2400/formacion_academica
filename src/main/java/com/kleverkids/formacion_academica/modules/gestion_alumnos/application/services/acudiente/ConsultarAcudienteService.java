package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.services.acudiente;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.acudiente.ConsultarAcudienteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.acudiente.AcudienteRepositoryPort;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model.Acudiente;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsultarAcudienteService implements ConsultarAcudienteUseCase {

    private final AcudienteRepositoryPort acudienteRepositoryPort;

    @Override
    public Acudiente consultarPorId(Long acudienteId) {
        return acudienteRepositoryPort.obtenerPorId(acudienteId)
                .orElseThrow(() -> new IllegalArgumentException("Acudiente no encontrado"));
    }

    @Override
    public Acudiente consultarPorTipoYNumeroDocumento(String tipoDocumento, String numeroDocumento) {
        return acudienteRepositoryPort.obtenerPorTipoYNumeroDocumento(tipoDocumento, numeroDocumento)
                .orElseThrow(() -> new IllegalArgumentException("Acudiente no encontrado"));
    }
}
