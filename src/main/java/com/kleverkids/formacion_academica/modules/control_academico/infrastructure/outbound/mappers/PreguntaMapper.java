package com.kleverkids.formacion_academica.modules.control_academico.application.services.pregunta.mapper;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.PreguntaDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring")
public interface PreguntaMapper {

    @Mapping(target = "questionType", expression = "java(obtenerTipoPregunta(entity))")
    @Mapping(target = "questionText", source = "textoPregunta")
    @Mapping(target = "difficulty", source = "dificultad")
    @Mapping(target = "maxScore", source = "puntajeMaximo")
    @Mapping(target = "themeId", source = "temaId")
    @Mapping(target = "specificData", expression = "java(obtenerDatosEspecificos(entity))")
    PreguntaDto toDto(PreguntaEntity entity);

    default String obtenerTipoPregunta(PreguntaEntity entity) {
        return switch (entity) {
            case PreguntaOpcionMultipleUnicaEntity e -> "multiple_choice_single";
            case PreguntaOpcionMultipleEntity e -> "multiple_choice_multi";
            case PreguntaVerdaderoFalsoEntity e -> "true_false";
            case PreguntaAbiertaCortaEntity e -> "open_short";
            case PreguntaAbiertaLargaEntity e -> "open_long";
            case PreguntaNumericaEntity e -> "numeric";
            case PreguntaEscalaEntity e -> "scale";
            case PreguntaOrdenamientoEntity e -> "ordering";
            case PreguntaEmparejamientoEntity e -> "matching";
            default -> "unknown";
        };
    }

    /**
     * Expone los datos específicos de cada tipo con las MISMAS llaves que espera
     * CreateQuestionCommand al escribir (snake_case), para que el cliente pueda
     * releer una pregunta y reenviarla sin traducir nombres.
     *
     * Ojo: las llaves de un Map no las renombra la estrategia SNAKE_CASE de
     * Jackson, por eso se escriben aquí explícitamente en snake_case.
     */
    default Map<String, Object> obtenerDatosEspecificos(PreguntaEntity entity) {
        Map<String, Object> datos = new LinkedHashMap<>();

        switch (entity) {
            case PreguntaOpcionMultipleUnicaEntity e -> {
                datos.put("options", mapearOpciones(e.getOptions()));
                datos.put("correct_option_id", e.getCorrectOptionId());
            }
            case PreguntaOpcionMultipleEntity e -> {
                datos.put("options", mapearOpciones(e.getOptions()));
                datos.put("correct_option_ids", e.getCorrectOptionIds());
                datos.put("min_selections", e.getMinSelections());
                datos.put("max_selections", e.getMaxSelections());
            }
            case PreguntaVerdaderoFalsoEntity e -> {
                datos.put("correct_answer", e.getCorrectAnswer());
            }
            case PreguntaAbiertaCortaEntity e -> {
                datos.put("accepted_answers", e.getAcceptedAnswers());
                datos.put("case_sensitive", e.getCaseSensitive());
                datos.put("max_length", e.getMaxLength());
            }
            case PreguntaAbiertaLargaEntity e -> {
                datos.put("rubric", e.getRubric());
                datos.put("min_words", e.getMinWords());
                datos.put("max_words", e.getMaxWords());
                datos.put("allow_attachments", e.getAllowAttachments());
            }
            case PreguntaNumericaEntity e -> {
                datos.put("correct_value", e.getCorrectValue());
                datos.put("tolerance", e.getTolerance());
                datos.put("unit", e.getUnit());
                datos.put("decimal_places", e.getDecimalPlaces());
            }
            case PreguntaEscalaEntity e -> {
                datos.put("scale_config", mapearConfigEscala(e.getScaleConfig()));
                datos.put("expected_value", e.getExpectedValue());
            }
            case PreguntaOrdenamientoEntity e -> {
                datos.put("items", mapearItems(e.getItems()));
                datos.put("partial_credit", e.getPartialCredit());
            }
            case PreguntaEmparejamientoEntity e -> {
                datos.put("pairs", mapearPares(e.getPairs()));
                datos.put("partial_credit", e.getPartialCredit());
            }
            default -> {}
        }

        return datos;
    }

    private List<Map<String, Object>> mapearOpciones(List<OpcionEmbeddable> opciones) {
        if (opciones == null) return null;

        return opciones.stream()
            .map(opcion -> {
                Map<String, Object> datos = new LinkedHashMap<>();
                datos.put("id", opcion.getId());
                datos.put("text", opcion.getTexto());
                datos.put("is_correct", opcion.esCorrecta());
                datos.put("media", mapearMedia(opcion.getMedia()));
                return datos;
            })
            .toList();
    }

    private List<Map<String, Object>> mapearItems(List<OrderingItemEmbeddable> items) {
        if (items == null) return null;

        return items.stream()
            .map(item -> {
                Map<String, Object> datos = new LinkedHashMap<>();
                datos.put("id", item.getId());
                datos.put("text", item.getText());
                datos.put("correct_position", item.getCorrectPosition());
                datos.put("media", mapearMedia(item.getMedia()));
                return datos;
            })
            .toList();
    }

    private List<Map<String, Object>> mapearPares(List<ParEmparejamientoEmbeddable> pares) {
        if (pares == null) return null;

        return pares.stream()
            .map(par -> {
                Map<String, Object> datos = new LinkedHashMap<>();
                datos.put("id", par.getId());
                datos.put("left_item", par.getItemIzquierdo());
                datos.put("right_item", par.getItemDerecho());
                datos.put("left_media", mapearMedia(par.getMediaIzquierda()));
                datos.put("right_media", mapearMedia(par.getMediaDerecha()));
                return datos;
            })
            .toList();
    }

    private Map<String, Object> mapearConfigEscala(ScaleConfigEmbeddable config) {
        if (config == null) return null;

        Map<String, Object> datos = new LinkedHashMap<>();
        datos.put("min_value", config.getMinValue());
        datos.put("max_value", config.getMaxValue());
        datos.put("min_label", config.getMinLabel());
        datos.put("max_label", config.getMaxLabel());
        datos.put("labels", config.getLabels());
        return datos;
    }

    private Map<String, Object> mapearMedia(MediaEmbeddable media) {
        if (media == null) return null;

        Map<String, Object> datos = new LinkedHashMap<>();
        datos.put("id", media.getId());
        datos.put("type", media.getType());
        datos.put("url", media.getUrl());
        datos.put("alt_text", media.getAltText());
        return datos;
    }

}
