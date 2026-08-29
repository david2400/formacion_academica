package com.kleverkids.formacion_academica.modules.control_academico.application.services.pregunta.mapper;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.PreguntaDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.HashMap;
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

    default Map<String, Object> obtenerDatosEspecificos(PreguntaEntity entity) {
        Map<String, Object> datos = new HashMap<>();

        switch (entity) {
            case PreguntaOpcionMultipleUnicaEntity e -> {
                datos.put("options", e.getOptions());
                datos.put("correctOptionId", e.getCorrectOptionId());
            }
            case PreguntaOpcionMultipleEntity e -> {
                datos.put("options", e.getOptions());
                datos.put("correctOptionIds", e.getCorrectOptionIds());
                datos.put("minSelections", e.getMinSelections());
                datos.put("maxSelections", e.getMaxSelections());
            }
            case PreguntaVerdaderoFalsoEntity e -> {
                datos.put("correctAnswer", e.getCorrectAnswer());
            }
            case PreguntaAbiertaCortaEntity e -> {
                datos.put("acceptedAnswers", e.getAcceptedAnswers());
                datos.put("caseSensitive", e.getCaseSensitive());
                datos.put("maxLength", e.getMaxLength());
            }
            case PreguntaAbiertaLargaEntity e -> {
                datos.put("rubric", e.getRubric());
                datos.put("minWords", e.getMinWords());
                datos.put("maxWords", e.getMaxWords());
                datos.put("allowAttachments", e.getAllowAttachments());
            }
            case PreguntaNumericaEntity e -> {
                datos.put("correctValue", e.getCorrectValue());
                datos.put("tolerance", e.getTolerance());
                datos.put("unit", e.getUnit());
                datos.put("decimalPlaces", e.getDecimalPlaces());
            }
            case PreguntaEscalaEntity e -> {
                datos.put("scaleConfig", e.getScaleConfig());
                datos.put("expectedValue", e.getExpectedValue());
            }
            case PreguntaOrdenamientoEntity e -> {
                datos.put("items", e.getItems());
                datos.put("partialCredit", e.getPartialCredit());
            }
            case PreguntaEmparejamientoEntity e -> {
                datos.put("pairs", e.getPairs());
                datos.put("partialCredit", e.getPartialCredit());
            }
            default -> {}
        }

        return datos;
    }

}
