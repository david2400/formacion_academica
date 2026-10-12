package com.kleverkids.formacion_academica.modules.estructura_institucion.application.services.grupo;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.grupo.ConsultarGrupoUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.grupo.GrupoRepositoryPort;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Grupo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsultarGrupoService implements ConsultarGrupoUseCase {

     private final GrupoRepositoryPort grupoRepositoryPort;

     @Override
     public Grupo consultarPorId(Long grupoId) {
         return grupoRepositoryPort.obtenerPorId(grupoId);
     }
}
