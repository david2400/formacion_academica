package com.kleverkids.formacion_academica.modules.usuarios.application.services.profesor;

import com.kleverkids.formacion_academica.modules.usuarios.application.input.profesor.*;
import com.kleverkids.formacion_academica.modules.usuarios.application.output.profesor.ProfesorConsultaPort;
import com.kleverkids.formacion_academica.modules.usuarios.domain.dto.profesor.*;
import com.kleverkids.formacion_academica.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConsultarProfesorService implements ConsultarProfesorUseCase {

    private final ProfesorConsultaPort consulta;

    @Override
    @Transactional(readOnly = true)
    public ProfesorDto consultar(Long id) {
        return consulta.obtener(id).orElseThrow(() -> new NotFoundException("Profesor no encontrado."));
    }
}
