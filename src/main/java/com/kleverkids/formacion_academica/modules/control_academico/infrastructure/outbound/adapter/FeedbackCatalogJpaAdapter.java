package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.feedback.FeedbackCatalogRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_form.FeedbackScaleDetalleDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackCriterion;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackScale;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackScaleLevel;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers.FeedbackMapper;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.feedback.FeedbackScaleEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.feedback.FeedbackScaleLevelEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository.FeedbackCriterionJpaRepository;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository.FeedbackQuestionJpaRepository;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository.FeedbackScaleJpaRepository;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository.FeedbackScaleLevelJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FeedbackCatalogJpaAdapter implements FeedbackCatalogRepositoryPort {

    private final FeedbackScaleJpaRepository scaleRepository;
    private final FeedbackScaleLevelJpaRepository levelRepository;
    private final FeedbackCriterionJpaRepository criterionRepository;
    private final FeedbackQuestionJpaRepository questionRepository;
    private final FeedbackMapper mapper;

    @Override
    public List<FeedbackScaleDetalleDto> listarEscalas() {
        List<FeedbackScaleEntity> escalas = scaleRepository.findAllByOrderByNombreAsc();
        if (escalas.isEmpty()) {
            return List.of();
        }
        Map<Long, List<FeedbackScaleLevel>> niveles = mapper.toLevelDomainList(levelRepository
                        .findByScaleIdInOrderByScaleIdAscOrdenAsc(escalas.stream().map(FeedbackScaleEntity::getId).toList()))
                .stream().collect(Collectors.groupingBy(FeedbackScaleLevel::getScaleId));
        return escalas.stream()
                .map(e -> FeedbackScaleDetalleDto.builder()
                        .escala(mapper.toDomain(e))
                        .niveles(niveles.getOrDefault(e.getId(), List.of()))
                        .build())
                .toList();
    }

    @Override
    public Optional<FeedbackScaleDetalleDto> buscarEscala(Long scaleId) {
        return scaleRepository.findById(scaleId).map(e -> FeedbackScaleDetalleDto.builder()
                .escala(mapper.toDomain(e))
                .niveles(mapper.toLevelDomainList(levelRepository.findByScaleIdInOrderByScaleIdAscOrdenAsc(List.of(scaleId))))
                .build());
    }

    @Override
    public FeedbackScaleDetalleDto guardarEscala(FeedbackScale escala, List<FeedbackScaleLevel> niveles) {
        FeedbackScaleEntity guardada = scaleRepository.save(mapper.toEntity(escala));
        levelRepository.borrarFisicamentePorEscala(guardada.getId());
        List<FeedbackScaleLevelEntity> nuevas = niveles.stream()
                .map(n -> {
                    FeedbackScaleLevelEntity e = mapper.toEntity(n);
                    e.setId(null);
                    e.setScaleId(guardada.getId());
                    return e;
                })
                .toList();
        levelRepository.saveAll(nuevas);
        return buscarEscala(guardada.getId()).orElseThrow();
    }

    @Override
    public boolean escalaEnUsoPorFormulariosNoEditables(Long scaleId, List<Long> estadosEditables) {
        // NOT IN () no es SQL válido: sin estados editables conocidos, cualquier uso cuenta.
        List<Long> editables = estadosEditables.isEmpty() ? List.of(-1L) : estadosEditables;
        return questionRepository.escalaEnUsoFueraDe(scaleId, editables);
    }

    @Override
    public List<FeedbackCriterion> listarCriterios() {
        return mapper.toCriterionDomainList(criterionRepository.findAllByOrderByNombreAsc());
    }

    @Override
    public Optional<FeedbackCriterion> buscarCriterio(Long criterionId) {
        return criterionRepository.findById(criterionId).map(mapper::toDomain);
    }

    @Override
    public Optional<FeedbackCriterion> buscarCriterioPorCodigo(String codigo) {
        return criterionRepository.findByCodigo(codigo).map(mapper::toDomain);
    }

    @Override
    public FeedbackCriterion guardarCriterio(FeedbackCriterion criterio) {
        return mapper.toDomain(criterionRepository.save(mapper.toEntity(criterio)));
    }
}
