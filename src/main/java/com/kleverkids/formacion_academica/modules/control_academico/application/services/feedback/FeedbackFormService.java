package com.kleverkids.formacion_academica.modules.control_academico.application.services.feedback;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.feedback_form.*;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.feedback.FeedbackCatalogRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.feedback.FeedbackFormRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.services.feedback.handler.DefinicionPreguntaFeedback;
import com.kleverkids.formacion_academica.modules.control_academico.application.services.feedback.handler.FeedbackQuestionTypeHandlerRegistry;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_form.*;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackForm;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackQuestion;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackQuestionOption;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackScaleLevel;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackSection;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.valueobject.FeedbackQuestionType;
import com.kleverkids.formacion_academica.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Autoría de formularios: el formulario, sus secciones y sus preguntas.
 *
 * <p>Regla central: solo un formulario en BORRADOR se modifica. Uno PUBLICADO
 * es inmutable porque las respuestas apuntan a sus preguntas; para cambiarlo
 * se duplica como nueva versión.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FeedbackFormService implements CrearFeedbackFormUseCase, ActualizarFeedbackFormUseCase,
        ConsultarFeedbackFormUseCase, ListarFeedbackFormsUseCase, DuplicarFeedbackFormUseCase,
        CambiarEstadoFeedbackFormUseCase, AgregarSeccionFeedbackUseCase, ActualizarSeccionFeedbackUseCase,
        EliminarSeccionFeedbackUseCase, ReordenarSeccionesFeedbackUseCase, AgregarPreguntaFeedbackUseCase,
        ActualizarPreguntaFeedbackUseCase, EliminarPreguntaFeedbackUseCase, ReordenarPreguntasFeedbackUseCase {

    private static final String NO_EDITABLE =
            "Solo se pueden modificar formularios en borrador. Duplica el formulario para crear una nueva versión.";

    private final FeedbackFormRepositoryPort formRepository;
    private final FeedbackCatalogRepositoryPort catalogRepository;
    private final FeedbackQuestionTypeHandlerRegistry handlers;
    private final FeedbackEstados estados;

    // ─── Formulario ──────────────────────────────────────────────────────────

    @Override
    @Transactional
    public FeedbackForm crear(CrearFeedbackFormDto dto) {
        FeedbackForm form = FeedbackForm.builder()
                .titulo(dto.getTitulo().trim())
                .descripcion(dto.getDescripcion())
                .anonimo(dto.isAnonimo())
                .permiteMultiplesRespuestas(dto.isPermiteMultiplesRespuestas())
                .version(1)
                .build();
        FeedbackForm creado = formRepository.crear(form);
        log.info("Formulario de feedback {} creado", creado.getId());
        return creado;
    }

    @Override
    @Transactional
    public FeedbackForm actualizar(Long formId, ActualizarFeedbackFormDto dto) {
        FeedbackForm form = formEditable(formId);
        form.setTitulo(dto.getTitulo().trim());
        form.setDescripcion(dto.getDescripcion());
        form.setAnonimo(dto.isAnonimo());
        form.setPermiteMultiplesRespuestas(dto.isPermiteMultiplesRespuestas());
        return formRepository.actualizar(form);
    }

    @Override
    @Transactional(readOnly = true)
    public FeedbackFormDetalleDto consultar(Long formId) {
        return formRepository.detalle(formId)
                .orElseThrow(() -> new NotFoundException("Formulario de feedback no encontrado"));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FeedbackForm> listar(FiltroFeedbackFormDto filtro, Pageable pageable) {
        return formRepository.listar(filtro == null ? new FiltroFeedbackFormDto() : filtro, pageable);
    }

    /**
     * Copia completa (secciones, preguntas, opciones) como nueva versión en
     * BORRADOR de la misma familia. Las respuestas de la versión original no
     * se tocan: siguen apuntando a sus propias preguntas.
     */
    @Override
    @Transactional
    public FeedbackForm duplicar(Long formId) {
        FeedbackFormDetalleDto original = consultar(formId);
        FeedbackForm base = original.getForm();
        Long raiz = base.getOrigenFormId() != null ? base.getOrigenFormId() : base.getId();

        FeedbackForm copia = formRepository.crear(FeedbackForm.builder()
                .titulo(base.getTitulo())
                .descripcion(base.getDescripcion())
                .anonimo(base.isAnonimo())
                .permiteMultiplesRespuestas(base.isPermiteMultiplesRespuestas())
                .version(formRepository.maxVersionDeFamilia(raiz) + 1)
                .origenFormId(raiz)
                .build());

        for (FeedbackFormDetalleDto.SeccionDetalle s : original.getSecciones()) {
            FeedbackSection seccion = formRepository.guardarSeccion(FeedbackSection.builder()
                    .formId(copia.getId())
                    .titulo(s.getSeccion().getTitulo())
                    .descripcion(s.getSeccion().getDescripcion())
                    .orden(s.getSeccion().getOrden())
                    .build());
            for (FeedbackFormDetalleDto.PreguntaDetalle p : s.getPreguntas()) {
                FeedbackQuestion q = p.getPregunta();
                FeedbackQuestion nueva = FeedbackQuestion.builder()
                        .formId(copia.getId())
                        .sectionId(seccion.getId())
                        .tipo(q.getTipo())
                        .enunciado(q.getEnunciado())
                        .ayuda(q.getAyuda())
                        .obligatoria(q.isObligatoria())
                        .orden(q.getOrden())
                        .scaleId(q.getScaleId())
                        .criterionId(q.getCriterionId())
                        .escalaMin(q.getEscalaMin())
                        .escalaMax(q.getEscalaMax())
                        .etiquetaMin(q.getEtiquetaMin())
                        .etiquetaMax(q.getEtiquetaMax())
                        .build();
                List<FeedbackQuestionOption> opciones = p.getOpciones().stream()
                        .map(o -> FeedbackQuestionOption.builder()
                                .etiqueta(o.getEtiqueta())
                                .valor(o.getValor())
                                .orden(o.getOrden())
                                .build())
                        .toList();
                formRepository.guardarPregunta(nueva, opciones);
            }
        }
        log.info("Formulario {} duplicado como {} (versión {})", formId, copia.getId(), copia.getVersion());
        return copia;
    }

    @Override
    @Transactional
    public FeedbackForm cambiarEstado(Long formId, CambiarEstadoFeedbackDto dto) {
        buscarForm(formId);
        String destino = estados.codigo(FeedbackEstados.MAQUINA_FORM, dto.getNuevoEstadoId());
        if (destino == null) {
            throw new IllegalArgumentException("El estado indicado no pertenece al ciclo de vida del formulario.");
        }
        if (FeedbackEstados.PUBLICADO.equals(destino)) {
            validarPublicacion(consultar(formId));
        }
        FeedbackForm resultado = formRepository.cambiarEstado(formId, dto.getNuevoEstadoId(), dto.getMotivo());
        log.info("Formulario {} pasó a {}", formId, destino);
        return resultado;
    }

    /** Un formulario publicable tiene preguntas, ninguna sección vacía y cada pregunta bien configurada. */
    void validarPublicacion(FeedbackFormDetalleDto detalle) {
        if (detalle.getSecciones().isEmpty() || detalle.todasLasPreguntas().isEmpty()) {
            throw new IllegalStateException("Agrega al menos una pregunta antes de publicar.");
        }
        for (FeedbackFormDetalleDto.SeccionDetalle s : detalle.getSecciones()) {
            if (s.getPreguntas().isEmpty()) {
                throw new IllegalStateException("La sección \"" + s.getSeccion().getTitulo()
                        + "\" no tiene preguntas. Agrégale preguntas o elimínala antes de publicar.");
            }
            for (FeedbackFormDetalleDto.PreguntaDetalle p : s.getPreguntas()) {
                try {
                    handlers.obtener(p.getPregunta().getTipo()).validarDefinicion(
                            new DefinicionPreguntaFeedback(p.getPregunta(), p.getOpciones(), p.getNiveles()));
                } catch (IllegalArgumentException e) {
                    throw new IllegalStateException("La pregunta \"" + p.getPregunta().getEnunciado()
                            + "\" no está completa: " + e.getMessage());
                }
            }
        }
    }

    // ─── Secciones ───────────────────────────────────────────────────────────

    @Override
    @Transactional
    public FeedbackSection agregarSeccion(Long formId, GuardarSeccionFeedbackDto dto) {
        formEditable(formId);
        int orden = formRepository.secciones(formId).size() + 1;
        return formRepository.guardarSeccion(FeedbackSection.builder()
                .formId(formId)
                .titulo(dto.getTitulo().trim())
                .descripcion(dto.getDescripcion())
                .orden(orden)
                .build());
    }

    @Override
    @Transactional
    public FeedbackSection actualizarSeccion(Long formId, Long sectionId, GuardarSeccionFeedbackDto dto) {
        formEditable(formId);
        FeedbackSection seccion = seccionDelFormulario(formId, sectionId);
        seccion.setTitulo(dto.getTitulo().trim());
        seccion.setDescripcion(dto.getDescripcion());
        return formRepository.guardarSeccion(seccion);
    }

    @Override
    @Transactional
    public void eliminarSeccion(Long formId, Long sectionId) {
        formEditable(formId);
        seccionDelFormulario(formId, sectionId);
        formRepository.eliminarSeccion(sectionId);
        renumerarSecciones(formRepository.secciones(formId));
    }

    @Override
    @Transactional
    public void reordenarSecciones(Long formId, ReordenarFeedbackDto dto) {
        formEditable(formId);
        List<FeedbackSection> actuales = formRepository.secciones(formId);
        Map<Long, FeedbackSection> porId = new HashMap<>();
        actuales.forEach(s -> porId.put(s.getId(), s));
        validarPermutacion(porId.keySet(), dto.getIds(), "secciones");

        List<FeedbackSection> ordenadas = dto.getIds().stream().map(porId::get).toList();
        renumerarSecciones(ordenadas);
    }

    private void renumerarSecciones(List<FeedbackSection> ordenadas) {
        for (int i = 0; i < ordenadas.size(); i++) {
            ordenadas.get(i).setOrden(i + 1);
        }
        formRepository.actualizarOrdenSecciones(ordenadas);
    }

    // ─── Preguntas ───────────────────────────────────────────────────────────

    @Override
    @Transactional
    public FeedbackFormDetalleDto.PreguntaDetalle agregarPregunta(Long formId, GuardarPreguntaFeedbackDto dto) {
        formEditable(formId);
        seccionDelFormulario(formId, dto.getSectionId());
        FeedbackQuestion pregunta = FeedbackQuestion.builder()
                .formId(formId)
                .sectionId(dto.getSectionId())
                .orden(formRepository.preguntasDeSeccion(dto.getSectionId()).size() + 1)
                .build();
        return guardarPregunta(pregunta, dto);
    }

    @Override
    @Transactional
    public FeedbackFormDetalleDto.PreguntaDetalle actualizarPregunta(Long formId, Long preguntaId, GuardarPreguntaFeedbackDto dto) {
        formEditable(formId);
        FeedbackQuestion pregunta = preguntaDelFormulario(formId, preguntaId);
        seccionDelFormulario(formId, dto.getSectionId());
        Long seccionAnterior = pregunta.getSectionId();
        if (!Objects.equals(seccionAnterior, dto.getSectionId())) {
            // Se mueve al final de la sección destino y se cierra el hueco en la de origen.
            pregunta.setSectionId(dto.getSectionId());
            pregunta.setOrden(formRepository.preguntasDeSeccion(dto.getSectionId()).size() + 1);
        }
        FeedbackFormDetalleDto.PreguntaDetalle guardada = guardarPregunta(pregunta, dto);
        if (!Objects.equals(seccionAnterior, dto.getSectionId())) {
            renumerarPreguntas(formRepository.preguntasDeSeccion(seccionAnterior));
        }
        return guardada;
    }

    @Override
    @Transactional
    public void eliminarPregunta(Long formId, Long preguntaId) {
        formEditable(formId);
        FeedbackQuestion pregunta = preguntaDelFormulario(formId, preguntaId);
        formRepository.eliminarPregunta(preguntaId);
        renumerarPreguntas(formRepository.preguntasDeSeccion(pregunta.getSectionId()));
    }

    @Override
    @Transactional
    public void reordenarPreguntas(Long formId, Long sectionId, ReordenarFeedbackDto dto) {
        formEditable(formId);
        seccionDelFormulario(formId, sectionId);
        List<FeedbackQuestion> actuales = formRepository.preguntasDeSeccion(sectionId);
        Map<Long, FeedbackQuestion> porId = new HashMap<>();
        actuales.forEach(q -> porId.put(q.getId(), q));
        validarPermutacion(porId.keySet(), dto.getIds(), "preguntas");
        renumerarPreguntas(dto.getIds().stream().map(porId::get).toList());
    }

    private void renumerarPreguntas(List<FeedbackQuestion> ordenadas) {
        for (int i = 0; i < ordenadas.size(); i++) {
            ordenadas.get(i).setOrden(i + 1);
        }
        formRepository.actualizarOrdenPreguntas(ordenadas);
    }

    private FeedbackFormDetalleDto.PreguntaDetalle guardarPregunta(FeedbackQuestion pregunta, GuardarPreguntaFeedbackDto dto) {
        FeedbackQuestionType tipo = dto.getTipo();
        pregunta.setTipo(tipo);
        pregunta.setEnunciado(dto.getEnunciado().trim());
        pregunta.setAyuda(dto.getAyuda());
        pregunta.setObligatoria(dto.isObligatoria());
        pregunta.setCriterionId(dto.getCriterionId());
        // Solo se conserva la configuración que aplica al tipo: evita datos
        // huérfanos si el coordinador cambia el tipo de una pregunta.
        pregunta.setScaleId(tipo == FeedbackQuestionType.LIKERT ? dto.getScaleId() : null);
        boolean numerica = tipo == FeedbackQuestionType.NUMERIC_SCALE;
        pregunta.setEscalaMin(numerica ? dto.getEscalaMin() : null);
        pregunta.setEscalaMax(numerica ? dto.getEscalaMax() : null);
        pregunta.setEtiquetaMin(numerica ? dto.getEtiquetaMin() : null);
        pregunta.setEtiquetaMax(numerica ? dto.getEtiquetaMax() : null);

        boolean conOpciones = tipo == FeedbackQuestionType.SINGLE_CHOICE || tipo == FeedbackQuestionType.MULTIPLE_CHOICE;
        List<FeedbackQuestionOption> opciones = new ArrayList<>();
        if (conOpciones && dto.getOpciones() != null) {
            for (int i = 0; i < dto.getOpciones().size(); i++) {
                OpcionPreguntaFeedbackDto o = dto.getOpciones().get(i);
                opciones.add(FeedbackQuestionOption.builder()
                        .etiqueta(o.getEtiqueta() == null ? null : o.getEtiqueta().trim())
                        .valor(o.getValor())
                        .orden(i + 1)
                        .build());
            }
        }

        if (pregunta.getCriterionId() != null && catalogRepository.buscarCriterio(pregunta.getCriterionId()).isEmpty()) {
            throw new IllegalArgumentException("El criterio seleccionado no existe.");
        }
        List<FeedbackScaleLevel> niveles = List.of();
        if (pregunta.getScaleId() != null) {
            FeedbackScaleDetalleDto escala = catalogRepository.buscarEscala(pregunta.getScaleId())
                    .orElseThrow(() -> new IllegalArgumentException("La escala seleccionada no existe."));
            if (!escala.getEscala().isActiva()) {
                throw new IllegalArgumentException("La escala seleccionada está inactiva.");
            }
            niveles = escala.getNiveles();
        }

        handlers.obtener(tipo).validarDefinicion(new DefinicionPreguntaFeedback(pregunta, opciones, niveles));

        FeedbackQuestion guardada = formRepository.guardarPregunta(pregunta, opciones);
        return formRepository.detalle(guardada.getFormId())
                .flatMap(d -> d.todasLasPreguntas().stream()
                        .filter(p -> p.getPregunta().getId().equals(guardada.getId()))
                        .findFirst())
                .orElseThrow();
    }

    // ─── Apoyo ───────────────────────────────────────────────────────────────

    private FeedbackForm buscarForm(Long formId) {
        return formRepository.buscarPorId(formId)
                .orElseThrow(() -> new NotFoundException("Formulario de feedback no encontrado"));
    }

    private FeedbackForm formEditable(Long formId) {
        FeedbackForm form = buscarForm(formId);
        if (!estados.es(FeedbackEstados.MAQUINA_FORM, form.getEstadoId(), FeedbackEstados.BORRADOR)) {
            throw new IllegalStateException(NO_EDITABLE);
        }
        return form;
    }

    private FeedbackSection seccionDelFormulario(Long formId, Long sectionId) {
        return formRepository.buscarSeccion(sectionId)
                .filter(s -> s.getFormId().equals(formId))
                .orElseThrow(() -> new NotFoundException("La sección no existe en este formulario"));
    }

    private FeedbackQuestion preguntaDelFormulario(Long formId, Long preguntaId) {
        return formRepository.buscarPregunta(preguntaId)
                .filter(q -> q.getFormId().equals(formId))
                .orElseThrow(() -> new NotFoundException("La pregunta no existe en este formulario"));
    }

    private static void validarPermutacion(java.util.Set<Long> actuales, List<Long> pedidos, String que) {
        if (pedidos.size() != actuales.size() || !actuales.equals(new HashSet<>(pedidos))) {
            throw new IllegalArgumentException("El nuevo orden debe incluir exactamente las " + que + " actuales, sin repetir.");
        }
    }
}
