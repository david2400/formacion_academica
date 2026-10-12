package com.kleverkids.formacion_academica.modules.estructura_institucion.application.services.nivel_educativo;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.nivel_educativo.*;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.nivel.NivelEducativoRepositoryPort;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.NivelEducativo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ListarNivelesEducativosService implements ListarNivelesEducativosUseCase {

    private final NivelEducativoRepositoryPort repository;

    @Transactional(readOnly = true)
    public List<NivelEducativo> listarTodos() {
        return repository.listarTodos();
    }

//    @Transactional(readOnly = true)
//    public List<NivelEducativo> listarPorCategoria(String categoria) {
//        return repository.listarPorCategoria(categoria);
//    }

    @Transactional(readOnly = true)
    public List<NivelEducativo> listarPorNivelSuperior(Long nivelSuperiorId) {
        return repository.listarPorNivelSuperior(nivelSuperiorId);
    }

    @Transactional(readOnly = true)
    public List<NivelEducativo> listarActivos() {
        return repository.listarActivos();
    }

//    @Transactional(readOnly = true)
//    public NivelEducativoEstadisticasDto obtenerEstadisticas() {
//        return repository.obtenerEstadisticas();
//    }

//    @Transactional(readOnly = true)
//    public List<NivelEducativo> listarPorCategoriaActivos(String categoria) {
//        return repository.listarPorCategoriaActivos(categoria);
//    }

    @Transactional(readOnly = true)
    public List<NivelEducativo> listarNivelesPrincipales() {
        return repository.listarNivelesPrincipales();
    }
}
