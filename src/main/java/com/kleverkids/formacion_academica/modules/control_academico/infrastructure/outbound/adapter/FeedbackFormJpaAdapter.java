package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.feedback.FeedbackFormRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.services.feedback.FeedbackEstados;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_form.FeedbackFormDetalleDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_form.FiltroFeedbackFormDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackForm;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackQuestion;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackQuestionOption;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackScaleLevel;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackSection;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers.FeedbackMapper;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.feedback.FeedbackFormEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.feedback.FeedbackQuestionEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.feedback.FeedbackQuestionOptionEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.feedback.FeedbackSectionEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository.FeedbackFormJpaRepository;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository.FeedbackQuestionJpaRepository;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository.FeedbackQuestionOptionJpaRepository;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository.FeedbackScaleLevelJpaRepository;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository.FeedbackSectionJpaRepository;
import com.kleverkids.formacion_academica.modules.estados.application.output.MotorEstadosPort;
import com.kleverkids.formacion_academica.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Persistencia de formularios. El estado inicial y las transiciones los
 * decide el motor (FEEDBACK_FORM_LIFECYCLE), igual que GrupoJpaAdapter.
 */
@Component
@RequiredArgsConstructor
public class FeedbackFormJpaAdapter implements FeedbackFormRepositoryPort {

    private final FeedbackFormJpaRepository formRepository;
    private final FeedbackSectionJpaRepository sectionRepository;
    private final FeedbackQuestionJpaRepository questionRepository;
    private final FeedbackQuestionOptionJpaRepository optionRepository;
    private final FeedbackScaleLevelJpaRepository levelRepository;
    private final FeedbackMapper mapper;
    private final MotorEstadosPort motorEstados;

    @Override
    public FeedbackForm crear(FeedbackForm form) {
        FeedbackFormEntity entity = mapper.toEntity(form);
        entity.setId(null);
        entity.setEstadoId(motorEstados.estadoInicial(FeedbackEstados.MAQUINA_FORM));
        FeedbackFormEntity guardado = formRepository.save(entity);
        motorEstados.iniciarCiclo(FeedbackEstados.MAQUINA_FORM, FeedbackEstados.TIPO_ENTIDAD_FORM, guardado.getId());
        return mapper.toDomain(guardado);
    }

    @Override
    public FeedbackForm actualizar(FeedbackForm form) {
        return mapper.toDomain(formRepository.save(mapper.toEntity(form)));
    }

