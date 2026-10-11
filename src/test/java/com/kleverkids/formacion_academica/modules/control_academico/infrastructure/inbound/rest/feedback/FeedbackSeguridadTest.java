package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.inbound.rest.feedback;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.security.jwt.GestionAlumnosPrincipal;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.security.jwt.TipoSujetoAutenticado;
import com.kleverkids.formacion_academica.security.accesscontrol.AccessControlPrincipal;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/** Quién puede qué en Feedback, y que un docente nunca escape de sus clases. */
class FeedbackSeguridadTest {

    private final FeedbackSeguridadProperties props = new FeedbackSeguridadProperties();
    private final FeedbackSeguridad seguridad = new FeedbackSeguridad(props);

    @AfterEach
    void limpiar() {
        SecurityContextHolder.clearContext();
    }

    private static void staff(String rol, Long userId, Long employeeId) {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                new AccessControlPrincipal(userId, employeeId, "u", List.of(rol)), null,
                List.of(new SimpleGrantedAuthority("ROLE_" + rol))));
    }

    @Test
    void administracionVeTodoYDocenteQuedaRestringido() {
        staff("COORDINADOR", 1L, null);
        assertThat(seguridad.administracion()).isTrue();
        assertThat(seguridad.docenteRestringido()).isNull();

        staff("DOCENTE", 42L, 9L);
        assertThat(seguridad.administracion()).isFalse();
        assertThat(seguridad.docenteRestringido()).isEqualTo(42L);

        props.setIdentificadorDocente(FeedbackSeguridadProperties.IdentificadorDocente.EMPLOYEE_ID);
        assertThat(seguridad.docenteRestringido()).isEqualTo(9L);
    }

    @Test
    void elNombreDelRolDeAccessControlCoincideConElCodigoConfigurado() {
        // El claim trae el nombre ("Coordinador Académico"); el filtro lo normaliza.
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                new AccessControlPrincipal(1L, null, "u", List.of("Coordinador Académico")), null,
                List.of(new SimpleGrantedAuthority(
                        com.kleverkids.formacion_academica.security.accesscontrol.RolNormalizado.autoridad("Coordinador Académico")))));
        props.setRolesAdministracion(List.of("ADMINISTRADOR", "coordinador académico"));
        assertThat(seguridad.administracion()).isTrue();
    }

    @Test
    void estudianteSoloSeResuelveDesdeSuToken() {
        assertThat(seguridad.estudiante()).isFalse();
        assertThatThrownBy(seguridad::estudianteActual).isInstanceOf(AccessDeniedException.class);

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                new GestionAlumnosPrincipal(15L, TipoSujetoAutenticado.ESTUDIANTE, "Ana", "Ruiz"), null,
                List.of(new SimpleGrantedAuthority("ROLE_ESTUDIANTE"))));
        assertThat(seguridad.estudianteActual()).isEqualTo(15L);
        assertThat(seguridad.administracionODocente()).isFalse();
        assertThatThrownBy(seguridad::docenteRestringido).isInstanceOf(AccessDeniedException.class);
    }

    @Test
    void acudienteNoEsEstudiante() {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                new GestionAlumnosPrincipal(15L, TipoSujetoAutenticado.ACUDIENTE, "Luis", "Ruiz"), null,
                List.of(new SimpleGrantedAuthority("ROLE_ACUDIENTE"))));
        assertThat(seguridad.estudiante()).isFalse();
    }
}
