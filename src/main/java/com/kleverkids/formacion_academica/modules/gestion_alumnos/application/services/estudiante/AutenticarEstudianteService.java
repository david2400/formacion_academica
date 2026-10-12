package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.services.estudiante;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.auth.AutenticarEstudianteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.estudiante.EstudianteRepositoryPort;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.auth.LoginEstudianteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.auth.LoginResponseDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model.Estudiante;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.security.jwt.GestionAlumnosJwtTokenProvider;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.security.jwt.TipoSujetoAutenticado;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AutenticarEstudianteService implements AutenticarEstudianteUseCase {

    private final EstudianteRepositoryPort repositoryPort;
    private final PasswordEncoder passwordEncoder;
    private final GestionAlumnosJwtTokenProvider jwtTokenProvider;

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
}
