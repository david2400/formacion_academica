package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

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

    @Data
    @Accessors(fluent = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static final class MultipleChoiceSingle implements CreateQuestionCommand {
        private String questionText;
        private String questionType;
        private String difficulty;
        private Integer maxScore;
        private Long themeId;
        private List<MediaDto> media;
        private String hint;
        private String explanation;
        private List<String> tags;
        private Map<String, Object> metadata;
        private List<OptionDto> options;
        private Long correctOptionId;
    }

    @Data
    @Accessors(fluent = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static final class MultipleChoiceMulti implements CreateQuestionCommand {
        private String questionText;
        private String questionType;
        private String difficulty;
        private Integer maxScore;
        private Long themeId;
        private List<MediaDto> media;
        private String hint;
        private String explanation;
        private List<String> tags;
        private Map<String, Object> metadata;
        private List<OptionDto> options;
        private List<Long> correctOptionIds;
        private Integer minSelections;
        private Integer maxSelections;
    }

    @Data
    @Accessors(fluent = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static final class TrueFalse implements CreateQuestionCommand {
        private String questionText;
        private String questionType;
        private String difficulty;
        private Integer maxScore;
        private Long themeId;
        private List<MediaDto> media;
        private String hint;
        private String explanation;
        private List<String> tags;
        private Map<String, Object> metadata;
        private Boolean correctAnswer;
    }

    @Data
    @Accessors(fluent = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static final class OpenShort implements CreateQuestionCommand {
        private String questionText;
        private String questionType;
        private String difficulty;
        private Integer maxScore;
        private Long themeId;
        private List<MediaDto> media;
        private String hint;
        private String explanation;
        private List<String> tags;
        private Map<String, Object> metadata;
        private List<String> acceptedAnswers;
        private boolean caseSensitive;
        private Integer maxLength;
    }

    @Data
    @Accessors(fluent = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static final class OpenLong implements CreateQuestionCommand {
        private String questionText;
        private String questionType;
        private String difficulty;
        private Integer maxScore;
        private Long themeId;
        private List<MediaDto> media;
        private String hint;
        private String explanation;
        private List<String> tags;
        private Map<String, Object> metadata;
        private RubricaDto rubric;
        private Integer minWords;
        private Integer maxWords;
        private boolean allowAttachments;
    }

    @Data
    @Accessors(fluent = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static final class Numeric implements CreateQuestionCommand {
        private String questionText;
        private String questionType;
        private String difficulty;
        private Integer maxScore;
        private Long themeId;
        private List<MediaDto> media;
        private String hint;
        private String explanation;
        private List<String> tags;
        private Map<String, Object> metadata;
        private BigDecimal correctValue;
        private BigDecimal tolerance;
        private String unit;
        private Integer decimalPlaces;
    }

    @Data
    @Accessors(fluent = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static final class Scale implements CreateQuestionCommand {
        private String questionText;
        private String questionType;
        private String difficulty;
        private Integer maxScore;
        private Long themeId;
        private List<MediaDto> media;
        private String hint;
        private String explanation;
        private List<String> tags;
        private Map<String, Object> metadata;
        private ScaleConfigDto scaleConfig;
        private Integer expectedValue;
    }

    @Data
    @Accessors(fluent = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static final class Ordering implements CreateQuestionCommand {
        private String questionText;
        private String questionType;
        private String difficulty;
        private Integer maxScore;
        private Long themeId;
        private List<MediaDto> media;
        private String hint;
        private String explanation;
        private List<String> tags;
        private Map<String, Object> metadata;
        private List<OrderingItemDto> items;
        private Boolean partialCredit;
    }

    @Data
    @Accessors(fluent = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static final class Matching implements CreateQuestionCommand {
        private String questionText;
        private String questionType;
        private String difficulty;
        private Integer maxScore;
        private Long themeId;
        private List<MediaDto> media;
        private String hint;
        private String explanation;
        private List<String> tags;
        private Map<String, Object> metadata;
        private List<MatchingPairDto> pairs;
        private Boolean partialCredit;
    }
}
