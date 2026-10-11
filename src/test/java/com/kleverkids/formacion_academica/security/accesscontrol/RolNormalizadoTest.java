package com.kleverkids.formacion_academica.security.accesscontrol;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/** access_control envía nombres de rol; la configuración usa códigos. */
class RolNormalizadoTest {

    @Test
    void nombreYCodigoTerminanIguales() {
        assertThat(RolNormalizado.de("Administrador")).isEqualTo("ADMINISTRADOR");
        assertThat(RolNormalizado.de(" Coordinador Académico ")).isEqualTo("COORDINADOR_ACADEMICO");
        assertThat(RolNormalizado.de("coordinador-academico")).isEqualTo("COORDINADOR_ACADEMICO");
        assertThat(RolNormalizado.de("Super Admin")).isEqualTo(RolNormalizado.de("SUPER_ADMIN"));
        assertThat(RolNormalizado.autoridad("Docente")).isEqualTo("ROLE_DOCENTE");
        assertThat(RolNormalizado.de(null)).isEmpty();
    }
}
