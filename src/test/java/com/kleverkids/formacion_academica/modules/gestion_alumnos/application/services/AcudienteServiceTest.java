package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.services;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.acudiente.AcudienteRepositoryPort;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.cuenta.CuentaUsuarioPort;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.estudiante.EstudianteRepositoryPort;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.acudiente.CrearAcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model.Acudiente;
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
 * Verifica el flujo de creación/actualización de un acudiente sin tocar
 * ninguna base de datos real: AcudienteRepositoryPort/EstudianteRepositoryPort
 * se mockean, igual que CuentaUsuarioPort.
 *
 * <p>La validación por documento contra access_control (CuentaUsuarioPort.
 * buscarPorDocumento) está deshabilitada temporalmente en AcudienteService
 * (depende de la vista vw_account_usuario, pendiente de crear en todos los
 * ambientes) — por eso el único test relacionado con CuentaUsuarioPort aquí
 * verifica que NO se interactúa con él, no que valide nada.
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
    void crear_noConsultaAccessControl_validacionDeshabilitadaTemporalmente() {
        CrearAcudienteDto request = CrearAcudienteDto.builder()
                .numeroDocumento("123")
                .nombres("Ana")
                .parentesco("Madre")
                .empresaId(5L)
                .build();
        when(acudienteRepositoryPort.guardar(request)).thenReturn(new Acudiente());

        acudienteService.crear(request);

        verifyNoInteractions(cuentaUsuarioPort);
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
