package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.feedback.FeedbackAssignmentRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.services.feedback.FeedbackEstados;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_assignment.FiltroFeedbackAssignmentDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_assignment.FeedbackAssignment;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers.FeedbackMapper;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.feedback.FeedbackAssignmentEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository.FeedbackAssignmentJpaRepository;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository.FeedbackResponseJpaRepository;
import com.kleverkids.formacion_academica.modules.estados.application.output.MotorEstadosPort;
import com.kleverkids.formacion_academica.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FeedbackAssignmentJpaAdapter implements FeedbackAssignmentRepositoryPort {

    private final FeedbackAssignmentJpaRepository assignmentRepository;
    private final FeedbackResponseJpaRepository responseRepository;
    private final FeedbackMapper mapper;
    private final MotorEstadosPort motorEstados;

    @Override
    public FeedbackAssignment crear(FeedbackAssignment assignment) {
        FeedbackAssignmentEntity entity = mapper.toEntity(assignment);
        entity.setId(null);
        entity.setEstadoId(motorEstados.estadoInicial(FeedbackEstados.MAQUINA_APLICACION));
        FeedbackAssignmentEntity guardada = assignmentRepository.save(entity);
        motorEstados.iniciarCiclo(FeedbackEstados.MAQUINA_APLICACION, FeedbackEstados.TIPO_ENTIDAD_APLICACION, guardada.getId());
        return mapper.toDomain(guardada);
    }

    @Override
    public FeedbackAssignment actualizar(FeedbackAssignment assignment) {
        return mapper.toDomain(assignmentRepository.save(mapper.toEntity(assignment)));
    }

    @Override
    public Optional<FeedbackAssignment> buscarPorId(Long id) {
        return assignmentRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<FeedbackAssignment> buscarPorIds(Collection<Long> ids) {
        return ids.isEmpty() ? List.of() : mapper.toAssignmentDomainList(assignmentRepository.findAllById(ids));
    }

    @Override
    public Page<FeedbackAssignment> listar(FiltroFeedbackAssignmentDto filtro, Pageable pageable) {
        return assignmentRepository.buscar(filtro.getFormId(), filtro.getClaseId(), filtro.getGrupoId(),
                filtro.getEstadoId(), pageable).map(mapper::toDomain);
    }

    @Override
    public FeedbackAssignment cambiarEstado(Long assignmentId, Long nuevoEstadoId, String motivo) {
        FeedbackAssignmentEntity entity = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new NotFoundException("Aplicación de feedback no encontrada"));
        Long resultado = motorEstados.moverAEstado(FeedbackEstados.MAQUINA_APLICACION,
                FeedbackEstados.TIPO_ENTIDAD_APLICACION, assignmentId, nuevoEstadoId, motivo);
        entity.setEstadoId(resultado);
        return mapper.toDomain(assignmentRepository.save(entity));
    }

    @Override
    public boolean tieneRespuestas(Long assignmentId) {
        return responseRepository.existsByAssignmentId(assignmentId);
    }
}
