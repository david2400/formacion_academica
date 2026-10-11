package com.kleverkids.formacion_academica.modules.control_academico.application.services.feedback;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.feedback_catalog.*;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.feedback.FeedbackCatalogRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_form.FeedbackScaleDetalleDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_form.GuardarCriterioFeedbackDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_form.GuardarEscalaFeedbackDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_form.NivelEscalaFeedbackDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackCriterion;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackScale;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackScaleLevel;
import com.kleverkids.formacion_academica.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Catálogos reutilizables: escalas y criterios.
 *
 * <p>Los niveles de una escala que ya usa algún formulario publicado o
 * archivado no se pueden cambiar (alterarían el significado de respuestas ya
 * guardadas); nombre, descripción y estado activo sí.
 */
@Service
@RequiredArgsConstructor
public class FeedbackCatalogService implements ListarEscalasFeedbackUseCase, CrearEscalaFeedbackUseCase,
        ActualizarEscalaFeedbackUseCase, ListarCriteriosFeedbackUseCase, CrearCriterioFeedbackUseCase,
        ActualizarCriterioFeedbackUseCase {

    private final FeedbackCatalogRepositoryPort catalogRepository;
    private final FeedbackEstados estados;

    @Override
    @Transactional(readOnly = true)
    public List<FeedbackScaleDetalleDto> listarEscalas() {
        return catalogRepository.listarEscalas();
    }

    @Override
    @Transactional
    public FeedbackScaleDetalleDto crearEscala(GuardarEscalaFeedbackDto dto) {
        FeedbackScale escala = FeedbackScale.builder()
                .nombre(dto.getNombre().trim())
                .descripcion(dto.getDescripcion())
                .activa(dto.isActiva())
                .build();
        return catalogRepository.guardarEscala(escala, niveles(dto));
    }

    @Override
    @Transactional
    public FeedbackScaleDetalleDto actualizarEscala(Long scaleId, GuardarEscalaFeedbackDto dto) {
        FeedbackScaleDetalleDto actual = catalogRepository.buscarEscala(scaleId)
                .orElseThrow(() -> new NotFoundException("Escala no encontrada"));
        List<FeedbackScaleLevel> nuevos = niveles(dto);

        List<Long> editables = estados.ids(FeedbackEstados.MAQUINA_FORM, FeedbackEstados.BORRADOR);
        if (!mismosNiveles(actual.getNiveles(), nuevos)
                && catalogRepository.escalaEnUsoPorFormulariosNoEditables(scaleId, editables)) {
            throw new IllegalStateException("La escala ya se usa en formularios publicados: solo puedes cambiar su nombre,"
                    + " descripción o estado. Crea una escala nueva si necesitas otros niveles.");
        }

        FeedbackScale escala = actual.getEscala();
        escala.setNombre(dto.getNombre().trim());
        escala.setDescripcion(dto.getDescripcion());
        escala.setActiva(dto.isActiva());
        return catalogRepository.guardarEscala(escala, nuevos);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FeedbackCriterion> listarCriterios() {
        return catalogRepository.listarCriterios();
    }

    @Override
    @Transactional
    public FeedbackCriterion crearCriterio(GuardarCriterioFeedbackDto dto) {
        if (catalogRepository.buscarCriterioPorCodigo(dto.getCodigo()).isPresent()) {
            throw new IllegalStateException("Ya existe un criterio con el código " + dto.getCodigo() + ".");
        }
        return catalogRepository.guardarCriterio(FeedbackCriterion.builder()
                .codigo(dto.getCodigo())
                .nombre(dto.getNombre().trim())
                .descripcion(dto.getDescripcion())
                .activo(dto.isActivo())
                .build());
    }

    @Override
    @Transactional
    public FeedbackCriterion actualizarCriterio(Long criterionId, GuardarCriterioFeedbackDto dto) {
        FeedbackCriterion criterio = catalogRepository.buscarCriterio(criterionId)
                .orElseThrow(() -> new NotFoundException("Criterio no encontrado"));
        if (!criterio.getCodigo().equals(dto.getCodigo())) {
            // El código es la clave estable de la analítica histórica.
            throw new IllegalStateException("El código de un criterio no se puede cambiar.");
        }
        criterio.setNombre(dto.getNombre().trim());
        criterio.setDescripcion(dto.getDescripcion());
        criterio.setActivo(dto.isActivo());
        return catalogRepository.guardarCriterio(criterio);
    }

    /** Niveles ordenados por valor; valores y etiquetas sin repetir. */
    private List<FeedbackScaleLevel> niveles(GuardarEscalaFeedbackDto dto) {
        if (dto.getNiveles() == null || dto.getNiveles().size() < 2) {
            throw new IllegalArgumentException("Una escala necesita al menos 2 niveles.");
        }
        Set<BigDecimal> valores = new HashSet<>();
        Set<String> etiquetas = new HashSet<>();
        List<NivelEscalaFeedbackDto> ordenados = new ArrayList<>(dto.getNiveles());
        ordenados.sort(Comparator.comparing(NivelEscalaFeedbackDto::getValor));
        List<FeedbackScaleLevel> niveles = new ArrayList<>();
        for (int i = 0; i < ordenados.size(); i++) {
            NivelEscalaFeedbackDto n = ordenados.get(i);
            if (!valores.add(n.getValor().stripTrailingZeros())) {
                throw new IllegalArgumentException("Hay niveles con el mismo valor (" + n.getValor() + ").");
            }
            if (!etiquetas.add(n.getEtiqueta().trim().toLowerCase())) {
                throw new IllegalArgumentException("Hay niveles con la misma etiqueta (" + n.getEtiqueta().trim() + ").");
            }
            niveles.add(FeedbackScaleLevel.builder()
                    .valor(n.getValor())
                    .etiqueta(n.getEtiqueta().trim())
                    .orden(i + 1)
                    .build());
        }
        return niveles;
    }

    private static boolean mismosNiveles(List<FeedbackScaleLevel> actuales, List<FeedbackScaleLevel> nuevos) {
        if (actuales.size() != nuevos.size()) {
            return false;
        }
        for (int i = 0; i < actuales.size(); i++) {
            FeedbackScaleLevel a = actuales.get(i);
            FeedbackScaleLevel b = nuevos.get(i);
            if (a.getValor().compareTo(b.getValor()) != 0 || !a.getEtiqueta().equals(b.getEtiqueta())) {
                return false;
            }
        }
        return true;
    }
}
