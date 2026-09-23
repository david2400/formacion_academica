package com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.jdbc;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.cuenta.CuentaUsuarioPort;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model.UsuarioAcceso;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Acceso a la identidad centralizada por SQL directo contra el esquema
 * {@code security} de access_control.
 *
 * <p>Ambos esquemas viven en el mismo servidor MySQL, así que basta con
 * prefijar el nombre del esquema en el SQL para llegar a las vistas de
 * access_control con el mismo {@link JdbcTemplate}/datasource que usa
 * gestion_alumnos para su propio esquema {@code academia}. No hace falta un
 * segundo DataSource ni que access_control esté levantado.
 *
 * <p>La identidad ({@link #obtenerUsuario}) se lee de la vista
 * {@code vw_account_usuario}, que es el contrato para ese caso: hace un JOIN
 * real entre {@code User} y {@code Client} y expone columnas propias, así que
 * un ALTER sobre esas tablas no rompe nada mientras la vista siga
 * devolviendo lo mismo.
 *
 * <p>La pertenencia a empresa ({@link #usuarioPerteneceAEmpresa}) consulta la
 * tabla {@code UserCompany} directamente: a diferencia de la identidad, aquí
 * no hay transformación real que justifique una vista intermedia (solo se
 * lee user_id/company_id/expires_at tal cual están en la tabla), así que se
 * evita el objeto de base de datos adicional. Esto sí acopla el adapter a la
 * forma de esa tabla (nombre y columnas); si UserCompany cambia de forma
 * relevante para esta consulta, hay que actualizar este SQL.
 */
@Component
public class CuentaUsuarioJdbcAdapter implements CuentaUsuarioPort {

    private static final RowMapper<UsuarioAcceso> USUARIO_ROW_MAPPER = (rs, rowNum) -> UsuarioAcceso.builder()
            .usuarioId(rs.getLong("usuario_id"))
            .usuario(rs.getString("usuario"))
            .estadoUsuario(rs.getString("estado_usuario"))
            .clienteId((Long) rs.getObject("cliente_id"))
            .primerNombre(rs.getString("primer_nombre"))
            .segundoNombre(rs.getString("segundo_nombre"))
            .primerApellido(rs.getString("primer_apellido"))
            .segundoApellido(rs.getString("segundo_apellido"))
            .tipoDocumento(rs.getString("tipo_documento"))
            .numeroDocumento(rs.getString("numero_documento"))
            .sexo(rs.getString("sexo"))
            .genero(rs.getString("genero"))
            .estadoCliente(rs.getString("estado_cliente"))
            .build();

    private final JdbcTemplate jdbc;
    private final String esquema;

    public CuentaUsuarioJdbcAdapter(JdbcTemplate jdbc, CuentaUsuarioProperties propiedades) {
        this.jdbc = jdbc;
        this.esquema = propiedades.getEsquema();
    }

    @Override
    public Optional<UsuarioAcceso> obtenerUsuario(Long usuarioId) {
        List<UsuarioAcceso> resultado = jdbc.query(
                "SELECT usuario_id, usuario, estado_usuario, cliente_id, primer_nombre, segundo_nombre, "
                        + "primer_apellido, segundo_apellido, tipo_documento, numero_documento, sexo, genero, estado_cliente "
                        + "FROM " + esquema + ".vw_account_usuario WHERE usuario_id = ?",
                USUARIO_ROW_MAPPER, usuarioId);
        return resultado.stream().findFirst();
    }

    @Override
    public boolean usuarioPerteneceAEmpresa(Long usuarioId, Long empresaId) {
        if (usuarioId == null || empresaId == null) {
            return false;
        }
        // Consulta directa a UserCompany (no hay vista intermedia): "vigente"
        // se calcula aquí igual que en UserCompanyEntity.isExpired(), invertido.
        Integer coincidencias = jdbc.queryForObject(
                "SELECT COUNT(*) FROM " + esquema + ".UserCompany "
                        + "WHERE user_id = ? AND company_id = ? "
                        + "AND (expires_at IS NULL OR expires_at > NOW())",
                Integer.class, usuarioId, empresaId);
        return coincidencias != null && coincidencias > 0;
    }

    @Override
    public Optional<UsuarioAcceso> buscarPorDocumento(String tipoDocumento, String numeroDocumento) {
        if (tipoDocumento == null || numeroDocumento == null) {
            return Optional.empty();
        }
        List<UsuarioAcceso> resultado = jdbc.query(
                "SELECT usuario_id, usuario, estado_usuario, cliente_id, primer_nombre, segundo_nombre, "
                        + "primer_apellido, segundo_apellido, tipo_documento, numero_documento, sexo, genero, estado_cliente "
                        + "FROM " + esquema + ".vw_account_usuario WHERE tipo_documento = ? AND numero_documento = ?",
                USUARIO_ROW_MAPPER, tipoDocumento, numeroDocumento);
        return resultado.stream().findFirst();
    }
}
