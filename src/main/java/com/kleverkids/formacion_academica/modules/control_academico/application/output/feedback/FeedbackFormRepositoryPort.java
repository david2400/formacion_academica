package com.kleverkids.formacion_academica.modules.control_academico.application.output.feedback;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_form.FeedbackFormDetalleDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_form.FiltroFeedbackFormDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackForm;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackQuestion;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackQuestionOption;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackSection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/** Persistencia de formularios, secciones, preguntas y opciones. */
public interface FeedbackFormRepositoryPort {

    /** Crea el formulario y arranca su ciclo en el motor (estado inicial). */
    FeedbackForm crear(FeedbackForm form);

    FeedbackForm actualizar(FeedbackForm form);

    Optional<FeedbackForm> buscarPorId(Long id);

    List<FeedbackForm> buscarPorIds(Collection<Long> ids);

    Page<FeedbackForm> listar(FiltroFeedbackFormDto filtro, Pageable pageable);

    /** Mayor versión dentro de la familia (origen + sus copias). */
    int maxVersionDeFamilia(Long origenFormId);

    /** Lleva el formulario al estado pedido a través del motor y replica el resultado. */
    FeedbackForm cambiarEstado(Long formId, Long nuevoEstadoId, String motivo);

    /** Formulario completo (4 consultas, sin N+1). */
    Optional<FeedbackFormDetalleDto> detalle(Long formId);

    List<FeedbackSection> secciones(Long formId);

    Optional<FeedbackSection> buscarSeccion(Long sectionId);

    FeedbackSection guardarSeccion(FeedbackSection seccion);

    /** Borra (lógicamente) la sección, sus preguntas y sus opciones. */
    void eliminarSeccion(Long sectionId);

    List<FeedbackQuestion> preguntasDeSeccion(Long sectionId);

    Optional<FeedbackQuestion> buscarPregunta(Long questionId);

    /** Guarda la pregunta y reemplaza por completo sus opciones. */
    FeedbackQuestion guardarPregunta(FeedbackQuestion pregunta, List<FeedbackQuestionOption> opciones);

    void eliminarPregunta(Long questionId);

    void actualizarOrdenSecciones(List<FeedbackSection> secciones);

    void actualizarOrdenPreguntas(List<FeedbackQuestion> preguntas);
}
