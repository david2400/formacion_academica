package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.services;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante.ActualizarEstudianteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante.ConsultarEstudianteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante.CrearEstudianteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante.EliminarEstudianteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante.ListarEstudiantesUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante.ListarEstudiantesPaginadoUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.auth.AutenticarEstudianteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.auth.LoginEstudianteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.auth.LoginResponseDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.security.jwt.GestionAlumnosJwtTokenProvider;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.security.jwt.TipoSujetoAutenticado;
import org.springframework.security.authentication.BadCredentialsException;
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
        EliminarEstudianteUseCase,
        AutenticarEstudianteUseCase {

    private final EstudianteRepositoryPort repositoryPort;
    private final CuentaUsuarioPort cuentaUsuarioPort;
    private final PasswordEncoder passwordEncoder;
    private final GestionAlumnosJwtTokenProvider jwtTokenProvider;

    public EstudianteService(EstudianteRepositoryPort repositoryPort,
                              CuentaUsuarioPort cuentaUsuarioPort,
                              PasswordEncoder passwordEncoder,
                              GestionAlumnosJwtTokenProvider jwtTokenProvider) {
        this.repositoryPort = repositoryPort;
        this.cuentaUsuarioPort = cuentaUsuarioPort;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public Estudiante crear(CrearEstudianteDto request) {
        validarDocumentoUnico(request.getTipoDocumento(), request.getNumeroDocumento());
        // validarPersonaEmpresa(...) deshabilitada temporalmente: depende de la
        // vista vw_account_usuario de access_control (ver CuentaUsuarioPort),
        // que todavía no está creada en todos los ambientes. Pendiente decidir
        // cómo resolver esa dependencia antes de reactivarla.
        aplicarPassword(request);
        return repositoryPort.guardar(request);
    }

    @Override
    public Estudiante actualizar(UpdateEstudianteDto request) {
        // validarPersonaEmpresa(...) deshabilitada temporalmente: ver comentario
        // en crear().
        aplicarPassword(request);
        return repositoryPort.actualizar(request);
    }

    @Override
    public Estudiante consultarPorId(Long estudianteId) {
        return repositoryPort.obtenerPorId(estudianteId)
                .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado"));
    }

    @Override
    public Estudiante consultarPorTipoYNumeroDocumento(String tipoDocumento, String numeroDocumento) {
        return repositoryPort.obtenerPorTipoYNumeroDocumento(tipoDocumento, numeroDocumento)
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

    @Override
    public LoginResponseDto autenticar(LoginEstudianteDto request) {
        Estudiante estudiante = repositoryPort
                .obtenerPorTipoYNumeroDocumento(request.getTipoDocumento(), request.getNumeroDocumento())
                .orElseThrow(() -> new BadCredentialsException("Documento o contraseña incorrectos"));

        if (estudiante.getPassword() == null
                || !passwordEncoder.matches(request.getPassword(), estudiante.getPassword())) {
            // Mismo mensaje que si el documento no existe: no revelar cuál de
            // los dos datos fue el incorrecto.
            throw new BadCredentialsException("Documento o contraseña incorrectos");
        }

        String apellidos = apellidosCompuestos(estudiante);
        String token = jwtTokenProvider.generarToken(estudiante.getId(), TipoSujetoAutenticado.ESTUDIANTE,
                estudiante.getNombres(), apellidos);

        return LoginResponseDto.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .expiresAt(jwtTokenProvider.expiracionDe(token))
                .tipo(TipoSujetoAutenticado.ESTUDIANTE.name())
                .id(estudiante.getId())
                .nombres(estudiante.getNombres())
                .apellidos(apellidos)
                .build();
    }

    /** Estudiante separa primerApellido/segundoApellido; el login los compone en un solo valor para mostrar. */
    private String apellidosCompuestos(Estudiante estudiante) {
        return String.join(" ", java.util.stream.Stream
                .of(estudiante.getPrimerApellido(), estudiante.getSegundoApellido())
                .filter(s -> s != null && !s.isBlank())
                .toList());
    }

    private void validarDocumentoUnico(String tipoDocumento, String numeroDocumento) {
        if (repositoryPort.existePorDocumento(tipoDocumento, numeroDocumento)) {
            throw new IllegalArgumentException("Ya existe un estudiante con el documento indicado");
        }
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
