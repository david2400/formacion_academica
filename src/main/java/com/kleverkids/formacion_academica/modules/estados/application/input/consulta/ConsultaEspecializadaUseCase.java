package com.kleverkids.formacion_academica.modules.estados.application.input.consulta;

import com.kleverkids.formacion_academica.modules.estados.domain.dto.EstadoDto;
import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.entity.EntidadEstadoEntity;

import java.util.List;

public interface ConsultaEspecializadaUseCase {
    List<EstadoDto> listarEntidadesConEstado(Integer estadoId);
    List<EstadoDto> obtenerWorkflowCompleto(Long idModulo);
    List<EntidadEstadoEntity> listarEntidadesPorModulo(String entidadTipo, Long idModulo);
    boolean tieneEstadosConfigurados(Long idModulo);
}
