package com.kleverkids.formacion_academica.modules.estados.application.services;

import com.kleverkids.formacion_academica.modules.estados.application.input.entidad.GestionarEntidadEstadoUseCase;
import com.kleverkids.formacion_academica.modules.estados.domain.dto.CambioEstadoRequest;
import com.kleverkids.formacion_academica.modules.estados.domain.dto.EstadoDto;
import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.entity.EntidadEstadoEntity;
import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.entity.EstadoEntity;
import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.entity.EstadoHistorialEntity;
import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.repository.EntidadEstadoJpaRepository;
import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.repository.EstadoHistorialJpaRepository;
import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.repository.EstadoJpaRepository;
import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.repository.EstadoTransicionJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class EntidadEstadoService implements GestionarEntidadEstadoUseCase {

    private final EstadoJpaRepository estadoRepository;
    private final EstadoTransicionJpaRepository transicionRepository;
    private final EntidadEstadoJpaRepository entidadEstadoRepository;
    private final EstadoHistorialJpaRepository historialRepository;

    @Override
    public void asignarEstadoInicial(String entidadTipo, Long entidadId, Long idModulo, Long idUsuarioCambio) {
        log.info("Asignando estado inicial para {} {} en módulo {}", entidadTipo, entidadId, idModulo);
        
        // Buscar estados iniciales activos para el módulo
        List<EstadoEntity> estadosIniciales = estadoRepository.findEstadosInicialesPorModulo(idModulo);
        
        if (estadosIniciales.isEmpty()) {
            throw new RuntimeException("No hay estados iniciales configurados para el módulo " + idModulo);
        }
        
        // Tomar el primer estado inicial (puede haber lógica para seleccionar el correcto)
        EstadoEntity estadoInicial = estadosIniciales.get(0);
        
        crearAsignacionEstado(entidadTipo, entidadId, estadoInicial, null, idUsuarioCambio, "Asignación de estado inicial");
    }

    @Override
    public Optional<EstadoDto> obtenerEstadoActual(String entidadTipo, Long entidadId) {
        // Simplified implementation - return empty for now
        return Optional.empty();
    }

    @Override
    public List<EstadoDto> listarHistorialEstados(String entidadTipo, Long entidadId) {
        // Simplified implementation - return empty list for now
        return List.of();
    }

    @Override
    public void cambiarEstadoConValidacion(CambioEstadoRequest request) {
        // Simplified implementation - just log for now
        log.info("Cambiando estado con validación para request: {}", request);
        throw new UnsupportedOperationException("Not implemented yet");
    }

    // ========================================
    // MÉTODOS PRIVADOS
    // ========================================

    private EntidadEstadoEntity crearAsignacionEstado(String entidadTipo, Long entidadId, EstadoEntity estado, 
                                                   EstadoEntity estadoAnterior, Long idUsuarioCambio, String motivo) {
        
        // Crear nueva asignación de estado
        EntidadEstadoEntity nuevaAsignacion = new EntidadEstadoEntity();
        nuevaAsignacion.setUuid(UUID.randomUUID().toString());
        nuevaAsignacion.setEntidadTipo(entidadTipo);
        nuevaAsignacion.setEntidadId(entidadId);
        nuevaAsignacion.setEstado(estado);
        nuevaAsignacion.setEstadoAnterior(estadoAnterior);
        nuevaAsignacion.setIdUsuarioCambio(idUsuarioCambio);
        nuevaAsignacion.setMotivo(motivo);
        nuevaAsignacion.setActivo(true);
        
        // Guardar la asignación
        EntidadEstadoEntity guardada = entidadEstadoRepository.save(nuevaAsignacion);
        
        // Crear registro en historial
        crearRegistroHistorial(guardada, estadoAnterior, estado, idUsuarioCambio, motivo);
        
        log.info("Estado asignado: {} {} -> {} (motivo: {})", entidadTipo, entidadId, estado.getCodigo(), motivo);
        
        return guardada;
    }

    private void crearRegistroHistorial(EntidadEstadoEntity entidadEstado, EstadoEntity estadoAnterior, 
                                      EstadoEntity estadoNuevo, Long idUsuarioCambio, String motivo) {
        
        EstadoHistorialEntity historial = new EstadoHistorialEntity();
        historial.setUuid(UUID.randomUUID().toString());
        historial.setEntidadEstado(entidadEstado);
        historial.setEntidadTipo(entidadEstado.getEntidadTipo());
        historial.setEntidadId(entidadEstado.getEntidadId());
        historial.setEstadoAnterior(estadoAnterior);
        historial.setEstadoNuevo(estadoNuevo);
        historial.setIdUsuarioCambio(idUsuarioCambio);
        historial.setMotivo(motivo);
        historial.setIdEmpresa(entidadEstado.getIdEmpresa());
        // Note: createdAt will be set automatically by AuditInfo
        
        historialRepository.save(historial);
    }
}
