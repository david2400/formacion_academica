package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.services;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.acudiente.ActualizarAcudienteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.acudiente.ConsultarAcudienteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.acudiente.CrearAcudienteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.acudiente.EliminarAcudienteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.acudiente.ListarAcudientesPorEstudianteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.auth.AutenticarAcudienteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.auth.LoginAcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.auth.LoginResponseDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.security.jwt.GestionAlumnosJwtTokenProvider;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.security.jwt.TipoSujetoAutenticado;
import org.springframework.security.authentication.BadCredentialsException;
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
        EliminarAcudienteUseCase,
        AutenticarAcudienteUseCase {

    private final AcudienteRepositoryPort acudienteRepositoryPort;
    private final EstudianteRepositoryPort estudianteRepositoryPort;
    private final CuentaUsuarioPort cuentaUsuarioPort;
    private final PasswordEncoder passwordEncoder;
    private final GestionAlumnosJwtTokenProvider jwtTokenProvider;

    public AcudienteService(AcudienteRepositoryPort acudienteRepositoryPort,
                            EstudianteRepositoryPort estudianteRepositoryPort,
                            CuentaUsuarioPort cuentaUsuarioPort,
                            PasswordEncoder passwordEncoder,
                            GestionAlumnosJwtTokenProvider jwtTokenProvider) {
        this.acudienteRepositoryPort = acudienteRepositoryPort;
        this.estudianteRepositoryPort = estudianteRepositoryPort;
        this.cuentaUsuarioPort = cuentaUsuarioPort;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public Acudiente crear(CrearAcudienteDto request) {
        // validarPersonaEmpresa(...) deshabilitada temporalmente: depende de la
        // vista vw_account_usuario de access_control (ver CuentaUsuarioPort),
        // que todavía no está creada en todos los ambientes. Pendiente decidir
        // cómo resolver esa dependencia antes de reactivarla.
        validarDocumentoUnico(request.getTipoDocumento(), request.getNumeroDocumento(), null);
        aplicarPassword(request);
        return acudienteRepositoryPort.guardar(request);
    }

    @Override
    public Acudiente actualizar(ActualizarAcudienteDto request) {
        Acudiente existente = consultarPorId(request.getId());
        // validarPersonaEmpresa(...) deshabilitada temporalmente: ver comentario
        // en crear().
        validarDocumentoUnico(request.getTipoDocumento(), request.getNumeroDocumento(), request.getId());
        aplicarPassword(request);
        return acudienteRepositoryPort.actualizar(request);
    }

    @Override
    public Acudiente consultarPorId(Long acudienteId) {
        return acudienteRepositoryPort.obtenerPorId(acudienteId)
                .orElseThrow(() -> new IllegalArgumentException("Acudiente no encontrado"));
    }

    @Override
    public Acudiente consultarPorTipoYNumeroDocumento(String tipoDocumento, String numeroDocumento) {
        return acudienteRepositoryPort.obtenerPorTipoYNumeroDocumento(tipoDocumento, numeroDocumento)
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

    @Override
    public LoginResponseDto autenticar(LoginAcudienteDto request) {
        Acudiente acudiente = acudienteRepositoryPort
                .obtenerPorTipoYNumeroDocumento(request.getTipoDocumento(), request.getNumeroDocumento())
                .orElseThrow(() -> new BadCredentialsException("Documento o contraseña incorrectos"));

        if (acudiente.getPassword() == null
                || !passwordEncoder.matches(request.getPassword(), acudiente.getPassword())) {
            throw new BadCredentialsException("Documento o contraseña incorrectos");
        }

        String token = jwtTokenProvider.generarToken(acudiente.getId(), TipoSujetoAutenticado.ACUDIENTE,
                acudiente.getNombres(), acudiente.getApellidos());

        return LoginResponseDto.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .expiresAt(jwtTokenProvider.expiracionDe(token))
                .tipo(TipoSujetoAutenticado.ACUDIENTE.name())
                .id(acudiente.getId())
                .nombres(acudiente.getNombres())
                .apellidos(acudiente.getApellidos())
                .build();
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

    /**
     * Evita registrar dos acudientes con el mismo tipo y numero de documento
     * (la tabla acudientes no tiene una restriccion unica para esa
     * combinacion, asi que la validacion se hace aqui).
     */
    private void validarDocumentoUnico(String tipoDocumento, String numeroDocumento, Long excluirId) {
        boolean existe = excluirId == null
                ? acudienteRepositoryPort.existePorTipoYNumeroDocumento(tipoDocumento, numeroDocumento)
                : acudienteRepositoryPort.existePorTipoYNumeroDocumentoConIdDiferente(tipoDocumento, numeroDocumento, excluirId);

        if (existe) {
            throw new IllegalArgumentException("Ya existe un acudiente registrado con ese tipo y número de documento");
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
