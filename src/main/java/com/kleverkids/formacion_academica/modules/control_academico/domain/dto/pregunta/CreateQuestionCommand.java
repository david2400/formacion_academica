package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    property = "question_type",
    visible = true
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = CreateQuestionCommand.MultipleChoiceSingle.class, name = "multiple_choice_single"),
    @JsonSubTypes.Type(value = CreateQuestionCommand.MultipleChoiceMulti.class, name = "multiple_choice_multi"),
    @JsonSubTypes.Type(value = CreateQuestionCommand.TrueFalse.class, name = "true_false"),
    @JsonSubTypes.Type(value = CreateQuestionCommand.OpenShort.class, name = "open_short"),
    @JsonSubTypes.Type(value = CreateQuestionCommand.OpenLong.class, name = "open_long"),
    @JsonSubTypes.Type(value = CreateQuestionCommand.Numeric.class, name = "numeric"),
    @JsonSubTypes.Type(value = CreateQuestionCommand.Scale.class, name = "scale"),
    @JsonSubTypes.Type(value = CreateQuestionCommand.Ordering.class, name = "ordering"),
    @JsonSubTypes.Type(value = CreateQuestionCommand.Matching.class, name = "matching")
})
public sealed interface CreateQuestionCommand permits
    CreateQuestionCommand.MultipleChoiceSingle,
    CreateQuestionCommand.MultipleChoiceMulti,
    CreateQuestionCommand.TrueFalse,
    CreateQuestionCommand.OpenShort,
    CreateQuestionCommand.OpenLong,
    CreateQuestionCommand.Numeric,
    CreateQuestionCommand.Scale,
    CreateQuestionCommand.Ordering,
    CreateQuestionCommand.Matching {
    
    @JsonProperty("question_text")
    String questionText();
    
    @JsonProperty("question_type")
    String questionType();
    
    String difficulty();
    
    @JsonProperty("max_score")
    Integer maxScore();
    
    @JsonProperty("theme_id")
    Long themeId();
    
    List<MediaDto> media();
    String hint();
    String explanation();
    List<String> tags();
    Map<String, Object> metadata();
    
    record MultipleChoiceSingle(
        String questionText,
        String questionType,
        String difficulty,
        Integer maxScore,
        Long themeId,
        List<MediaDto> media,
        String hint,
        String explanation,
        List<String> tags,
        Map<String, Object> metadata,
        List<OptionDto> options,
        Long correctOptionId
    ) implements CreateQuestionCommand {}
    
    record MultipleChoiceMulti(
        String questionText,
        String questionType,
        String difficulty,
        Integer maxScore,
        Long themeId,
        List<MediaDto> media,
        String hint,
        String explanation,
        List<String> tags,
        Map<String, Object> metadata,
        List<OptionDto> options,
        List<Long> correctOptionIds,
        Integer minSelections,
        Integer maxSelections
    ) implements CreateQuestionCommand {}
    
    record TrueFalse(
        String questionText,
        String questionType,
        String difficulty,
        Integer maxScore,
        Long themeId,
        List<MediaDto> media,
        String hint,
        String explanation,
        List<String> tags,
        Map<String, Object> metadata,
        Boolean correctAnswer
    ) implements CreateQuestionCommand {}
    
    record OpenShort(
        String questionText,
        String questionType,
        String difficulty,
        Integer maxScore,
        Long themeId,
        List<MediaDto> media,
        String hint,
        String explanation,
        List<String> tags,
        Map<String, Object> metadata,
        List<String> acceptedAnswers,
        boolean caseSensitive,
        Integer maxLength
    ) implements CreateQuestionCommand {}
    
    record OpenLong(
        String questionText,
        String questionType,
        String difficulty,
        Integer maxScore,
        Long themeId,
        List<MediaDto> media,
        String hint,
        String explanation,
        List<String> tags,
        Map<String, Object> metadata,
        RubricaDto rubric,
        Integer minWords,
        Integer maxWords,
        boolean allowAttachments
    ) implements CreateQuestionCommand {}
    
    record Numeric(
        String questionText,
        String questionType,
        String difficulty,
        Integer maxScore,
        Long themeId,
        List<MediaDto> media,
        String hint,
        String explanation,
        List<String> tags,
        Map<String, Object> metadata,
        BigDecimal correctValue,
        BigDecimal tolerance,
        String unit,
        Integer decimalPlaces
    ) implements CreateQuestionCommand {}
    
    record Scale(
        String questionText,
        String questionType,
        String difficulty,
        Integer maxScore,
        Long themeId,
        List<MediaDto> media,
        String hint,
        String explanation,
        List<String> tags,
        Map<String, Object> metadata,
        ScaleConfigDto scaleConfig,
        Integer expectedValue
    ) implements CreateQuestionCommand {}
    
    record Ordering(
        String questionText,
        String questionType,
        String difficulty,
        Integer maxScore,
        Long themeId,
        List<MediaDto> media,
        String hint,
        String explanation,
        List<String> tags,
        Map<String, Object> metadata,
        List<OrderingItemDto> items,
        Boolean partialCredit
    ) implements CreateQuestionCommand {}
    
    record Matching(
        String questionText,
        String questionType,
        String difficulty,
        Integer maxScore,
        Long themeId,
        List<MediaDto> media,
        String hint,
        String explanation,
        List<String> tags,
        Map<String, Object> metadata,
        List<MatchingPairDto> pairs,
        Boolean partialCredit
    ) implements CreateQuestionCommand {}
}
