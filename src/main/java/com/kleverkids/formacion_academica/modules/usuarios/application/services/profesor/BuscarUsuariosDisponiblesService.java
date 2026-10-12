package com.kleverkids.formacion_academica.modules.usuarios.application.services.profesor;

import com.kleverkids.formacion_academica.modules.usuarios.application.input.profesor.*;
import com.kleverkids.formacion_academica.modules.usuarios.application.output.profesor.ProfesorConsultaPort;
import com.kleverkids.formacion_academica.modules.usuarios.domain.dto.profesor.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BuscarUsuariosDisponiblesService implements BuscarUsuariosDisponiblesUseCase {

    private final ProfesorConsultaPort consulta;

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDisponibleDto> buscarDisponibles(Long empresaId, String texto, int limite) {
        return consulta.usuariosDisponibles(empresaId, ProfesorSoporte.limpiar(texto), Math.min(Math.max(limite, 1), ProfesorSoporte.LIMITE_MAXIMO));
    }
}
