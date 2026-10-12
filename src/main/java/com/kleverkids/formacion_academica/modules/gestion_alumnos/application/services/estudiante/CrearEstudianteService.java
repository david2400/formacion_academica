package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.services.estudiante;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante.CrearEstudianteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.estudiante.EstudianteRepositoryPort;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante.CrearEstudianteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model.Estudiante;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CrearEstudianteService implements CrearEstudianteUseCase {

    private final EstudianteRepositoryPort repositoryPort;
    private final EstudianteSoporte estudianteSoporte;

    @Override
    public Estudiante crear(CrearEstudianteDto request) {
        validarDocumentoUnico(request.getTipoDocumento(), request.getNumeroDocumento());
        // validarPersonaEmpresa(...) deshabilitada temporalmente: depende de la
        // vista vw_account_usuario de access_control (ver CuentaUsuarioPort),
        // que todavía no está creada en todos los ambientes. Pendiente decidir
        // cómo resolver esa dependencia antes de reactivarla.
        estudianteSoporte.aplicarPassword(request);
        return repositoryPort.guardar(request);
    }

    private void validarDocumentoUnico(String tipoDocumento, String numeroDocumento) {
        if (repositoryPort.existePorDocumento(tipoDocumento, numeroDocumento)) {
            throw new IllegalArgumentException("Ya existe un estudiante con el documento indicado");
        }
    }
}
