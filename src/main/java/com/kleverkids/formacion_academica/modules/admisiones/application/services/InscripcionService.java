package com.kleverkids.formacion_academica.modules.admisiones.application.services;

import com.kleverkids.formacion_academica.modules.admisiones.application.input.inscripcion.CambiarEstadoInscripcionUseCase;
import com.kleverkids.formacion_academica.modules.admisiones.application.input.inscripcion.ConsultarInscripcionUseCase;
import com.kleverkids.formacion_academica.modules.admisiones.application.input.inscripcion.EliminarInscripcionUseCase;
import com.kleverkids.formacion_academica.modules.admisiones.application.input.inscripcion.ListarInscripcionesUseCase;
import com.kleverkids.formacion_academica.modules.admisiones.application.input.inscripcion.RegistrarInscripcionUseCase;
import com.kleverkids.formacion_academica.modules.admisiones.application.output.inscripcion.InscripcionRepositoryPort;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion.ActualizarEstadoInscripcionDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion.CrearInscripcionDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion.InscripcionDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion.ListarInscripcionesFiltroDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class InscripcionService implements RegistrarInscripcionUseCase,
        ConsultarInscripcionUseCase,
        ListarInscripcionesUseCase,
        CambiarEstadoInscripcionUseCase,
        EliminarInscripcionUseCase {

    private final InscripcionRepositoryPort inscripcionRepositoryPort;

    public InscripcionService(InscripcionRepositoryPort inscripcionRepositoryPort) {
        this.inscripcionRepositoryPort = inscripcionRepositoryPort;
    }

    @Override
    public InscripcionDto registrar(CrearInscripcionDto request) {
        return inscripcionRepositoryPort.registrar(request);
    }

    @Override
    public InscripcionDto consultarPorId(UUID inscripcionId) {
        return inscripcionRepositoryPort.obtenerPorId(inscripcionId)
                .orElseThrow(() -> new IllegalArgumentException("Inscripción no encontrada"));
    }

    @Override
    public List<InscripcionDto> listar(ListarInscripcionesFiltroDto filtro) {
        return inscripcionRepositoryPort.listar(filtro);
    }

    @Override
    public InscripcionDto cambiarEstado(ActualizarEstadoInscripcionDto request) {
        consultarPorId(request.getInscripcionId());
        return inscripcionRepositoryPort.actualizarEstado(request);
    }

    @Override
    public void eliminar(UUID inscripcionId) {
        consultarPorId(inscripcionId);
        inscripcionRepositoryPort.eliminar(inscripcionId);
    }
}
