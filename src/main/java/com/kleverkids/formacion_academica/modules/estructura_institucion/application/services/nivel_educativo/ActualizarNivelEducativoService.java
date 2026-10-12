package com.kleverkids.formacion_academica.modules.estructura_institucion.application.services.nivel_educativo;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.nivel_educativo.*;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.nivel.NivelEducativoRepositoryPort;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.nivel_academico.ActualizarNivelEducativoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.NivelEducativo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ActualizarNivelEducativoService implements ActualizarNivelEducativoUseCase {

    private final NivelEducativoRepositoryPort repository;
    private final NivelEducativoSoporte nivelEducativoSoporte;

    @Transactional
    public Optional<NivelEducativo> actualizar(Long id, ActualizarNivelEducativoDto dto) {
        if (dto.getNivelSuperiorId() != null) {
            nivelEducativoSoporte.validarNivelSuperior(dto.getNivelSuperiorId());
        }
        
        return repository.actualizar(id, dto);
    }
}
