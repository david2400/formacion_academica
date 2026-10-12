package com.kleverkids.formacion_academica.modules.estructura_institucion.application.services.nivel_educativo;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.nivel_educativo.*;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.nivel.NivelEducativoRepositoryPort;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.nivel_academico.CrearNivelEducativoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.NivelEducativo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CrearNivelEducativoService implements CrearNivelEducativoUseCase {

    private final NivelEducativoRepositoryPort repository;
    private final NivelEducativoSoporte nivelEducativoSoporte;

    @Transactional
    public NivelEducativo crear(CrearNivelEducativoDto dto) {
        validarCodigoUnico(dto.getCodigo());
        nivelEducativoSoporte.validarNivelSuperior(dto.getNivelSuperiorId());
        
        return repository.crear(dto);
    }

    private void validarCodigoUnico(String codigo) {
        if (repository.existePorCodigo(codigo)) {
            throw new IllegalArgumentException("Ya existe un nivel educativo con el código: " + codigo);
        }
    }
}
