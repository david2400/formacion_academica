package com.kleverkids.formacion_academica.modules.control_academico.application.services.feedback;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.feedback_assignment.*;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.feedback.ContextoAcademicoFeedbackPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.feedback.FeedbackAssignmentRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.feedback.FeedbackFormRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_assignment.ActualizarFeedbackAssignmentDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_assignment.CrearFeedbackAssignmentDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_assignment.FiltroFeedbackAssignmentDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_form.CambiarEstadoFeedbackDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_assignment.FeedbackAssignment;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackForm;
import com.kleverkids.formacion_academica.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Aplicación de formularios a clases. Reglas:
 * <ul>
 *   <li>Solo formularios PUBLICADOS se aplican (y se activan).</li>
 *   <li>Exactamente uno de clase / grupo; con grupo, rango de fechas obligatorio.</li>
 *   <li>Formulario y alcance no cambian después de crear la aplicación.</li>
 *   <li>soloAsistentes y umbral de anonimato solo se cambian en BORRADOR
 *       (cambiarlos con respuestas recibidas alteraría elegibilidad y privacidad
 *       de forma retroactiva).</li>
 * </ul>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FeedbackAssignmentService implements CrearFeedbackAssignmentUseCase, ActualizarFeedbackAssignmentUseCase,
        ConsultarFeedbackAssignmentUseCase, ListarFeedbackAssignmentsUseCase, CambiarEstadoFeedbackAssignmentUseCase {

    static final int VIGENCIA_HORAS_POR_DEFECTO = 72;
    static final int UMBRAL_ANONIMATO_POR_DEFECTO = 3;

    private final FeedbackAssignmentRepositoryPort assignmentRepository;
    private final FeedbackFormRepositoryPort formRepository;
    private final ContextoAcademicoFeedbackPort contexto;
    private final FeedbackEstados estados;

    @Override
    @Transactional
    public FeedbackAssignment crear(CrearFeedbackAssignmentDto dto) {
        FeedbackForm form = formRepository.buscarPorId(dto.getFormId())
                .orElseThrow(() -> new NotFoundException("Formulario de feedback no encontrado"));
        exigirFormularioPublicado(form);

        boolean porClase = dto.getClaseId() != null;
        boolean porGrupo = dto.getGrupoId() != null;
        if (porClase == porGrupo) {
            throw new IllegalArgumentException("Elige una clase o un grupo (exactamente uno de los dos).");
        }
        if (porClase && contexto.clase(dto.getClaseId()).isEmpty()) {
            throw new NotFoundException("La clase no existe");
        }
        if (porGrupo) {
            if (!contexto.existeGrupo(dto.getGrupoId())) {
                throw new NotFoundException("El grupo no existe");
            }
            if (dto.getDesde() == null || dto.getHasta() == null) {
                throw new IllegalArgumentException("Indica el rango de fechas de las clases del grupo.");
            }
            if (dto.getHasta().isBefore(dto.getDesde())) {
                throw new IllegalArgumentException("La fecha final del rango debe ser posterior a la inicial.");
            }
        }
        validarLimites(dto.getAbreEn(), dto.getCierraEn());

        FeedbackAssignment assignment = FeedbackAssignment.builder()
                .formId(form.getId())
                .nombre(dto.getNombre().trim())
                .claseId(dto.getClaseId())
                .grupoId(dto.getGrupoId())
                .desde(porGrupo ? dto.getDesde() : null)
                .hasta(porGrupo ? dto.getHasta() : null)
                .aperturaOffsetMinutos(dto.getAperturaOffsetMinutos() == null ? 0 : dto.getAperturaOffsetMinutos())
                .vigenciaHoras(dto.getVigenciaHoras() == null ? VIGENCIA_HORAS_POR_DEFECTO : dto.getVigenciaHoras())
                .abreEn(dto.getAbreEn())
                .cierraEn(dto.getCierraEn())
                .soloAsistentes(dto.isSoloAsistentes())
                .umbralAnonimato(dto.getUmbralAnonimato() == null ? UMBRAL_ANONIMATO_POR_DEFECTO : dto.getUmbralAnonimato())
                .build();
        FeedbackAssignment creada = assignmentRepository.crear(assignment);
        log.info("Aplicación de feedback {} creada (formulario {})", creada.getId(), form.getId());
        return creada;
    }

    @Override
    @Transactional
    public FeedbackAssignment actualizar(Long assignmentId, ActualizarFeedbackAssignmentDto dto) {
        FeedbackAssignment assignment = consultar(assignmentId);
        String estado = estados.codigo(FeedbackEstados.MAQUINA_APLICACION, assignment.getEstadoId());
        boolean borrador = FeedbackEstados.BORRADOR.equals(estado);
        if (!borrador && !FeedbackEstados.ACTIVA.equals(estado)) {
            throw new IllegalStateException("Una aplicación cerrada o cancelada ya no se puede modificar.");
        }
        boolean cambiaReglas = (dto.getSoloAsistentes() != null && dto.getSoloAsistentes() != assignment.isSoloAsistentes())
                || (dto.getUmbralAnonimato() != null && !dto.getUmbralAnonimato().equals(assignment.getUmbralAnonimato()));
        if (cambiaReglas && !borrador) {
            throw new IllegalStateException("\"Solo asistentes\" y el umbral de anonimato solo se pueden cambiar en borrador.");
        }
        validarLimites(dto.getAbreEn(), dto.getCierraEn());

        assignment.setNombre(dto.getNombre().trim());
        if (dto.getAperturaOffsetMinutos() != null) {
            assignment.setAperturaOffsetMinutos(dto.getAperturaOffsetMinutos());
        }
        if (dto.getVigenciaHoras() != null) {
            assignment.setVigenciaHoras(dto.getVigenciaHoras());
        }
        assignment.setAbreEn(dto.getAbreEn());
        assignment.setCierraEn(dto.getCierraEn());
        if (dto.getSoloAsistentes() != null) {
            assignment.setSoloAsistentes(dto.getSoloAsistentes());
        }
        if (dto.getUmbralAnonimato() != null) {
            assignment.setUmbralAnonimato(dto.getUmbralAnonimato());
        }
        return assignmentRepository.actualizar(assignment);
    }

    @Override
    @Transactional(readOnly = true)
    public FeedbackAssignment consultar(Long assignmentId) {
        return assignmentRepository.buscarPorId(assignmentId)
                .orElseThrow(() -> new NotFoundException("Aplicación de feedback no encontrada"));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FeedbackAssignment> listar(FiltroFeedbackAssignmentDto filtro, Pageable pageable) {
        return assignmentRepository.listar(filtro == null ? new FiltroFeedbackAssignmentDto() : filtro, pageable);
    }

    @Override
    @Transactional
    public FeedbackAssignment cambiarEstado(Long assignmentId, CambiarEstadoFeedbackDto dto) {
        FeedbackAssignment assignment = consultar(assignmentId);
        String destino = estados.codigo(FeedbackEstados.MAQUINA_APLICACION, dto.getNuevoEstadoId());
        if (destino == null) {
            throw new IllegalArgumentException("El estado indicado no pertenece al ciclo de vida de la aplicación.");
        }
        if (FeedbackEstados.ACTIVA.equals(destino)) {
            FeedbackForm form = formRepository.buscarPorId(assignment.getFormId())
                    .orElseThrow(() -> new NotFoundException("Formulario de feedback no encontrado"));
            exigirFormularioPublicado(form);
        }
        FeedbackAssignment resultado = assignmentRepository.cambiarEstado(assignmentId, dto.getNuevoEstadoId(), dto.getMotivo());
        log.info("Aplicación de feedback {} pasó a {}", assignmentId, destino);
        return resultado;
    }

    private void exigirFormularioPublicado(FeedbackForm form) {
        if (!estados.es(FeedbackEstados.MAQUINA_FORM, form.getEstadoId(), FeedbackEstados.PUBLICADO)) {
            throw new IllegalStateException("Solo se pueden aplicar formularios publicados.");
        }
    }

    private static void validarLimites(LocalDateTime abreEn, LocalDateTime cierraEn) {
        if (abreEn != null && cierraEn != null && !cierraEn.isAfter(abreEn)) {
            throw new IllegalArgumentException("La fecha de cierre debe ser posterior a la de apertura.");
        }
    }
}
