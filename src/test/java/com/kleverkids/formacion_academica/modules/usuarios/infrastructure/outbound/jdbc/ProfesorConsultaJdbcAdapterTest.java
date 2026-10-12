package com.kleverkids.formacion_academica.modules.usuarios.infrastructure.outbound.jdbc;

import com.kleverkids.formacion_academica.modules.usuarios.domain.dto.profesor.*;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * SQL real (H2 en modo MySQL) con un esquema "security" mínimo: User, Client y user_company
 * (las tablas reales; el adaptador ya no depende de la vista vw_account_usuario).
 */
class ProfesorConsultaJdbcAdapterTest {

    private ProfesorConsultaJdbcAdapter adapter;
    private JdbcTemplate sql;

    @BeforeEach
    void preparar() {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:" + UUID.randomUUID() + ";DB_CLOSE_DELAY=-1;MODE=MySQL;DATABASE_TO_LOWER=TRUE;NON_KEYWORDS=USER");
        sql = new JdbcTemplate(ds);
        sql.execute("CREATE SCHEMA security");
        sql.execute("CREATE TABLE security.`Client` (id_client BIGINT PRIMARY KEY, first_name VARCHAR(50), second_name VARCHAR(50),"
                + " first_last_name VARCHAR(50), second_last_name VARCHAR(50), type_id VARCHAR(2), card_id VARCHAR(20),"
                + " sex VARCHAR(10), gender VARCHAR(10), status VARCHAR(30))");
        sql.execute("CREATE TABLE security.`User` (id_user BIGINT PRIMARY KEY, username VARCHAR(50), status VARCHAR(30),"
                + " company_id BIGINT, client_id BIGINT)");
        sql.execute("CREATE TABLE security.user_company (user_id BIGINT, company_id BIGINT, expires_at TIMESTAMP)");
        sql.execute("CREATE TABLE profesores (id BIGINT AUTO_INCREMENT PRIMARY KEY, usuario_id BIGINT, empresa_id BIGINT,"
                + " activo BOOLEAN, fecha_vinculacion DATE, observaciones VARCHAR(500), created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                + " eliminado BOOLEAN DEFAULT FALSE)");

        persona(1, "Ana", "Pérez", "1001", 10L);          // dueña de la empresa 10 (User.company_id)
        persona(2, "Bruno", "Gómez", "1002", null);        // asignado por UserCompany vigente
        persona(3, "Carla", "Ruiz", "1003", null);         // UserCompany vencida
        persona(4, "Diego", "Soto", "1004", 20L);          // otra empresa
        persona(5, "Eva", "Arias", "1005", null);          // vigente, ya es profesora
        sql.update("INSERT INTO security.user_company VALUES (2, 10, NULL), (3, 10, TIMESTAMP '2000-01-01 00:00:00'), (5, 10, NULL)");
        sql.update("INSERT INTO profesores (usuario_id, empresa_id, activo, eliminado) VALUES (5, 10, TRUE, FALSE), (1, 10, FALSE, FALSE), (4, 20, TRUE, FALSE)");

        adapter = new ProfesorConsultaJdbcAdapter(new NamedParameterJdbcTemplate(ds), "security");
    }

    private void persona(long id, String nombre, String apellido, String doc, Long empresa) {
        sql.update("INSERT INTO security.`Client` VALUES (?, ?, NULL, ?, NULL, 'CC', ?, 'F', 'F', 'ACTIVE')", id, nombre, apellido, doc);
        sql.update("INSERT INTO security.`User` VALUES (?, ?, 'ACTIVE', ?, ?)", id, nombre.toLowerCase(), empresa, id);
    }

    @Test
    void pertenenciaPorCompanyIdOUserCompanyVigente() {
        assertThat(adapter.usuarioPerteneceAEmpresa(1L, 10L)).isTrue();
        assertThat(adapter.usuarioPerteneceAEmpresa(2L, 10L)).isTrue();
        assertThat(adapter.usuarioPerteneceAEmpresa(3L, 10L)).isFalse();
        assertThat(adapter.usuarioPerteneceAEmpresa(4L, 10L)).isFalse();
        assertThat(adapter.usuarioPerteneceAEmpresa(99L, 10L)).isFalse();
    }

    @Test
    void disponiblesSonDeLaEmpresaYTodaviaNoProfesores() {
        List<UsuarioDisponibleDto> todos = adapter.usuariosDisponibles(10L, null, 20);
        assertThat(todos).extracting(UsuarioDisponibleDto::getUsuarioId).containsExactly(2L);
        assertThat(todos.get(0).getNombreCompleto()).isEqualTo("Bruno Gómez");
        assertThat(adapter.usuariosDisponibles(10L, "zzz", 20)).isEmpty();
    }

    @Test
    void listaSoloLaEmpresaConFiltrosYPaginacion() {
        Page<ProfesorDto> pagina = adapter.listar(FiltroProfesoresDto.builder().empresaId(10L).build(),
                PageRequest.of(0, 1, Sort.by("nombre")));
        assertThat(pagina.getTotalElements()).isEqualTo(2);
        assertThat(pagina.getContent()).extracting(ProfesorDto::getNombreCompleto).containsExactly("Eva Arias");

        Page<ProfesorDto> activos = adapter.listar(FiltroProfesoresDto.builder().empresaId(10L).activo(true).build(),
                PageRequest.of(0, 20, Sort.by("nombre")));
        assertThat(activos.getContent()).extracting(ProfesorDto::getUsuarioId).containsExactly(5L);

        Page<ProfesorDto> porDocumento = adapter.listar(FiltroProfesoresDto.builder().empresaId(10L).texto("1001").build(),
                PageRequest.of(0, 20, Sort.by("nombre")));
        assertThat(porDocumento.getContent()).extracting(ProfesorDto::getUsuario).containsExactly("ana");

        // Los comodines de LIKE se escapan: "%" no devuelve todo.
        assertThat(adapter.listar(FiltroProfesoresDto.builder().empresaId(10L).texto("%").build(),
                PageRequest.of(0, 20, Sort.by("nombre"))).getTotalElements()).isZero();
    }

    @Test
    void resumenDevuelveNombresPorUsuario() {
        List<ProfesorResumenDto> resumen = adapter.resumen(10L, List.of(5L, 1L, 4L));
        assertThat(resumen).extracting(ProfesorResumenDto::getNombreCompleto).containsExactlyInAnyOrder("Eva Arias", "Ana Pérez");
    }
}
