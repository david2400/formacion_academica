package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.services;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante.ActualizarEstudianteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante.ConsultarEstudianteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante.CrearEstudianteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante.EliminarEstudianteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante.ListarEstudiantesUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante.ListarEstudiantesPaginadoUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.cuenta.CuentaUsuarioPort;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.estudiante.EstudianteRepositoryPort;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model.Estudiante;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante.CrearEstudianteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante.UpdateEstudianteDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstudianteService implements CrearEstudianteUseCase,
        ActualizarEstudianteUseCase,
        ConsultarEstudianteUseCase,
        ListarEstudiantesUseCase,
        ListarEstudiantesPaginadoUseCase,
        EliminarEstudianteUseCase {

    private final EstudianteRepositoryPort repositoryPort;
    private final CuentaUsuarioPort cuentaUsuarioPort;
    private final PasswordEncoder passwordEncoder;

    public EstudianteService(EstudianteRepositoryPort repositoryPort,
                              CuentaUsuarioPort cuentaUsuarioPort,
                              PasswordEncoder passwordEncoder) {
        this.repositoryPort = repositoryPort;
        this.cuentaUsuarioPort = cuentaUsuarioPort;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Estudiante crear(CrearEstudianteDto request) {
        validarDocumentoUnico(request.getTipoDocumento(), request.getNumeroDocumento());
        validarUsuarioEmpresa(request.getUsuarioId(), request.getEmpresaId());
        aplicarPassword(request);
        return repositoryPort.guardar(request);
    }

    @Override
    public Estudiante actualizar(UpdateEstudianteDto request) {
        validarUsuarioEmpresa(request.getUsuarioId(), request.getEmpresaId());
        aplicarPassword(request);
        return repositoryPort.actualizar(request);
    }

    @Override
    public Estudiante consultarPorId(Long estudianteId) {
        return repositoryPort.obtenerPorId(estudianteId)
                .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado"));
    }

    @Override
    public List<Estudiante> listar() {
        return repositoryPort.listar();
    }

    @Override
    public Page<Estudiante> listar(Pageable pageable) {
        return repositoryPort.listar(pageable);
    }

    @Override
    public void eliminar(Long estudianteId) {
        consultarPorId(estudianteId);
        repositoryPort.eliminar(estudianteId);
    }

    private void validarDocumentoUnico(String tipoDocumento, String numeroDocumento) {
        if (repositoryPort.existePorDocumento(tipoDocumento, numeroDocumento)) {
            throw new IllegalArgumentException("Ya existe un estudiante con el documento indicado");
        }
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

    /**
     * Hashea (BCrypt) la contraseña en texto plano del request antes de que
     * llegue al mapper/entidad — nunca se persiste en texto plano.
     *
     * <p>Si no se informa contraseña (null o en blanco), se deja el campo en
     * null: en una creación, el estudiante queda sin contraseña; en una
     * actualización, {@code updateEntityFromDto} usa
     * {@code NullValuePropertyMappingStrategy.IGNORE}, así que la contraseña
     * existente no se toca.
     */
    private void aplicarPassword(CrearEstudianteDto request) {
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            request.setPassword(null);
            return;
        }
        request.setPassword(passwordEncoder.encode(request.getPassword()));
    }
}
