package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.acudiente;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.acudiente.AcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.acudiente.ActualizarAcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.acudiente.CrearAcudienteDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AcudienteRepositoryPort {

    AcudienteDto guardar(CrearAcudienteDto request);

    AcudienteDto actualizar(ActualizarAcudienteDto request);

    Optional<AcudienteDto> obtenerPorId(UUID acudienteId);

    List<AcudienteDto> listarPorEstudiante(UUID estudianteId);

    boolean existePrincipalParaEstudiante(UUID estudianteId, UUID excluirAcudienteId);

    void eliminar(UUID acudienteId);
}
