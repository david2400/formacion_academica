package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.services.acudiente;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.acudiente.ActualizarAcudienteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.acudiente.ConsultarAcudienteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.acudiente.AcudienteRepositoryPort;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.acudiente.ActualizarAcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model.Acudiente;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActualizarAcudienteService implements ActualizarAcudienteUseCase {

    private final AcudienteRepositoryPort acudienteRepositoryPort;
    private final AcudienteSoporte acudienteSoporte;
    private final ConsultarAcudienteUseCase consultarAcudienteUseCase;

    @Override
    public Acudiente actualizar(ActualizarAcudienteDto request) {
        Acudiente existente = consultarAcudienteUseCase.consultarPorId(request.getId());
        // validarPersonaEmpresa(...) deshabilitada temporalmente: ver comentario
        // en crear().
        acudienteSoporte.validarDocumentoUnico(request.getTipoDocumento(), request.getNumeroDocumento(), request.getId());
        acudienteSoporte.aplicarPassword(request);
        return acudienteRepositoryPort.actualizar(request);
    }
}
