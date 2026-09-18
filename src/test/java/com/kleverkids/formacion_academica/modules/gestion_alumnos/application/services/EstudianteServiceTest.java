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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

/**
 * Verifica la validación de usuarioId/empresaId contra access_control al crear
 * o actualizar un estudiante, sin tocar ninguna base de datos real:
 * CuentaUsuarioPort se mockea, igual que EstudianteRepositoryPort.
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
    void crear_sinUsuarioId_noValidaContraAccessControl() {
        CrearEstudianteDto request = dtoBase().build();
        when(repositoryPort.existePorDocumento(request.getTipoDocumento(), request.getNumeroDocumento()))
                .thenReturn(false);
        when(repositoryPort.guardar(request)).thenReturn(new Estudiante());

        estudianteService.crear(request);

        verifyNoInteractions(cuentaUsuarioPort);
        verify(repositoryPort).guardar(request);
    }

    @Test
    void crear_conUsuarioIdSinEmpresaId_lanzaExcepcion() {
        CrearEstudianteDto request = dtoBase().usuarioId(20L).build();
        when(repositoryPort.existePorDocumento(request.getTipoDocumento(), request.getNumeroDocumento()))
                .thenReturn(false);

        assertThatThrownBy(() -> estudianteService.crear(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("empresaId");

        verify(repositoryPort, org.mockito.Mockito.never()).guardar(request);
    }

    @Test
    void crear_conUsuarioIdQueNoPerteneceALaEmpresa_lanzaExcepcion() {
        CrearEstudianteDto request = dtoBase().usuarioId(20L).empresaId(7L).build();
        when(repositoryPort.existePorDocumento(request.getTipoDocumento(), request.getNumeroDocumento()))
                .thenReturn(false);
        when(cuentaUsuarioPort.usuarioPerteneceAEmpresa(20L, 7L)).thenReturn(false);

        assertThatThrownBy(() -> estudianteService.crear(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("no está asignado");

        verify(repositoryPort, org.mockito.Mockito.never()).guardar(request);
    }

    @Test
    void crear_conUsuarioIdValidoEnLaEmpresa_guardaElEstudiante() {
        CrearEstudianteDto request = dtoBase().usuarioId(20L).empresaId(7L).build();
        when(repositoryPort.existePorDocumento(request.getTipoDocumento(), request.getNumeroDocumento()))
                .thenReturn(false);
        when(cuentaUsuarioPort.usuarioPerteneceAEmpresa(20L, 7L)).thenReturn(true);
        Estudiante guardado = new Estudiante();
        when(repositoryPort.guardar(request)).thenReturn(guardado);

        Estudiante resultado = estudianteService.crear(request);

        assertThat(resultado).isSameAs(guardado);
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
