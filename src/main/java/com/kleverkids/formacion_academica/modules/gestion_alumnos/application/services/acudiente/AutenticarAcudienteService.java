package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.services.acudiente;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.auth.AutenticarAcudienteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.acudiente.AcudienteRepositoryPort;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.auth.LoginAcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.auth.LoginResponseDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model.Acudiente;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.security.jwt.GestionAlumnosJwtTokenProvider;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.security.jwt.TipoSujetoAutenticado;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AutenticarAcudienteService implements AutenticarAcudienteUseCase {

    private final AcudienteRepositoryPort acudienteRepositoryPort;
    private final PasswordEncoder passwordEncoder;
    private final GestionAlumnosJwtTokenProvider jwtTokenProvider;

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
}
