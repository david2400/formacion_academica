package com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.sede;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Sede;
import java.util.Optional;

public interface ConsultarSedeUseCase {
    Optional<Sede> buscarPorId(Long id);
//    Optional<Sede> buscarPorCodigo(String codigo);
}
