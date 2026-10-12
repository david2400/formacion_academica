package com.kleverkids.formacion_academica.modules.usuarios.application.services.profesor;

import com.kleverkids.formacion_academica.modules.usuarios.application.input.profesor.*;
import com.kleverkids.formacion_academica.modules.usuarios.application.input.profesor.ConsultarProfesorUseCase;
import com.kleverkids.formacion_academica.modules.usuarios.application.output.profesor.ProfesorConsultaPort;
import com.kleverkids.formacion_academica.modules.usuarios.application.output.profesor.ProfesorRepositoryPort;
import com.kleverkids.formacion_academica.modules.usuarios.domain.dto.profesor.*;
import com.kleverkids.formacion_academica.modules.usuarios.domain.model.Profesor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CrearProfesorService implements CrearProfesorUseCase {

    private final ProfesorRepositoryPort repository;
    private final ProfesorConsultaPort consulta;
    private final ConsultarProfesorUseCase consultarProfesorUseCase;

    @Override
    @Transactional
    public ProfesorDto crear(CrearProfesorDto dto) {
        if (!consulta.usuarioPerteneceAEmpresa(dto.getUsuarioId(), dto.getEmpresaId())) {
            throw new IllegalArgumentException("El usuario seleccionado no pertenece a esta empresa.");
        }
        if (repository.existe(dto.getUsuarioId(), dto.getEmpresaId())) {
            throw new IllegalStateException("Este usuario ya está registrado como profesor.");
        }
        Profesor datos = Profesor.builder()
                .usuarioId(dto.getUsuarioId())
                .empresaId(dto.getEmpresaId())
                .activo(true)
                .fechaVinculacion(dto.getFechaVinculacion())
                .observaciones(ProfesorSoporte.limpiar(dto.getObservaciones()))
                .build();
        Long id = repository.restaurarEliminado(datos).orElseGet(() -> repository.guardar(datos).getId());
        return consultarProfesorUseCase.consultar(id);
    }
}
