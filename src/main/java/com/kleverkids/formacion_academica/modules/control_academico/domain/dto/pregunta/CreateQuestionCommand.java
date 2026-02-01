package com.kleverkids.formacion_academica.modules.questions.application.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    property = "questionType",
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
    
    String questionText();
    String questionType();
    String difficulty();
    int maxScore();
    UUID themeId();
    List<MediaDto> media();
    String hint();
    String explanation();
    List<String> tags();
    Map<String, Object> metadata();
    
    record MultipleChoiceSingle(
        String questionText,
        String questionType,
        String difficulty,
        int maxScore,
        UUID themeId,
        List<MediaDto> media,
        String hint,
        String explanation,
        List<String> tags,
        Map<String, Object> metadata,
        List<OptionDto> options,
        UUID correctOptionId
    ) implements CreateQuestionCommand {}
    
    record MultipleChoiceMulti(
        String questionText,
        String questionType,
        String difficulty,
        int maxScore,
        UUID themeId,
        List<MediaDto> media,
        String hint,
        String explanation,
        List<String> tags,
        Map<String, Object> metadata,
        List<OptionDto> options,
        List<UUID> correctOptionIds,
        Integer minSelections,
        Integer maxSelections
    ) implements CreateQuestionCommand {}
    
    record TrueFalse(
        String questionText,
        String questionType,
        String difficulty,
        int maxScore,
        UUID themeId,
        List<MediaDto> media,
        String hint,
        String explanation,
        List<String> tags,
        Map<String, Object> metadata,
        boolean correctAnswer
    ) implements CreateQuestionCommand {}
    
    record OpenShort(
        String questionText,
        String questionType,
        String difficulty,
        int maxScore,
        UUID themeId,
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
        int maxScore,
        UUID themeId,
        List<MediaDto> media,
        String hint,
        String explanation,
        List<String> tags,
        Map<String, Object> metadata,
        RubricDto rubric,
        Integer minWords,
        Integer maxWords,
        boolean allowAttachments
    ) implements CreateQuestionCommand {}
    
    record Numeric(
        String questionText,
        String questionType,
        String difficulty,
        int maxScore,
        UUID themeId,
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
        int maxScore,
        UUID themeId,
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
        int maxScore,
        UUID themeId,
        List<MediaDto> media,
        String hint,
        String explanation,
        List<String> tags,
        Map<String, Object> metadata,
        List<OrderingItemDto> items,
        boolean partialCredit
    ) implements CreateQuestionCommand {}
    
    record Matching(
        String questionText,
        String questionType,
        String difficulty,
        int maxScore,
        UUID themeId,
        List<MediaDto> media,
        String hint,
        String explanation,
        List<String> tags,
        Map<String, Object> metadata,
        List<MatchingPairDto> pairs,
        boolean partialCredit
    ) implements CreateQuestionCommand {}
}
