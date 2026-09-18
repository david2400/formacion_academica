package com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.jdbc;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model.UsuarioAcceso;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

/**
 * No abre conexión a MySQL: JdbcTemplate se mockea. Lo que se verifica es que
 * el adapter arma el SQL contra el esquema configurado (por defecto
 * {@code security}) y traduce correctamente el resultado, no la consulta en sí
 * contra una base real (eso sería un test de integración aparte).
 */
@ExtendWith(MockitoExtension.class)
class CuentaUsuarioJdbcAdapterTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    private CuentaUsuarioJdbcAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new CuentaUsuarioJdbcAdapter(jdbcTemplate, new CuentaUsuarioProperties());
    }

    @Test
    void obtenerUsuario_consultaLaVistaDelEsquemaConfigurado() {
        UsuarioAcceso esperado = UsuarioAcceso.builder().usuarioId(1L).usuario("jperez").build();
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(1L)))
                .thenReturn(List.of(esperado));

        Optional<UsuarioAcceso> resultado = adapter.obtenerUsuario(1L);

        assertThat(resultado).contains(esperado);

        ArgumentCaptor<String> sqlCaptor = ArgumentCaptor.forClass(String.class);
        verify(jdbcTemplate).query(sqlCaptor.capture(), any(RowMapper.class), eq(1L));
        assertThat(sqlCaptor.getValue()).contains("security.vw_account_usuario");
    }

    @Test
    void obtenerUsuario_sinResultados_devuelveOptionalVacio() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(1L)))
                .thenReturn(List.of());

        assertThat(adapter.obtenerUsuario(1L)).isEmpty();
    }

    @Test
    void usuarioPerteneceAEmpresa_conCoincidencias_devuelveTrue() {
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), eq(10L), eq(5L)))
                .thenReturn(1);

        assertThat(adapter.usuarioPerteneceAEmpresa(10L, 5L)).isTrue();
    }

    @Test
    void usuarioPerteneceAEmpresa_sinCoincidencias_devuelveFalse() {
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), eq(10L), eq(5L)))
                .thenReturn(0);

        assertThat(adapter.usuarioPerteneceAEmpresa(10L, 5L)).isFalse();
    }

    @Test
    void usuarioPerteneceAEmpresa_consultaLaTablaUserCompanyDirectamente() {
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), eq(10L), eq(5L)))
                .thenReturn(1);

        adapter.usuarioPerteneceAEmpresa(10L, 5L);

        ArgumentCaptor<String> sqlCaptor = ArgumentCaptor.forClass(String.class);
        verify(jdbcTemplate).queryForObject(sqlCaptor.capture(), eq(Integer.class), eq(10L), eq(5L));
        assertThat(sqlCaptor.getValue())
                .contains("security.UserCompany")
                .doesNotContain("vw_account_usuario_empresa");
    }

    @Test
    void usuarioPerteneceAEmpresa_conIdsNulos_devuelveFalseSinConsultar() {
        assertThat(adapter.usuarioPerteneceAEmpresa(null, 5L)).isFalse();
        verifyNoInteractions(jdbcTemplate);
    }
}
