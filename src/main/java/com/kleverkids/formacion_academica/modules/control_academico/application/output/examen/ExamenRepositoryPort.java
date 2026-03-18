package com.kleverkids.formacion_academica.modules.control_academico.application.output.examen;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.CalificacionPersonalizadaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.CrearExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen.Examen;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.RegistrarCalificacionPersonalizadaDto;

public interface ExamenRepositoryPort {

    Examen guardar(CrearExamenDto request);

    CalificacionPersonalizadaDto registrarCalificacion(RegistrarCalificacionPersonalizadaDto request);
}