    @Override
    public Optional<FeedbackForm> buscarPorId(Long id) {
        return formRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<FeedbackForm> buscarPorIds(Collection<Long> ids) {
        return ids.isEmpty() ? List.of() : mapper.toFormDomainList(formRepository.findAllById(ids));
    }

    @Override
    public Page<FeedbackForm> listar(FiltroFeedbackFormDto filtro, Pageable pageable) {
        String texto = filtro.getTexto() == null || filtro.getTexto().isBlank() ? null : filtro.getTexto().trim();
        return formRepository.buscar(texto, filtro.getEstadoId(), pageable).map(mapper::toDomain);
    }

    @Override
    public int maxVersionDeFamilia(Long origenFormId) {
        return formRepository.maxVersionDeFamilia(origenFormId);
    }

    /** El motor valida la transición; el estado local se sobrescribe con lo que él devuelve. */
    @Override
    public FeedbackForm cambiarEstado(Long formId, Long nuevoEstadoId, String motivo) {
        FeedbackFormEntity entity = formRepository.findById(formId)
                .orElseThrow(() -> new NotFoundException("Formulario de feedback no encontrado"));
        Long resultado = motorEstados.moverAEstado(FeedbackEstados.MAQUINA_FORM, FeedbackEstados.TIPO_ENTIDAD_FORM,
                formId, nuevoEstadoId, motivo);
        entity.setEstadoId(resultado);
        return mapper.toDomain(formRepository.save(entity));
    }

    /** Formulario completo en 4 consultas (form, secciones, preguntas, opciones) + 1 de niveles si hay Likert. */
    @Override
    public Optional<FeedbackFormDetalleDto> detalle(Long formId) {
        Optional<FeedbackFormEntity> form = formRepository.findById(formId);
        if (form.isEmpty()) {
            return Optional.empty();
        }
        List<FeedbackSection> secciones = mapper.toSectionDomainList(sectionRepository.findByFormIdOrderByOrdenAsc(formId));
        List<FeedbackQuestion> preguntas = mapper.toQuestionDomainList(questionRepository.findByFormIdOrderBySectionIdAscOrdenAsc(formId));

        List<Long> preguntaIds = preguntas.stream().map(FeedbackQuestion::getId).toList();
        Map<Long, List<FeedbackQuestionOption>> opciones = preguntaIds.isEmpty() ? Map.of()
                : mapper.toOptionDomainList(optionRepository.findByQuestionIdInOrderByQuestionIdAscOrdenAsc(preguntaIds))
                .stream().collect(Collectors.groupingBy(FeedbackQuestionOption::getQuestionId));

        List<Long> escalaIds = preguntas.stream().map(FeedbackQuestion::getScaleId).filter(Objects::nonNull).distinct().toList();
        Map<Long, List<FeedbackScaleLevel>> niveles = escalaIds.isEmpty() ? Map.of()
                : mapper.toLevelDomainList(levelRepository.findByScaleIdInOrderByScaleIdAscOrdenAsc(escalaIds))
                .stream().collect(Collectors.groupingBy(FeedbackScaleLevel::getScaleId));

        Map<Long, List<FeedbackQuestion>> porSeccion = preguntas.stream()
                .collect(Collectors.groupingBy(FeedbackQuestion::getSectionId));

        List<FeedbackFormDetalleDto.SeccionDetalle> detalleSecciones = secciones.stream()
                .map(s -> FeedbackFormDetalleDto.SeccionDetalle.builder()
                        .seccion(s)
                        .preguntas(porSeccion.getOrDefault(s.getId(), List.of()).stream()
                                .map(q -> FeedbackFormDetalleDto.PreguntaDetalle.builder()
                                        .pregunta(q)
                                        .opciones(opciones.getOrDefault(q.getId(), List.of()))
                                        .niveles(q.getScaleId() == null ? List.of() : niveles.getOrDefault(q.getScaleId(), List.of()))
                                        .build())
                                .toList())
                        .build())
                .toList();

        return Optional.of(FeedbackFormDetalleDto.builder()
                .form(mapper.toDomain(form.get()))
                .secciones(detalleSecciones)
                .build());
    }

    @Override
    public List<FeedbackSection> secciones(Long formId) {
        return mapper.toSectionDomainList(sectionRepository.findByFormIdOrderByOrdenAsc(formId));
    }

    @Override
    public Optional<FeedbackSection> buscarSeccion(Long sectionId) {
        return sectionRepository.findById(sectionId).map(mapper::toDomain);
    }

    @Override
    public FeedbackSection guardarSeccion(FeedbackSection seccion) {
        return mapper.toDomain(sectionRepository.save(mapper.toEntity(seccion)));
    }

    @Override
    public void eliminarSeccion(Long sectionId) {
        List<FeedbackQuestionEntity> preguntas = questionRepository.findBySectionIdOrderByOrdenAsc(sectionId);
        preguntas.forEach(q -> optionRepository.deleteAll(optionRepository.findByQuestionId(q.getId())));
        questionRepository.deleteAll(preguntas);
        sectionRepository.deleteById(sectionId);
    }

    @Override
    public List<FeedbackQuestion> preguntasDeSeccion(Long sectionId) {
        return mapper.toQuestionDomainList(questionRepository.findBySectionIdOrderByOrdenAsc(sectionId));
    }

    @Override
    public Optional<FeedbackQuestion> buscarPregunta(Long questionId) {
        return questionRepository.findById(questionId).map(mapper::toDomain);
    }

    @Override
    public FeedbackQuestion guardarPregunta(FeedbackQuestion pregunta, List<FeedbackQuestionOption> opciones) {
        FeedbackQuestionEntity guardada = questionRepository.save(mapper.toEntity(pregunta));
        // Solo se llega aquí con formularios en BORRADOR, que no tienen respuestas:
        // reemplazar las opciones no deja respuestas huérfanas.
        optionRepository.deleteAll(optionRepository.findByQuestionId(guardada.getId()));
        List<FeedbackQuestionOptionEntity> nuevas = opciones.stream()
                .map(o -> {
                    FeedbackQuestionOptionEntity e = mapper.toEntity(o);
                    e.setId(null);
                    e.setQuestionId(guardada.getId());
                    return e;
                })
                .toList();
        optionRepository.saveAll(nuevas);
        return mapper.toDomain(guardada);
    }

    @Override
    public void eliminarPregunta(Long questionId) {
        optionRepository.deleteAll(optionRepository.findByQuestionId(questionId));
        questionRepository.deleteById(questionId);
    }

    @Override
    public void actualizarOrdenSecciones(List<FeedbackSection> secciones) {
        Map<Long, Integer> orden = secciones.stream().collect(Collectors.toMap(FeedbackSection::getId, FeedbackSection::getOrden));
        List<FeedbackSectionEntity> entidades = sectionRepository.findAllById(orden.keySet());
        entidades.forEach(e -> e.setOrden(orden.get(e.getId())));
        sectionRepository.saveAll(entidades);
    }

    @Override
    public void actualizarOrdenPreguntas(List<FeedbackQuestion> preguntas) {
        Map<Long, Integer> orden = preguntas.stream().collect(Collectors.toMap(FeedbackQuestion::getId, FeedbackQuestion::getOrden));
        List<FeedbackQuestionEntity> entidades = questionRepository.findAllById(orden.keySet());
        entidades.forEach(e -> e.setOrden(orden.get(e.getId())));
        questionRepository.saveAll(entidades);
    }
}
