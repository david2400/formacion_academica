package com.kleverkids.formacion_academica.modules.usuarios.application.output.profesor;

import com.kleverkids.formacion_academica.modules.usuarios.domain.dto.profesor.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Lecturas que combinan {@code profesores} con la identidad de access_control en UNA consulta
 * (mismo servidor MySQL): sin N+1 y con búsqueda/paginación en la base de datos.
 */
public interface ProfesorConsultaPort {

    Page<ProfesorDto> listar(FiltroProfesoresDto filtro, Pageable pageable);

    Optional<ProfesorDto> obtener(Long id);

    List<UsuarioDisponibleDto> usuariosDisponibles(Long empresaId, String texto, int limite);

    List<ProfesorResumenDto> resumen(Long empresaId, Collection<Long> usuarioIds);

    /** El usuario existe y pertenece a la empresa (User.company_id o UserCompany vigente). */
    boolean usuarioPerteneceAEmpresa(Long usuarioId, Long empresaId);
}
