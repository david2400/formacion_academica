package com.kleverkids.formacion_academica.modules.questions.application.dto;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.pregunta.*;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public record QuestionResponse(
    UUID id,
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
    Instant createdAt,
    Instant updatedAt,
    Integer version,
    // Type-specific fields
    List<OptionDto> options,
    UUID correctOptionId,
    List<UUID> correctOptionIds,
    Integer minSelections,
    Integer maxSelections,
    Boolean correctAnswer,
    List<String> acceptedAnswers,
    Boolean caseSensitive,
    Integer maxLength,
    RubricDto rubric,
    Integer minWords,
    Integer maxWords,
    Boolean allowAttachments,
    BigDecimal correctValue,
    BigDecimal tolerance,
    String unit,
    Integer decimalPlaces,
    ScaleConfigDto scaleConfig,
    Integer expectedValue,
    List<OrderingItemDto> items,
    Boolean partialCredit,
    List<MatchingPairDto> pairs
) {
    
    public static QuestionResponse fromDomain(Question question) {
        QuestionResponseBuilder builder = new QuestionResponseBuilder()
            .id(question.getId())
            .questionText(question.getQuestionText())
            .questionType(question.getQuestionType().getValue())
            .difficulty(question.getDifficulty().getValue())
            .maxScore(question.getMaxScore())
            .themeId(question.getThemeId())
            .media(mapMedia(question.getMedia()))
            .hint(question.getHint())
            .explanation(question.getExplanation())
            .tags(question.getTags())
            .metadata(question.getMetadata())
            .createdAt(question.getCreatedAt())
            .updatedAt(question.getUpdatedAt())
            .version(question.getVersion());
        
        switch (question) {
            case MultipleChoiceSingleQuestion q -> builder
                .options(mapOptions(q.getOptions()))
                .correctOptionId(q.getCorrectOptionId());
            case MultipleChoiceMultiQuestion q -> builder
                .options(mapOptions(q.getOptions()))
                .correctOptionIds(q.getCorrectOptionIds())
                .minSelections(q.getMinSelections())
                .maxSelections(q.getMaxSelections());
            case TrueFalseQuestion q -> builder.correctAnswer(q.isCorrectAnswer());
            case OpenShortQuestion q -> builder
                .acceptedAnswers(q.getAcceptedAnswers())
                .caseSensitive(q.isCaseSensitive())
                .maxLength(q.getMaxLength());
            case OpenLongQuestion q -> builder
                .rubric(mapRubric(q.getRubric()))
                .minWords(q.getMinWords())
                .maxWords(q.getMaxWords())
                .allowAttachments(q.isAllowAttachments());
            case NumericQuestion q -> builder
                .correctValue(q.getCorrectValue())
                .tolerance(q.getTolerance())
                .unit(q.getUnit())
                .decimalPlaces(q.getDecimalPlaces());
            case ScaleQuestion q -> builder
                .scaleConfig(mapScaleConfig(q.getScaleConfig()))
                .expectedValue(q.getExpectedValue());
            case OrderingQuestion q -> builder
                .items(mapOrderingItems(q.getItems()))
                .partialCredit(q.isPartialCredit());
            case MatchingQuestion q -> builder
                .pairs(mapMatchingPairs(q.getPairs()))
                .partialCredit(q.isPartialCredit());
            default -> {}
        }
        
        return builder.build();
    }
    
    private static List<MediaDto> mapMedia(List<Media> mediaList) {
        if (mediaList == null) return null;
        return mediaList.stream()
            .map(m -> new MediaDto(m.getId(), m.getType(), m.getUrl(), m.getAltText()))
            .collect(Collectors.toList());
    }
    
    private static List<OptionDto> mapOptions(List<Option> options) {
        if (options == null) return null;
        return options.stream()
            .map(o -> new OptionDto(o.getId(), o.getText(), 
                o.getMedia() != null ? new MediaDto(o.getMedia().getId(), o.getMedia().getType(), 
                    o.getMedia().getUrl(), o.getMedia().getAltText()) : null, 
                o.isCorrect()))
            .collect(Collectors.toList());
    }
    
    private static RubricDto mapRubric(Rubric rubric) {
        if (rubric == null) return null;
        return new RubricDto(rubric.getCriteria().stream()
            .map(c -> new RubricDto.CriterionDto(c.name(), c.description(), c.maxScore(),
                c.levels().stream()
                    .map(l -> new RubricDto.LevelDto(l.name(), l.description(), l.score()))
                    .collect(Collectors.toList())))
            .collect(Collectors.toList()));
    }
    
    private static ScaleConfigDto mapScaleConfig(ScaleConfig config) {
        if (config == null) return null;
        return new ScaleConfigDto(config.getMinValue(), config.getMaxValue(), 
            config.getMinLabel(), config.getMaxLabel(), config.getLabels());
    }
    
    private static List<OrderingItemDto> mapOrderingItems(List<OrderingItem> items) {
        if (items == null) return null;
        return items.stream()
            .map(i -> new OrderingItemDto(i.getId(), i.getText(), i.getCorrectPosition(),
                i.getMedia() != null ? new MediaDto(i.getMedia().getId(), i.getMedia().getType(),
                    i.getMedia().getUrl(), i.getMedia().getAltText()) : null))
            .collect(Collectors.toList());
    }
    
    private static List<MatchingPairDto> mapMatchingPairs(List<MatchingPair> pairs) {
        if (pairs == null) return null;
        return pairs.stream()
            .map(p -> new MatchingPairDto(p.getId(), p.getLeftItem(), p.getRightItem(),
                p.getLeftMedia() != null ? new MediaDto(p.getLeftMedia().getId(), p.getLeftMedia().getType(),
                    p.getLeftMedia().getUrl(), p.getLeftMedia().getAltText()) : null,
                p.getRightMedia() != null ? new MediaDto(p.getRightMedia().getId(), p.getRightMedia().getType(),
                    p.getRightMedia().getUrl(), p.getRightMedia().getAltText()) : null))
            .collect(Collectors.toList());
    }
    
    private static class QuestionResponseBuilder {
        private UUID id;
        private String questionText;
        private String questionType;
        private String difficulty;
        private int maxScore;
        private UUID themeId;
        private List<MediaDto> media;
        private String hint;
        private String explanation;
        private List<String> tags;
        private Map<String, Object> metadata;
        private Instant createdAt;
        private Instant updatedAt;
        private Integer version;
        private List<OptionDto> options;
        private UUID correctOptionId;
        private List<UUID> correctOptionIds;
        private Integer minSelections;
        private Integer maxSelections;
        private Boolean correctAnswer;
        private List<String> acceptedAnswers;
        private Boolean caseSensitive;
        private Integer maxLength;
        private RubricDto rubric;
        private Integer minWords;
        private Integer maxWords;
        private Boolean allowAttachments;
        private BigDecimal correctValue;
        private BigDecimal tolerance;
        private String unit;
        private Integer decimalPlaces;
        private ScaleConfigDto scaleConfig;
        private Integer expectedValue;
        private List<OrderingItemDto> items;
        private Boolean partialCredit;
        private List<MatchingPairDto> pairs;
        
        QuestionResponseBuilder id(UUID id) { this.id = id; return this; }
        QuestionResponseBuilder questionText(String questionText) { this.questionText = questionText; return this; }
        QuestionResponseBuilder questionType(String questionType) { this.questionType = questionType; return this; }
        QuestionResponseBuilder difficulty(String difficulty) { this.difficulty = difficulty; return this; }
        QuestionResponseBuilder maxScore(int maxScore) { this.maxScore = maxScore; return this; }
        QuestionResponseBuilder themeId(UUID themeId) { this.themeId = themeId; return this; }
        QuestionResponseBuilder media(List<MediaDto> media) { this.media = media; return this; }
        QuestionResponseBuilder hint(String hint) { this.hint = hint; return this; }
        QuestionResponseBuilder explanation(String explanation) { this.explanation = explanation; return this; }
        QuestionResponseBuilder tags(List<String> tags) { this.tags = tags; return this; }
        QuestionResponseBuilder metadata(Map<String, Object> metadata) { this.metadata = metadata; return this; }
        QuestionResponseBuilder createdAt(Instant createdAt) { this.createdAt = createdAt; return this; }
        QuestionResponseBuilder updatedAt(Instant updatedAt) { this.updatedAt = updatedAt; return this; }
        QuestionResponseBuilder version(Integer version) { this.version = version; return this; }
        QuestionResponseBuilder options(List<OptionDto> options) { this.options = options; return this; }
        QuestionResponseBuilder correctOptionId(UUID correctOptionId) { this.correctOptionId = correctOptionId; return this; }
        QuestionResponseBuilder correctOptionIds(List<UUID> correctOptionIds) { this.correctOptionIds = correctOptionIds; return this; }
        QuestionResponseBuilder minSelections(Integer minSelections) { this.minSelections = minSelections; return this; }
        QuestionResponseBuilder maxSelections(Integer maxSelections) { this.maxSelections = maxSelections; return this; }
        QuestionResponseBuilder correctAnswer(Boolean correctAnswer) { this.correctAnswer = correctAnswer; return this; }
        QuestionResponseBuilder acceptedAnswers(List<String> acceptedAnswers) { this.acceptedAnswers = acceptedAnswers; return this; }
        QuestionResponseBuilder caseSensitive(Boolean caseSensitive) { this.caseSensitive = caseSensitive; return this; }
        QuestionResponseBuilder maxLength(Integer maxLength) { this.maxLength = maxLength; return this; }
        QuestionResponseBuilder rubric(RubricDto rubric) { this.rubric = rubric; return this; }
        QuestionResponseBuilder minWords(Integer minWords) { this.minWords = minWords; return this; }
        QuestionResponseBuilder maxWords(Integer maxWords) { this.maxWords = maxWords; return this; }
        QuestionResponseBuilder allowAttachments(Boolean allowAttachments) { this.allowAttachments = allowAttachments; return this; }
        QuestionResponseBuilder correctValue(BigDecimal correctValue) { this.correctValue = correctValue; return this; }
        QuestionResponseBuilder tolerance(BigDecimal tolerance) { this.tolerance = tolerance; return this; }
        QuestionResponseBuilder unit(String unit) { this.unit = unit; return this; }
        QuestionResponseBuilder decimalPlaces(Integer decimalPlaces) { this.decimalPlaces = decimalPlaces; return this; }
        QuestionResponseBuilder scaleConfig(ScaleConfigDto scaleConfig) { this.scaleConfig = scaleConfig; return this; }
        QuestionResponseBuilder expectedValue(Integer expectedValue) { this.expectedValue = expectedValue; return this; }
        QuestionResponseBuilder items(List<OrderingItemDto> items) { this.items = items; return this; }
        QuestionResponseBuilder partialCredit(Boolean partialCredit) { this.partialCredit = partialCredit; return this; }
        QuestionResponseBuilder pairs(List<MatchingPairDto> pairs) { this.pairs = pairs; return this; }
        
        QuestionResponse build() {
            return new QuestionResponse(id, questionText, questionType, difficulty, maxScore, themeId,
                media, hint, explanation, tags, metadata, createdAt, updatedAt, version,
                options, correctOptionId, correctOptionIds, minSelections, maxSelections,
                correctAnswer, acceptedAnswers, caseSensitive, maxLength, rubric, minWords, maxWords,
                allowAttachments, correctValue, tolerance, unit, decimalPlaces, scaleConfig,
                expectedValue, items, partialCredit, pairs);
        }
    }
}
