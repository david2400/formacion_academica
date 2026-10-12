package com.kleverkids.formacion_academica.modules.estructura_institucion.application.services.nivel_educativo;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.nivel_educativo.*;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.nivel.NivelEducativoRepositoryPort;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.NivelEducativo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class EliminarNivelEducativoService implements EliminarNivelEducativoUseCase {

    private final NivelEducativoRepositoryPort repository;

    @Transactional
    public boolean eliminar(Long id) {
        Optional<NivelEducativo> nivel = repository.buscarPorId(id);
        if (nivel.isPresent()) {
            // Verificar si tiene niveles dependientes
            List<NivelEducativo> dependientes = repository.listarPorNivelSuperior(id);
            if (!dependientes.isEmpty()) {
                throw new IllegalStateException("No se puede eliminar el nivel educativo porque tiene niveles dependientes");
            }
            
            return repository.eliminar(id);
        }
        return false;
    }
}
