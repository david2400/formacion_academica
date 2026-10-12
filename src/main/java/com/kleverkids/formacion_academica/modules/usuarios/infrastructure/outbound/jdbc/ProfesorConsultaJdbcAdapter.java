package com.kleverkids.formacion_academica.modules.usuarios.infrastructure.outbound.jdbc;

import com.kleverkids.formacion_academica.modules.usuarios.application.output.profesor.ProfesorConsultaPort;
import com.kleverkids.formacion_academica.modules.usuarios.domain.dto.profesor.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;

/**
 * Lecturas de profesores unidas a la identidad de access_control, en el mismo servidor MySQL
 * (mismo mecanismo que el motor de estados): la identidad sale de las tablas {@code User} y
 * {@code Client}; la pertenencia a la empresa, de {@code User.company_id} o de
 * {@code user_company} vigente.
 *
 * <p>Todo se resuelve en una sola consulta por pantalla (sin N+1) y la búsqueda y la
 * paginación ocurren en la base de datos.
 */
@Component
public class ProfesorConsultaJdbcAdapter implements ProfesorConsultaPort {

    private static final String NOMBRE =
            "CONCAT_WS(' ', sc.first_name, sc.second_name, sc.first_last_name, sc.second_last_name)";

    /** Orden permitido desde la API (nunca se interpola texto del cliente). */
    private static final Map<String, String> ORDEN = Map.of(
            "nombre", "sc.first_last_name %1$s, sc.first_name %1$s",
            "fechaVinculacion", "p.fecha_vinculacion %1$s",
            "createdAt", "p.created_at %1$s");

    private static final RowMapper<ProfesorDto> PROFESOR = (rs, i) -> {
        Date fecha = rs.getDate("fecha_vinculacion");
        Timestamp creado = rs.getTimestamp("created_at");
        return ProfesorDto.builder()
                .id(rs.getLong("id"))
                .usuarioId(rs.getLong("usuario_id"))
                .empresaId(rs.getLong("empresa_id"))
                .activo(rs.getBoolean("activo"))
                .fechaVinculacion(fecha == null ? null : fecha.toLocalDate())
                .observaciones(rs.getString("observaciones"))
                .createdAt(creado == null ? null : creado.toInstant())
                .usuario(rs.getString("usuario"))
                .nombreCompleto(vacioANull(rs.getString("nombre_completo")))
                .tipoDocumento(rs.getString("tipo_documento"))
                .numeroDocumento(rs.getString("numero_documento"))
                .estadoUsuario(rs.getString("estado_usuario"))
                .build();
    };

    private final NamedParameterJdbcTemplate jdbc;
    private final String esquema;

    public ProfesorConsultaJdbcAdapter(NamedParameterJdbcTemplate jdbc,
                                       @Value("${access-control.esquema:security}") String esquema) {
        // Se interpola en el SQL (un esquema no se puede parametrizar): solo desde configuración.
        if (esquema == null || !esquema.matches("[A-Za-z0-9_]{1,64}")) {
            throw new IllegalArgumentException("access-control.esquema inválido: " + esquema);
        }
        this.jdbc = jdbc;
        this.esquema = esquema;
    }

    @Override
    public Page<ProfesorDto> listar(FiltroProfesoresDto filtro, Pageable pageable) {
        MapSqlParameterSource params = new MapSqlParameterSource("empresaId", filtro.getEmpresaId());
        StringBuilder where = new StringBuilder(" WHERE p.eliminado = 0 AND p.empresa_id = :empresaId");
        if (filtro.getActivo() != null) {
            where.append(" AND p.activo = :activo");
            params.addValue("activo", filtro.getActivo());
        }
        agregarBusqueda(where, params, filtro.getTexto());

        String from = " FROM profesores p" + identidad("p.usuario_id");
        Long total = jdbc.queryForObject("SELECT COUNT(*)" + from + where, params, Long.class);
        if (total == null || total == 0) {
            return Page.empty(pageable);
        }
        params.addValue("limite", pageable.getPageSize()).addValue("desde", pageable.getOffset());
        List<ProfesorDto> filas = jdbc.query(columnasProfesor() + from + where
                + " ORDER BY " + orden(pageable.getSort()) + ", p.id LIMIT :limite OFFSET :desde", params, PROFESOR);
        return new PageImpl<>(filas, pageable, total);
    }

    @Override
    public Optional<ProfesorDto> obtener(Long id) {
        List<ProfesorDto> filas = jdbc.query(columnasProfesor()
                        + " FROM profesores p" + identidad("p.usuario_id")
                        + " WHERE p.eliminado = 0 AND p.id = :id",
                new MapSqlParameterSource("id", id), PROFESOR);
        return filas.stream().findFirst();
    }

