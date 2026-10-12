package com.kleverkids.formacion_academica.modules.usuarios.application.services.profesor;

import com.kleverkids.formacion_academica.modules.usuarios.application.input.profesor.*;
import com.kleverkids.formacion_academica.modules.usuarios.application.output.profesor.ProfesorConsultaPort;
import com.kleverkids.formacion_academica.modules.usuarios.domain.dto.profesor.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ListarProfesoresService implements ListarProfesoresUseCase {

    private final ProfesorConsultaPort consulta;

    @Override
    @Transactional(readOnly = true)
    public Page<ProfesorDto> listar(FiltroProfesoresDto filtro, Pageable pageable) {
        Objects.requireNonNull(filtro.getEmpresaId(), "empresaId");
        filtro.setTexto(ProfesorSoporte.limpiar(filtro.getTexto()));
        return consulta.listar(filtro, pageable);
    }
}
