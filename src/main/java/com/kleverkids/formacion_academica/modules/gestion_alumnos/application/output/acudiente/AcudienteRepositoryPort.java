package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.acudiente;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model.Acudiente;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.acudiente.ActualizarAcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.acudiente.CrearAcudienteDto;

import java.util.List;
import java.util.Optional;

public interface AcudienteRepositoryPort {

    Acudiente guardar(CrearAcudienteDto request);

    Acudiente actualizar(ActualizarAcudienteDto request);

    Optional<Acudiente> obtenerPorId(Long acudienteId);

    List<Acudiente> listarPorEstudiante(Long estudianteId);

    boolean existePrincipalParaEstudiante(Long estudianteId, Long excluirAcudienteId);

    void eliminar(Long acudienteId);
}
