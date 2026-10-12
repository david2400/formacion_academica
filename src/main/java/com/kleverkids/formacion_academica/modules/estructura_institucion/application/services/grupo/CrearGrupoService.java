package com.kleverkids.formacion_academica.modules.estructura_institucion.application.services.grupo;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.grupo.CrearGrupoUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.grupo.GrupoRepositoryPort;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grupo.CrearGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Grupo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CrearGrupoService implements CrearGrupoUseCase {

     private final GrupoRepositoryPort grupoRepositoryPort;

     @Override
     public Grupo crear(CrearGrupoDto request) {
 //        validarCodigoUnico(request.codigo());
         return grupoRepositoryPort.guardar(request);
     }

     private void validarCodigoUnico(String codigo) {
         if (grupoRepositoryPort.existePorCodigo(codigo)) {
             throw new IllegalArgumentException("Ya existe un grupo con código " + codigo);
         }
     }
}
