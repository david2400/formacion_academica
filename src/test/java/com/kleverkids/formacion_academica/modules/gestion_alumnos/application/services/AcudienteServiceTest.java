package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.services;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.acudiente.AcudienteRepositoryPort;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.cuenta.CuentaUsuarioPort;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.estudiante.EstudianteRepositoryPort;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.acudiente.CrearAcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model.Acudiente;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model.Estudiante;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

/**
 * Verifica la validación de usuarioId/empresaId contra access_control al crear
 * o actualizar un acudiente, sin tocar ninguna base de datos real: CuentaUsuarioPort
 * se mockea, igual que AcudienteRepositoryPort/EstudianteRepositoryPort.
 *
 * <p>También verifica que la contraseña propia del acudiente se hashea (BCrypt,
 * vía PasswordEncoder mockeado) antes de guardar, y que se deja sin tocar
 * cuando no se informa.
 */
@ExtendWith(MockitoExtension.class)
class AcudienteServiceTest {

    @Mock
    private AcudienteRepositoryPort acudienteRepositoryPort;

    @Mock
    private EstudianteRepositoryPort estudianteRepositoryPort;

    @Mock
    private CuentaUsuarioPort cuentaUsuarioPort;

    @Mock
    private PasswordEncoder passwordEncoder;

    private AcudienteService acudienteService;

    @BeforeEach
    void setUp() {
        acudienteService = new AcudienteService(acudienteRepositoryPort, estudianteRepositoryPort, cuentaUsuarioPort, passwordEncoder);
    }

    @Test
    void crear_sinUsuarioId_noValidaContraAccessControl() {
        CrearAcudienteDto request = CrearAcudienteDto.builder()
                .numeroDocumento("123")
                .nombres("Ana")
                .parentesco("Madre")
                .build();
        when(acudienteRepositoryPort.guardar(request)).thenReturn(new Acudiente());

        acudienteService.crear(request);

        verifyNoInteractions(cuentaUsuarioPort);
        verify(acudienteRepositoryPort).guardar(request);
    }

    @Test
    void crear_conUsuarioIdSinEmpresaId_lanzaExcepcion() {
        CrearAcudienteDto request = CrearAcudienteDto.builder()
                .numeroDocumento("123")
                .nombres("Ana")
                .parentesco("Madre")
                .usuarioId(10L)
                .build();

        assertThatThrownBy(() -> acudienteService.crear(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("empresaId");

        verifyNoInteractions(acudienteRepositoryPort);
    }

    @Test
    void crear_conUsuarioIdQueNoPerteneceALaEmpresa_lanzaExcepcion() {
        CrearAcudienteDto request = CrearAcudienteDto.builder()
                .numeroDocumento("123")
                .nombres("Ana")
                .parentesco("Madre")
                .usuarioId(10L)
                .empresaId(5L)
                .build();
        when(cuentaUsuarioPort.usuarioPerteneceAEmpresa(10L, 5L)).thenReturn(false);

        assertThatThrownBy(() -> acudienteService.crear(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("no está asignado");

        verifyNoInteractions(acudienteRepositoryPort);
    }

    @Test
    void crear_conUsuarioIdValidoEnLaEmpresa_guardaElAcudiente() {
        CrearAcudienteDto request = CrearAcudienteDto.builder()
                .numeroDocumento("123")
                .nombres("Ana")
                .parentesco("Madre")
                .usuarioId(10L)
                .empresaId(5L)
                .build();
        when(cuentaUsuarioPort.usuarioPerteneceAEmpresa(10L, 5L)).thenReturn(true);
        Acudiente guardado = new Acudiente();
        when(acudienteRepositoryPort.guardar(request)).thenReturn(guardado);

        Acudiente resultado = acudienteService.crear(request);

        assertThat(resultado).isSameAs(guardado);
        verify(acudienteRepositoryPort).guardar(request);
    }

    @Test
    void crear_conPassword_laHasheaAntesDeGuardar() {
        CrearAcudienteDto request = CrearAcudienteDto.builder()
                .numeroDocumento("123")
                .nombres("Ana")
                .parentesco("Madre")
                .password("plano123")
                .build();
        when(passwordEncoder.encode("plano123")).thenReturn("hash-bcrypt");
        when(acudienteRepositoryPort.guardar(request)).thenReturn(new Acudiente());

        acudienteService.crear(request);

        assertThat(request.getPassword()).isEqualTo("hash-bcrypt");
        verify(passwordEncoder).encode("plano123");
    }

    @Test
    void crear_sinPassword_noLlamaAlPasswordEncoderYQuedaSinContrasena() {
        CrearAcudienteDto request = CrearAcudienteDto.builder()
                .numeroDocumento("123")
                .nombres("Ana")
                .parentesco("Madre")
                .build();
        when(acudienteRepositoryPort.guardar(request)).thenReturn(new Acudiente());

        acudienteService.crear(request);

        assertThat(request.getPassword()).isNull();
        verifyNoInteractions(passwordEncoder);
    }
}
