package com.kleverkids.formacion_academica.modules.usuarios.application.services.profesor;

import com.kleverkids.formacion_academica.modules.usuarios.application.input.profesor.*;
import com.kleverkids.formacion_academica.modules.usuarios.application.output.profesor.ProfesorConsultaPort;
import com.kleverkids.formacion_academica.modules.usuarios.domain.dto.profesor.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResumirProfesoresService implements ResumirProfesoresUseCase {

    private final ProfesorConsultaPort consulta;

    @Override
    @Transactional(readOnly = true)
    public List<ProfesorResumenDto> resumir(Long empresaId, Collection<Long> usuarioIds) {
        if (usuarioIds == null || usuarioIds.isEmpty()) {
            return List.of();
        }
        if (usuarioIds.size() > ProfesorSoporte.LIMITE_MAXIMO * 4) {
            throw new IllegalArgumentException("Pide como máximo " + (ProfesorSoporte.LIMITE_MAXIMO * 4) + " usuarios por consulta.");
        }
        return consulta.resumen(empresaId, usuarioIds);
    }
}
