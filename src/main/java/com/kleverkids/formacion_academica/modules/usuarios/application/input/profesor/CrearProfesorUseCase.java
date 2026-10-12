package com.kleverkids.formacion_academica.modules.usuarios.application.input.profesor;

import com.kleverkids.formacion_academica.modules.usuarios.domain.dto.profesor.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;

public interface CrearProfesorUseCase {
    ProfesorDto crear(CrearProfesorDto dto);
}
