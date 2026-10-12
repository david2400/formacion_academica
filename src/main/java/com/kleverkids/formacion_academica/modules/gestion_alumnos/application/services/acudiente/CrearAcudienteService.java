package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.services.acudiente;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.acudiente.CrearAcudienteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.acudiente.AcudienteRepositoryPort;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.acudiente.CrearAcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model.Acudiente;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CrearAcudienteService implements CrearAcudienteUseCase {

    private final AcudienteRepositoryPort acudienteRepositoryPort;
    private final AcudienteSoporte acudienteSoporte;

    @Override
    public Acudiente crear(CrearAcudienteDto request) {
        // validarPersonaEmpresa(...) deshabilitada temporalmente: depende de la
        // vista vw_account_usuario de access_control (ver CuentaUsuarioPort),
        // que todavía no está creada en todos los ambientes. Pendiente decidir
        // cómo resolver esa dependencia antes de reactivarla.
        acudienteSoporte.validarDocumentoUnico(request.getTipoDocumento(), request.getNumeroDocumento(), null);
        acudienteSoporte.aplicarPassword(request);
        return acudienteRepositoryPort.guardar(request);
    }
}