    @Override
    public List<UsuarioDisponibleDto> usuariosDisponibles(Long empresaId, String texto, int limite) {
        MapSqlParameterSource params = new MapSqlParameterSource("empresaId", empresaId).addValue("limite", limite);
        StringBuilder where = new StringBuilder(" WHERE " + perteneceAEmpresa("su.id_user")
                + " AND NOT EXISTS (SELECT 1 FROM profesores p WHERE p.usuario_id = su.id_user"
                + " AND p.empresa_id = :empresaId AND p.eliminado = 0)");
        agregarBusqueda(where, params, texto);
        return jdbc.query("SELECT su.id_user AS usuario_id, su.username AS usuario, su.status AS estado_usuario,"
                        + " sc.type_id AS tipo_documento, sc.card_id AS numero_documento, " + NOMBRE + " AS nombre_completo"
                        + " FROM " + esquema + ".`User` su LEFT JOIN " + esquema + ".`Client` sc ON sc.id_client = su.client_id"
                        + where + " ORDER BY sc.first_last_name, sc.first_name, su.username LIMIT :limite",
                params, (rs, i) -> UsuarioDisponibleDto.builder()
                        .usuarioId(rs.getLong("usuario_id"))
                        .usuario(rs.getString("usuario"))
                        .nombreCompleto(vacioANull(rs.getString("nombre_completo")))
                        .tipoDocumento(rs.getString("tipo_documento"))
                        .numeroDocumento(rs.getString("numero_documento"))
                        .estadoUsuario(rs.getString("estado_usuario"))
                        .build());
    }

    @Override
    public List<ProfesorResumenDto> resumen(Long empresaId, Collection<Long> usuarioIds) {
        MapSqlParameterSource params = new MapSqlParameterSource("ids", usuarioIds);
        String filtroEmpresa = "";
        if (empresaId != null) {
            filtroEmpresa = " AND p.empresa_id = :empresaId";
            params.addValue("empresaId", empresaId);
        }
        return jdbc.query("SELECT p.id, p.usuario_id, p.activo, " + NOMBRE + " AS nombre_completo, su.username AS usuario"
                        + " FROM profesores p" + identidad("p.usuario_id")
                        + " WHERE p.eliminado = 0 AND p.usuario_id IN (:ids)" + filtroEmpresa,
                params, (rs, i) -> {
                    String nombre = vacioANull(rs.getString("nombre_completo"));
                    return ProfesorResumenDto.builder()
                            .profesorId(rs.getLong("id"))
                            .usuarioId(rs.getLong("usuario_id"))
                            .activo(rs.getBoolean("activo"))
                            .nombreCompleto(nombre != null ? nombre : rs.getString("usuario"))
                            .build();
                });
    }

    @Override
    public boolean usuarioPerteneceAEmpresa(Long usuarioId, Long empresaId) {
        if (usuarioId == null || empresaId == null) {
            return false;
        }
        Integer n = jdbc.queryForObject("SELECT COUNT(*) FROM " + esquema + ".`User` x WHERE x.id_user = :usuarioId AND "
                        + perteneceAEmpresa("x.id_user"),
                new MapSqlParameterSource("usuarioId", usuarioId).addValue("empresaId", empresaId), Integer.class);
        return n != null && n > 0;
    }

    // ─── SQL compartido ─────────────────────────────────────────────────────

    private static String columnasProfesor() {
        return "SELECT p.id, p.usuario_id, p.empresa_id, p.activo, p.fecha_vinculacion, p.observaciones, p.created_at, "
                + "su.username AS usuario, su.status AS estado_usuario, sc.type_id AS tipo_documento,"
                + " sc.card_id AS numero_documento, " + NOMBRE + " AS nombre_completo";
    }

    /**
     * Identidad leída directo de las tablas de access_control ({@code User} + {@code Client}), por
     * su clave primaria. Antes se usaba la vista {@code vw_account_usuario}, pero si la vista no
     * existe o queda inválida (tablas renombradas/recreadas, error 1356) el listado fallaba con 500;
     * es el mismo motivo por el que el motor de estados dejó de usar vistas.
     */
    private String identidad(String columnaUsuario) {
        return " LEFT JOIN " + esquema + ".`User` su ON su.id_user = " + columnaUsuario
                + " LEFT JOIN " + esquema + ".`Client` sc ON sc.id_client = su.client_id";
    }

    /**
     * User.company_id (empresa propia del usuario) o una asignación vigente en UserCompany,
     * igual que valida gestion_alumnos (CuentaUsuarioJdbcAdapter.usuarioPerteneceAEmpresa).
     */
    private String perteneceAEmpresa(String columnaUsuario) {
        return "(EXISTS (SELECT 1 FROM " + esquema + ".`User` cu WHERE cu.id_user = " + columnaUsuario
                + " AND cu.company_id = :empresaId)"
                + " OR EXISTS (SELECT 1 FROM " + esquema + ".user_company uc WHERE uc.user_id = " + columnaUsuario
                + " AND uc.company_id = :empresaId AND (uc.expires_at IS NULL OR uc.expires_at > NOW())))";
    }

    private void agregarBusqueda(StringBuilder where, MapSqlParameterSource params, String texto) {
        if (texto == null || texto.isBlank()) {
            return;
        }
        where.append(" AND (").append(NOMBRE).append(" LIKE :texto ESCAPE '!' OR su.username LIKE :texto ESCAPE '!'"
                + " OR sc.card_id LIKE :texto ESCAPE '!')");
        params.addValue("texto", "%" + texto.trim().replace("!", "!!").replace("%", "!%").replace("_", "!_") + "%");
    }

    private static String orden(Sort sort) {
        List<String> partes = new ArrayList<>();
        for (Sort.Order o : sort) {
            String plantilla = ORDEN.get(o.getProperty());
            if (plantilla != null) {
                partes.add(String.format(plantilla, o.isAscending() ? "ASC" : "DESC"));
            }
        }
        return partes.isEmpty() ? String.format(ORDEN.get("nombre"), "ASC") : String.join(", ", partes);
    }

    private static String vacioANull(String s) {
        return s == null || s.isBlank() ? null : s;
    }
}
