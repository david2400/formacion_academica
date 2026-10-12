package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.services.acudiente;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.acudiente.ConsultarAcudienteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.acudiente.EliminarAcudienteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.acudiente.AcudienteRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EliminarAcudienteService implements EliminarAcudienteUseCase {

    private final AcudienteRepositoryPort acudienteRepositoryPort;
    private final ConsultarAcudienteUseCase consultarAcudienteUseCase;

    @Override
    public void eliminar(Long acudienteId) {
        consultarAcudienteUseCase.consultarPorId(acudienteId);
        acudienteRepositoryPort.eliminar(acudienteId);
    }
}
