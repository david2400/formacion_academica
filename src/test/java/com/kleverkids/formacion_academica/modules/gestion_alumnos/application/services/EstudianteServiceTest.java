package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.services;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.cuenta.CuentaUsuarioPort;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.estudiante.EstudianteRepositoryPort;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante.CrearEstudianteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model.Estudiante;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

/**
 * Verifica el flujo de creación/actualización de un estudiante sin tocar
 * ninguna base de datos real: EstudianteRepositoryPort se mockea, igual que
 * CuentaUsuarioPort.
 *
 * <p>La validación por documento contra access_control (CuentaUsuarioPort.
 * buscarPorDocumento) está deshabilitada temporalmente en EstudianteService
 * (depende de la vista vw_account_usuario, pendiente de crear en todos los
 * ambientes) — por eso el único test relacionado con CuentaUsuarioPort aquí
 * verifica que NO se interactúa con él, no que valide nada.
 *
 * <p>También verifica que la contraseña propia del estudiante se hashea
 * (BCrypt, vía PasswordEncoder mockeado) antes de guardar, y que se deja sin
 * tocar cuando no se informa.
 */
@ExtendWith(MockitoExtension.class)
class EstudianteServiceTest {

    @Mock
    private EstudianteRepositoryPort repositoryPort;

    @Mock
    private CuentaUsuarioPort cuentaUsuarioPort;

    @Mock
    private PasswordEncoder passwordEncoder;

    private EstudianteService estudianteService;

    @BeforeEach
    void setUp() {
        estudianteService = new EstudianteService(repositoryPort, cuentaUsuarioPort, passwordEncoder);
    }

    private CrearEstudianteDto.CrearEstudianteDtoBuilder<?, ?> dtoBase() {
        return CrearEstudianteDto.builder()
                .numeroDocumento("456")
                .nombres("Juan")
                .primerApellido("Pérez")
                .segundoApellido("Gómez");
    }

    @Test
    void crear_noConsultaAccessControl_validacionDeshabilitadaTemporalmente() {
        CrearEstudianteDto request = dtoBase().empresaId(7L).build();
        when(repositoryPort.existePorDocumento(request.getTipoDocumento(), request.getNumeroDocumento()))
                .thenReturn(false);
        when(repositoryPort.guardar(request)).thenReturn(new Estudiante());

        estudianteService.crear(request);

        verifyNoInteractions(cuentaUsuarioPort);
        verify(repositoryPort).guardar(request);
    }

    @Test
    void crear_conPassword_laHasheaAntesDeGuardar() {
        CrearEstudianteDto request = dtoBase().password("plano123").build();
        when(repositoryPort.existePorDocumento(request.getTipoDocumento(), request.getNumeroDocumento()))
                .thenReturn(false);
        when(passwordEncoder.encode("plano123")).thenReturn("hash-bcrypt");
        when(repositoryPort.guardar(request)).thenReturn(new Estudiante());

        estudianteService.crear(request);

        assertThat(request.getPassword()).isEqualTo("hash-bcrypt");
        verify(passwordEncoder).encode("plano123");
    }

    @Test
    void crear_sinPassword_noLlamaAlPasswordEncoderYQuedaSinContrasena() {
        CrearEstudianteDto request = dtoBase().build();
        when(repositoryPort.existePorDocumento(request.getTipoDocumento(), request.getNumeroDocumento()))
                .thenReturn(false);
        when(repositoryPort.guardar(request)).thenReturn(new Estudiante());

        estudianteService.crear(request);

        assertThat(request.getPassword()).isNull();
        verifyNoInteractions(passwordEncoder);
    }
}
