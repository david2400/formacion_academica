package com.kleverkids.formacion_academica.modules.admisiones.application.services.inscripcion;

import com.kleverkids.formacion_academica.modules.admisiones.application.input.inscripcion.ListarInscripcionesUseCase;
import com.kleverkids.formacion_academica.modules.admisiones.application.output.inscripcion.InscripcionRepositoryPort;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion.ListarInscripcionesFiltroDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.model.Inscripcion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarInscripcionesService implements ListarInscripcionesUseCase {

    private final InscripcionRepositoryPort inscripcionRepositoryPort;

    @Override
    public List<Inscripcion> listar(ListarInscripcionesFiltroDto filtro) {
        return inscripcionRepositoryPort.listar(filtro);
    }
}
