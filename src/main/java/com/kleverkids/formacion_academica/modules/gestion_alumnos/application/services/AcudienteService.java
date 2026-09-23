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
        // validarPersonaEmpresa(...) deshabilitada temporalmente: depende de la
        // vista vw_account_usuario de access_control (ver CuentaUsuarioPort),
        // que todavía no está creada en todos los ambientes. Pendiente decidir
        // cómo resolver esa dependencia antes de reactivarla.
        aplicarPassword(request);
        return acudienteRepositoryPort.guardar(request);
    }

    @Override
    public Acudiente actualizar(ActualizarAcudienteDto request) {
        Acudiente existente = consultarPorId(request.getId());
        // validarPersonaEmpresa(...) deshabilitada temporalmente: ver comentario
        // en crear().
        aplicarPassword(request);
        return acudienteRepositoryPort.actualizar(request);
    }

    @Override
    public Acudiente consultarPorId(Long acudienteId) {
        return acudienteRepositoryPort.obtenerPorId(acudienteId)
                .orElseThrow(() -> new IllegalArgumentException("Acudiente no encontrado"));
    }

    @Override
    public Acudiente consultarPorNumeroDocumento(String numeroDocumento) {
        return acudienteRepositoryPort.obtenerPorNumeroDocumento(numeroDocumento)
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
     * Si existe una persona en access_control con el documento indicado,
     * valida que esté asignada a la empresa indicada.
     *
     * <p>Es opcional: si no existe ninguna persona con ese documento en
     * access_control, no bloquea la creación/actualización. Si existe pero no
     * está asignada a la empresa indicada, sí bloquea.
     */
    private void validarPersonaEmpresa(String tipoDocumento, String numeroDocumento, Long empresaId) {
        cuentaUsuarioPort.buscarPorDocumento(tipoDocumento, numeroDocumento).ifPresent(usuario -> {
            if (!cuentaUsuarioPort.usuarioPerteneceAEmpresa(usuario.getUsuarioId(), empresaId)) {
                throw new IllegalArgumentException(
                        "La persona con el documento indicado existe en access_control pero no está asignada a la empresa indicada");
            }
        });
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
