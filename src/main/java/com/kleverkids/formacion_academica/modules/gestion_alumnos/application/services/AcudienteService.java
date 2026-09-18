package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.services;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.acudiente.ActualizarAcudienteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.acudiente.ConsultarAcudienteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.acudiente.CrearAcudienteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.acudiente.EliminarAcudienteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.acudiente.ListarAcudientesPorEstudianteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.acudiente.AcudienteRepositoryPort;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.cuenta.CuentaUsuarioPort;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.estudiante.EstudianteRepositoryPort;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model.Acudiente;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.acudiente.ActualizarAcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.acudiente.CrearAcudienteDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AcudienteService implements CrearAcudienteUseCase,
        ActualizarAcudienteUseCase,
        ConsultarAcudienteUseCase,
        ListarAcudientesPorEstudianteUseCase,
        EliminarAcudienteUseCase {

    private final AcudienteRepositoryPort acudienteRepositoryPort;
    private final EstudianteRepositoryPort estudianteRepositoryPort;
    private final CuentaUsuarioPort cuentaUsuarioPort;
    private final PasswordEncoder passwordEncoder;

    public AcudienteService(AcudienteRepositoryPort acudienteRepositoryPort,
                            EstudianteRepositoryPort estudianteRepositoryPort,
                            CuentaUsuarioPort cuentaUsuarioPort,
                            PasswordEncoder passwordEncoder) {
        this.acudienteRepositoryPort = acudienteRepositoryPort;
        this.estudianteRepositoryPort = estudianteRepositoryPort;
        this.cuentaUsuarioPort = cuentaUsuarioPort;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Acudiente crear(CrearAcudienteDto request) {
        validarUsuarioEmpresa(request.getUsuarioId(), request.getEmpresaId());
        aplicarPassword(request);
        return acudienteRepositoryPort.guardar(request);
    }

    @Override
    public Acudiente actualizar(ActualizarAcudienteDto request) {
        Acudiente existente = consultarPorId(request.getId());
        validarUsuarioEmpresa(request.getUsuarioId(), request.getEmpresaId());
        aplicarPassword(request);
        return acudienteRepositoryPort.actualizar(request);
    }

    @Override
    public Acudiente consultarPorId(Long acudienteId) {
        return acudienteRepositoryPort.obtenerPorId(acudienteId)
                .orElseThrow(() -> new IllegalArgumentException("Acudiente no encontrado"));
    }

    @Override
    public List<Acudiente> listar(Long estudianteId) {
        validarExistenciaEstudiante(estudianteId);
        return acudienteRepositoryPort.listarPorEstudiante(estudianteId);
    }

    @Override
    public void eliminar(Long acudienteId) {
        consultarPorId(acudienteId);
        acudienteRepositoryPort.eliminar(acudienteId);
    }

    private void validarExistenciaEstudiante(Long estudianteId) {
        estudianteRepositoryPort.obtenerPorId(estudianteId)
                .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado"));
    }

    /**
     * Valida el usuario de access_control contra la empresa indicada.
     *
     * <p>usuarioId es opcional (compatibilidad con integraciones existentes):
     * si no se informa, no se valida nada aquí. Si se informa, empresaId pasa
     * a ser obligatorio para poder verificar la asignación.
     */
    private void validarUsuarioEmpresa(Long usuarioId, Long empresaId) {
        if (usuarioId == null) {
            return;
        }
        if (empresaId == null) {
            throw new IllegalArgumentException("empresaId es obligatorio cuando se informa usuarioId");
        }
        if (!cuentaUsuarioPort.usuarioPerteneceAEmpresa(usuarioId, empresaId)) {
            throw new IllegalArgumentException(
                    "El usuario indicado no existe en access_control o no está asignado a la empresa indicada");
        }
    }

    private void validarPrincipalUnico(Long estudianteId, Long excluirAcudienteId, Boolean esPrincipal) {
        if (Boolean.TRUE.equals(esPrincipal) &&
                acudienteRepositoryPort.existePrincipalParaEstudiante(estudianteId, excluirAcudienteId)) {
            throw new IllegalArgumentException("El estudiante ya tiene un acudiente principal");
        }
    }

    /**
     * Hashea (BCrypt) la contraseña en texto plano del request antes de que
     * llegue al mapper/entidad — nunca se persiste en texto plano.
     *
     * <p>Si no se informa contraseña (null o en blanco), se deja el campo en
     * null: en una creación, el acudiente queda sin contraseña; en una
     * actualización, {@code updateEntityFromDto} usa
     * {@code NullValuePropertyMappingStrategy.IGNORE}, así que la contraseña
     * existente no se toca.
     */
    private void aplicarPassword(CrearAcudienteDto request) {
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            request.setPassword(null);
            return;
        }
        request.setPassword(passwordEncoder.encode(request.getPassword()));
    }
}
