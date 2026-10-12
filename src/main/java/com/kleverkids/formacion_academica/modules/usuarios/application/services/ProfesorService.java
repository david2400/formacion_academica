package com.kleverkids.formacion_academica.modules.usuarios.application.services;

import com.kleverkids.formacion_academica.modules.usuarios.application.input.profesor.*;
import com.kleverkids.formacion_academica.modules.usuarios.application.output.profesor.ProfesorConsultaPort;
import com.kleverkids.formacion_academica.modules.usuarios.application.output.profesor.ProfesorRepositoryPort;
import com.kleverkids.formacion_academica.modules.usuarios.domain.dto.profesor.*;
import com.kleverkids.formacion_academica.modules.usuarios.domain.model.Profesor;
import com.kleverkids.formacion_academica.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProfesorService implements CrearProfesorUseCase, ActualizarProfesorUseCase, CambiarEstadoProfesorUseCase,
        EliminarProfesorUseCase, ConsultarProfesorUseCase, ListarProfesoresUseCase, BuscarUsuariosDisponiblesUseCase,
        ResumirProfesoresUseCase {

    /** Tope de resultados del buscador de usuarios y del resumen por lote. */
    static final int LIMITE_MAXIMO = 50;

    private final ProfesorRepositoryPort repository;
    private final ProfesorConsultaPort consulta;

    @Override
    @Transactional
    public ProfesorDto crear(CrearProfesorDto dto) {
        if (!consulta.usuarioPerteneceAEmpresa(dto.getUsuarioId(), dto.getEmpresaId())) {
            throw new IllegalArgumentException("El usuario seleccionado no pertenece a esta empresa.");
        }
        if (repository.existe(dto.getUsuarioId(), dto.getEmpresaId())) {
            throw new IllegalStateException("Este usuario ya está registrado como profesor.");
        }
        Profesor datos = Profesor.builder()
                .usuarioId(dto.getUsuarioId())
                .empresaId(dto.getEmpresaId())
                .activo(true)
                .fechaVinculacion(dto.getFechaVinculacion())
                .observaciones(limpiar(dto.getObservaciones()))
                .build();
        Long id = repository.restaurarEliminado(datos).orElseGet(() -> repository.guardar(datos).getId());
        return consultar(id);
    }

    @Override
    @Transactional
    public ProfesorDto actualizar(Long id, ActualizarProfesorDto dto) {
        Profesor profesor = existente(id);
        profesor.setFechaVinculacion(dto.getFechaVinculacion());
        profesor.setObservaciones(limpiar(dto.getObservaciones()));
        repository.guardar(profesor);
        return consultar(id);
    }

    @Override
    @Transactional
    public ProfesorDto cambiarEstado(Long id, boolean activo) {
        Profesor profesor = existente(id);
        if (profesor.isActivo() != activo) {
            profesor.setActivo(activo);
            repository.guardar(profesor);
        }
        return consultar(id);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        existente(id);
        repository.eliminar(id);
    }

    @Override
    @Transactional(readOnly = true)
    public ProfesorDto consultar(Long id) {
        return consulta.obtener(id).orElseThrow(() -> new NotFoundException("Profesor no encontrado."));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProfesorDto> listar(FiltroProfesoresDto filtro, Pageable pageable) {
        Objects.requireNonNull(filtro.getEmpresaId(), "empresaId");
        filtro.setTexto(limpiar(filtro.getTexto()));
        return consulta.listar(filtro, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDisponibleDto> buscarDisponibles(Long empresaId, String texto, int limite) {
        return consulta.usuariosDisponibles(empresaId, limpiar(texto), Math.min(Math.max(limite, 1), LIMITE_MAXIMO));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProfesorResumenDto> resumir(Long empresaId, Collection<Long> usuarioIds) {
        if (usuarioIds == null || usuarioIds.isEmpty()) {
            return List.of();
        }
        if (usuarioIds.size() > LIMITE_MAXIMO * 4) {
            throw new IllegalArgumentException("Pide como máximo " + (LIMITE_MAXIMO * 4) + " usuarios por consulta.");
        }
        return consulta.resumen(empresaId, usuarioIds);
    }

    private Profesor existente(Long id) {
        return repository.buscarPorId(id).orElseThrow(() -> new NotFoundException("Profesor no encontrado."));
    }

    private static String limpiar(String texto) {
        if (texto == null) {
            return null;
        }
        String t = texto.trim();
        return t.isEmpty() ? null : t;
    }
}
