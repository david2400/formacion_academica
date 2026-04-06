package com.kleverkids.formacion_academica.modules.estados.application.services;

import com.kleverkids.formacion_academica.modules.estados.application.input.consulta.ConsultaEspecializadaUseCase;
import com.kleverkids.formacion_academica.modules.estados.domain.dto.EstadoDto;
import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.entity.EntidadEstadoEntity;
import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.entity.EstadoEntity;
import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.repository.EntidadEstadoJpaRepository;
import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.repository.EstadoJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ConsultaEspecializadaService implements ConsultaEspecializadaUseCase {

    private final EstadoJpaRepository estadoRepository;
    private final EntidadEstadoJpaRepository entidadEstadoRepository;

    @Override
    public List<EstadoDto> listarEntidadesConEstado(Integer estadoId) {
        // Simplified implementation - return empty list for now
        // Need proper mapper for EntidadEstadoEntity -> EstadoDto
        return List.of();
    }

    @Override
    public List<EstadoDto> obtenerWorkflowCompleto(Long idModulo) {
        // Simplified implementation - return empty list for now
        // Need proper mapper for EstadoEntity -> EstadoDto
        return List.of();
    }

    @Override
    public List<EntidadEstadoEntity> listarEntidadesPorModulo(String entidadTipo, Long idModulo) {
        return entidadEstadoRepository.findEntidadesPorTipoYModulo(entidadTipo, idModulo);
    }

    @Override
    public boolean tieneEstadosConfigurados(Long idModulo) {
        return estadoRepository.existsByCodigoAndIdModuloAndActivoTrue("ACTIVO", idModulo);
    }
}
