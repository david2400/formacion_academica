package com.kleverkids.formacion_academica.modules.estructura_institucion.application.services.grupo;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.grupo.ActualizarGrupoUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.grupo.GrupoRepositoryPort;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grupo.ActualizarGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Grupo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActualizarGrupoService implements ActualizarGrupoUseCase {

     private final GrupoRepositoryPort grupoRepositoryPort;

     @Override
     public Grupo actualizar(ActualizarGrupoDto request) {
         return grupoRepositoryPort.actualizar(request);
     }
}
