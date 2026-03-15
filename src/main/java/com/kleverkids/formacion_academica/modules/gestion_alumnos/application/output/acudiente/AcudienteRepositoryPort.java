package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.acudiente;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.acudiente.AcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.acudiente.ActualizarAcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.acudiente.CrearAcudienteDto;

import java.util.List;
import java.util.Optional;

public interface AcudienteRepositoryPort {

    AcudienteDto guardar(CrearAcudienteDto request);

    AcudienteDto actualizar(ActualizarAcudienteDto request);

    Optional<AcudienteDto> obtenerPorId(Long acudienteId);

    List<AcudienteDto> listarPorEstudiante(Long estudianteId);

    boolean existePrincipalParaEstudiante(Long estudianteId, Long excluirAcudienteId);

    void eliminar(Long acudienteId);
}
