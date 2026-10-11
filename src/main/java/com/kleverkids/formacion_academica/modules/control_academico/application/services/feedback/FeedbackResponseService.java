package com.kleverkids.formacion_academica.modules.control_academico.application.services.feedback;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.feedback_response.ConsultarSolicitudFeedbackUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.feedback_response.ListarMisSolicitudesFeedbackUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.feedback_response.ResponderFeedbackUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.feedback.ContextoAcademicoFeedbackPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.feedback.ContextoAcademicoFeedbackPort.Candidata;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.feedback.FeedbackAssignmentRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.feedback.FeedbackEventPublisher;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.feedback.FeedbackFormRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.feedback.FeedbackResponseRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.feedback.FeedbackResponseRepositoryPort.ClaseAplicacionKey;
import com.kleverkids.formacion_academica.modules.control_academico.application.services.feedback.handler.DefinicionPreguntaFeedback;
import com.kleverkids.formacion_academica.modules.control_academico.application.services.feedback.handler.FeedbackQuestionTypeHandler;
import com.kleverkids.formacion_academica.modules.control_academico.application.services.feedback.handler.FeedbackQuestionTypeHandlerRegistry;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_form.FeedbackFormDetalleDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_response.ConfirmacionRespuestaFeedbackDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_response.RespuestaPreguntaFeedbackDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_response.ResponderFeedbackDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_response.SolicitudFeedbackDetalleDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_response.SolicitudFeedbackDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.events.feedback.FeedbackResponseSubmittedEvent;
import com.kleverkids.formacion_academica.modules.control_academico.domain.exception.FeedbackValidacionException;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_assignment.FeedbackAssignment;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_assignment.valueobject.EstadoSolicitudFeedback;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_assignment.valueobject.VentanaFeedback;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackForm;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_response.FeedbackAnswer;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_response.FeedbackParticipation;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_response.FeedbackResponse;
import com.kleverkids.formacion_academica.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Experiencia del estudiante: bandeja, formulario y envío.
 *
 * <p>El {@code estudianteId} siempre llega del token (lo resuelve el
 * controlador); este servicio nunca lo acepta de la URL.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FeedbackResponseService implements ListarMisSolicitudesFeedbackUseCase,
        ConsultarSolicitudFeedbackUseCase, ResponderFeedbackUseCase {

    /** Cuánto hacia atrás mira la bandeja (clases terminadas). Lo anterior ya está expirado o respondido. */
    static final int DIAS_HISTORIA = 60;
    /** Clases próximas que se muestran como PENDIENTE. */
    static final int DIAS_ADELANTE = 7;

    private static final DateTimeFormatter FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final ContextoAcademicoFeedbackPort contexto;
    private final FeedbackAssignmentRepositoryPort assignmentRepository;
    private final FeedbackFormRepositoryPort formRepository;
    private final FeedbackResponseRepositoryPort responseRepository;
    private final FeedbackQuestionTypeHandlerRegistry handlers;
    private final FeedbackEventPublisher eventPublisher;
    private final FeedbackEstados estados;

    /** Reloj reemplazable en tests (no hay bean Clock en el proyecto). */
    private Clock clock = Clock.systemDefaultZone();

    void setClock(Clock clock) {
        this.clock = clock;
    }

    // ─── Bandeja ─────────────────────────────────────────────────────────────

    @Override
    @Transactional(readOnly = true)
    public Page<SolicitudFeedbackDto> listarMisSolicitudes(Long estudianteId, EstadoSolicitudFeedback estado, Pageable pageable) {
        LocalDateTime ahora = LocalDateTime.now(clock);
        List<Long> aplicacionesVisibles = estados.ids(FeedbackEstados.MAQUINA_APLICACION,
                FeedbackEstados.ACTIVA, FeedbackEstados.CERRADA);
        if (aplicacionesVisibles.isEmpty()) {
            return Page.empty(pageable);
        }

        List<Candidata> candidatas = contexto.candidatas(estudianteId, aplicacionesVisibles, membresiasExcluidas(),
                ahora.minusDays(DIAS_HISTORIA), ahora.plusDays(DIAS_ADELANTE));
        if (candidatas.isEmpty()) {
            return Page.empty(pageable);
        }

        Map<Long, FeedbackAssignment> aplicaciones = assignmentRepository.buscarPorIds(
                        candidatas.stream().map(Candidata::assignmentId).collect(Collectors.toSet()))
                .stream().collect(Collectors.toMap(FeedbackAssignment::getId, Function.identity()));
        Map<Long, FeedbackForm> formularios = formRepository.buscarPorIds(
                        aplicaciones.values().stream().map(FeedbackAssignment::getFormId).collect(Collectors.toSet()))
                .stream().collect(Collectors.toMap(FeedbackForm::getId, Function.identity()));
        Set<ClaseAplicacionKey> respondidas = responseRepository.respondidas(estudianteId,
                candidatas.stream().map(c -> new ClaseAplicacionKey(c.assignmentId(), c.claseId())).toList());
        Map<Long, List<Long>> profesores = contexto.profesoresDeClases(
                candidatas.stream().map(Candidata::claseId).collect(Collectors.toSet()));
        List<Long> activas = estados.ids(FeedbackEstados.MAQUINA_APLICACION, FeedbackEstados.ACTIVA);

        List<SolicitudFeedbackDto> filas = new ArrayList<>();
        for (Candidata c : candidatas) {
            FeedbackAssignment a = aplicaciones.get(c.assignmentId());
            FeedbackForm f = a == null ? null : formularios.get(a.getFormId());
            if (a == null || f == null) {
                continue;
            }
            boolean respondio = respondidas.contains(new ClaseAplicacionKey(c.assignmentId(), c.claseId()));
            EstadoSolicitudFeedback e = a.estadoSolicitud(c.claseFin(), ahora, activas.contains(a.getEstadoId()),
                    respondio && !f.isPermiteMultiplesRespuestas());
            if (estado != null && e != estado) {
                continue;
            }
            filas.add(fila(c, a, f, e, profesores.getOrDefault(c.claseId(), List.of())));
        }
        filas.sort(PRIORIDAD);

        int desde = (int) Math.min(pageable.getOffset(), filas.size());
        int hasta = Math.min(desde + pageable.getPageSize(), filas.size());
        return new PageImpl<>(filas.subList(desde, hasta), pageable, filas.size());
    }

    /** Primero lo que se puede responder ya (lo que cierra antes arriba), luego lo próximo, luego el historial. */
    private static final Comparator<SolicitudFeedbackDto> PRIORIDAD = (x, y) -> {
        int porRango = Integer.compare(rango(x), rango(y));
        if (porRango != 0) {
            return porRango;
        }
        return switch (x.getEstado()) {
            case DISPONIBLE -> x.getCierra().compareTo(y.getCierra());
            case PENDIENTE -> x.getAbre().compareTo(y.getAbre());
            default -> y.getClaseFin().compareTo(x.getClaseFin());
        };
    };

    private static int rango(SolicitudFeedbackDto s) {
        return switch (s.getEstado()) {
            case DISPONIBLE -> 0;
            case PENDIENTE -> 1;
            default -> 2;
        };
    }

    // ─── Formulario ──────────────────────────────────────────────────────────

    @Override
    @Transactional(readOnly = true)
    public SolicitudFeedbackDetalleDto consultarSolicitud(Long estudianteId, Long assignmentId, Long claseId) {
        Contexto ctx = cargar(estudianteId, assignmentId, claseId);
        return SolicitudFeedbackDetalleDto.builder()
                .solicitud(fila(ctx.candidata, ctx.aplicacion, ctx.form.getForm(), ctx.estado,
                        contexto.profesoresDeClases(List.of(claseId)).getOrDefault(claseId, List.of())))
                .formulario(ctx.form)
                .build();
    }

    // ─── Envío ───────────────────────────────────────────────────────────────

    @Override
    @Transactional
    public ConfirmacionRespuestaFeedbackDto responder(Long estudianteId, Long assignmentId, Long claseId, ResponderFeedbackDto dto) {
        Contexto ctx = cargar(estudianteId, assignmentId, claseId);
        exigirDisponible(ctx);

        FeedbackForm form = ctx.form.getForm();
        List<FeedbackAnswer> filas = validarYConvertir(ctx.form, dto == null ? List.of() : dto.getRespuestas());

        LocalDateTime ahora = LocalDateTime.now(clock);
        FeedbackResponse respuesta = FeedbackResponse.builder()
                .assignmentId(assignmentId)
                .formId(form.getId())
                .claseId(claseId)
                // Anónimo: ni estudiante ni hora exacta (ver FeedbackResponse).
                .estudianteId(form.isAnonimo() ? null : estudianteId)
                .enviadoEn(form.isAnonimo() ? ahora.truncatedTo(ChronoUnit.HOURS) : ahora)
                .respuestas(filas)
                .build();
        int numero = form.isPermiteMultiplesRespuestas() ? (int) ctx.participaciones + 1 : 1;
        FeedbackParticipation participacion = FeedbackParticipation.builder()
                .assignmentId(assignmentId)
                .claseId(claseId)
                .estudianteId(estudianteId)
                .numero(numero)
                .fecha(ahora.toLocalDate())
                .build();

        responseRepository.registrar(respuesta, participacion);
        eventPublisher.publish(new FeedbackResponseSubmittedEvent(assignmentId, form.getId(), claseId));
        // Sin estudiante en el log: en formularios anónimos sería la única traza que los une.
        log.info("Feedback recibido para aplicación {} / clase {}", assignmentId, claseId);

        return ConfirmacionRespuestaFeedbackDto.builder()
                .assignmentId(assignmentId)
                .claseId(claseId)
                .recibidoEn(ahora)
                .anonimo(form.isAnonimo())
                .build();
    }

    /**
     * Valida TODAS las preguntas y acumula los errores por pregunta (no se
     * detiene en el primero), para que el estudiante los vea juntos.
     */
    List<FeedbackAnswer> validarYConvertir(FeedbackFormDetalleDto form, List<RespuestaPreguntaFeedbackDto> respuestas) {
        Map<Long, RespuestaPreguntaFeedbackDto> porPregunta = new HashMap<>();
        for (RespuestaPreguntaFeedbackDto r : respuestas) {
            if (porPregunta.put(r.getPreguntaId(), r) != null) {
                throw new IllegalArgumentException("Cada pregunta solo puede responderse una vez por envío.");
            }
        }
        Set<Long> delFormulario = form.todasLasPreguntas().stream()
                .map(p -> p.getPregunta().getId()).collect(Collectors.toSet());
        if (!delFormulario.containsAll(porPregunta.keySet())) {
            throw new IllegalArgumentException("Hay respuestas a preguntas que no pertenecen a este formulario.");
        }

        Map<Long, String> errores = new LinkedHashMap<>();
        List<FeedbackAnswer> filas = new ArrayList<>();
        for (FeedbackFormDetalleDto.PreguntaDetalle p : form.todasLasPreguntas()) {
            Long id = p.getPregunta().getId();
            FeedbackQuestionTypeHandler handler = handlers.obtener(p.getPregunta().getTipo());
            RespuestaPreguntaFeedbackDto r = porPregunta.get(id);
            if (!handler.respondida(r)) {
                if (p.getPregunta().isObligatoria()) {
                    errores.put(id, "Esta pregunta es obligatoria.");
                }
                continue;
            }
            try {
                filas.addAll(handler.convertir(
                        new DefinicionPreguntaFeedback(p.getPregunta(), p.getOpciones(), p.getNiveles()), r));
            } catch (IllegalArgumentException e) {
                errores.put(id, e.getMessage());
            }
        }
        if (!errores.isEmpty()) {
            throw new FeedbackValidacionException(errores);
        }
        return filas;
    }

    // ─── Apoyo ───────────────────────────────────────────────────────────────

    private record Contexto(Candidata candidata, FeedbackAssignment aplicacion, FeedbackFormDetalleDto form,
                            EstadoSolicitudFeedback estado, long participaciones) {
    }

    /** Resuelve y valida pertenencia: si el estudiante no es elegible, la solicitud "no existe" para él. */
    private Contexto cargar(Long estudianteId, Long assignmentId, Long claseId) {
        Candidata candidata = contexto.candidata(estudianteId, assignmentId, claseId, membresiasExcluidas())
                .orElseThrow(() -> new NotFoundException("No tienes este feedback asignado"));
        FeedbackAssignment aplicacion = assignmentRepository.buscarPorId(assignmentId)
                .filter(a -> estados.ids(FeedbackEstados.MAQUINA_APLICACION, FeedbackEstados.ACTIVA, FeedbackEstados.CERRADA)
                        .contains(a.getEstadoId()))
                // Borradores y canceladas no existen para el estudiante.
                .orElseThrow(() -> new NotFoundException("No tienes este feedback asignado"));
        FeedbackFormDetalleDto form = formRepository.detalle(aplicacion.getFormId())
                .orElseThrow(() -> new NotFoundException("Formulario de feedback no encontrado"));

        long participaciones = responseRepository.contarParticipaciones(assignmentId, claseId, estudianteId);
        boolean vigente = estados.es(FeedbackEstados.MAQUINA_APLICACION, aplicacion.getEstadoId(), FeedbackEstados.ACTIVA);
        EstadoSolicitudFeedback estado = aplicacion.estadoSolicitud(candidata.claseFin(), LocalDateTime.now(clock),
                vigente, participaciones > 0 && !form.getForm().isPermiteMultiplesRespuestas());
        return new Contexto(candidata, aplicacion, form, estado, participaciones);
    }

    private void exigirDisponible(Contexto ctx) {
        VentanaFeedback ventana = ctx.aplicacion.ventanaPara(ctx.candidata.claseFin());
        switch (ctx.estado) {
            case DISPONIBLE -> {
            }
            case PENDIENTE -> throw new IllegalStateException(
                    "Este feedback aún no está disponible. Abre el " + FECHA.format(ventana.abre()) + ".");
            case RESPONDIDO -> throw new IllegalStateException("Ya respondiste este feedback. ¡Gracias!");
            case EXPIRADO -> throw new IllegalStateException("El plazo para responder este feedback terminó.");
            case CERRADO -> throw new IllegalStateException("Este feedback ya no recibe respuestas.");
        }
    }

    private List<Long> membresiasExcluidas() {
        return estados.ids(FeedbackEstados.MAQUINA_MEMBRESIA, FeedbackEstados.RETIRADO);
    }

    private static SolicitudFeedbackDto fila(Candidata c, FeedbackAssignment a, FeedbackForm f,
                                             EstadoSolicitudFeedback e, List<Long> profesores) {
        VentanaFeedback v = a.ventanaPara(c.claseFin());
        return SolicitudFeedbackDto.builder()
                .assignmentId(a.getId())
                .assignmentNombre(a.getNombre())
                .formId(f.getId())
                .formTitulo(f.getTitulo())
                .anonimo(f.isAnonimo())
                .claseId(c.claseId())
                .claseNombre(c.claseNombre())
                .claseInicio(c.claseInicio())
                .claseFin(c.claseFin())
                .grupoId(c.grupoId())
                .profesoresIds(profesores)
                .estado(e)
                .abre(v.abre())
                .cierra(v.cierra())
                .build();
    }
}
