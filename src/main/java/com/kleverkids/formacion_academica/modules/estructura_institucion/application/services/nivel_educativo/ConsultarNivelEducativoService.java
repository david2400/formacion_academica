package com.kleverkids.formacion_academica.modules.estructura_institucion.application.services.nivel_educativo;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.nivel_educativo.*;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.nivel.NivelEducativoRepositoryPort;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.NivelEducativo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ConsultarNivelEducativoService implements ConsultarNivelEducativoUseCase {

    private final NivelEducativoRepositoryPort repository;

    @Transactional(readOnly = true)
    public Optional<NivelEducativo> buscarPorId(Long id) {
        return repository.buscarPorId(id);
    }

    @Transactional(readOnly = true)
    public Optional<NivelEducativo> buscarPorCodigo(String codigo) {
        return repository.buscarPorCodigo(codigo);
    }
}
