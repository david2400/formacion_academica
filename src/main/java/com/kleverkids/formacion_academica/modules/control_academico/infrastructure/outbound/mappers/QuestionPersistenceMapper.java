package com.kleverkids.formacion_academica.modules.questions.infrastructure.adapter.out.persistence.mapper;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.pregunta.*;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.*;
import com.kleverkids.formacion_academica.modules.questions.infrastructure.adapter.out.persistence.entity.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuestionPersistenceMapper {
    
    public QuestionEntity toEntity(Question question) {
        return switch (question) {
            case MultipleChoiceSingleQuestion q -> toMultipleChoiceSingleEntity(q);
            case MultipleChoiceMultiQuestion q -> toMultipleChoiceMultiEntity(q);
            case TrueFalseQuestion q -> toTrueFalseEntity(q);
            case OpenShortQuestion q -> toOpenShortEntity(q);
            case OpenLongQuestion q -> toOpenLongEntity(q);
            case NumericQuestion q -> toNumericEntity(q);
            case ScaleQuestion q -> toScaleEntity(q);
            case OrderingQuestion q -> toOrderingEntity(q);
            case MatchingQuestion q -> toMatchingEntity(q);
            default -> throw new IllegalArgumentException("Unknown question type");
        };
    }
    
    public Question toDomain(QuestionEntity entity) {
        return switch (entity) {
            case MultipleChoiceSingleQuestionEntity e -> toMultipleChoiceSingleDomain(e);
            case MultipleChoiceMultiQuestionEntity e -> toMultipleChoiceMultiDomain(e);
            case TrueFalseQuestionEntity e -> toTrueFalseDomain(e);
            case OpenShortQuestionEntity e -> toOpenShortDomain(e);
            case OpenLongQuestionEntity e -> toOpenLongDomain(e);
            case NumericQuestionEntity e -> toNumericDomain(e);
            case ScaleQuestionEntity e -> toScaleDomain(e);
            case OrderingQuestionEntity e -> toOrderingDomain(e);
            case MatchingQuestionEntity e -> toMatchingDomain(e);
            default -> throw new IllegalArgumentException("Unknown question entity type");
        };
    }
    
    // Base entity mapping
    private void mapBaseToEntity(Question q, QuestionEntity e) {
        e.setId(q.getId());
        e.setQuestionText(q.getQuestionText());
        e.setDifficulty(q.getDifficulty().getValue());
        e.setMaxScore(q.getMaxScore());
        e.setThemeId(q.getThemeId());
        e.setHint(q.getHint());
        e.setExplanation(q.getExplanation());
        e.setTags(q.getTags());
        e.setMetadata(q.getMetadata());
        e.setMedia(mapMediaToEmbeddable(q.getMedia()));
        e.setCreatedAt(q.getCreatedAt());
        e.setUpdatedAt(q.getUpdatedAt());
        e.setDeleted(q.isDeleted());
        e.setVersion(q.getVersion());
    }
    
    private void mapBaseToDomain(QuestionEntity e, Question q) {
        q.setId(e.getId());
        q.setQuestionText(e.getQuestionText());
        q.setDifficulty(Difficulty.fromValue(e.getDifficulty()));
        q.setMaxScore(e.getMaxScore());
        q.setThemeId(e.getThemeId());
        q.setHint(e.getHint());
        q.setExplanation(e.getExplanation());
        q.setTags(e.getTags());
        q.setMetadata(e.getMetadata());
        q.setMedia(mapMediaToDomain(e.getMedia()));
        q.setCreatedAt(e.getCreatedAt());
        q.setUpdatedAt(e.getUpdatedAt());
        q.setDeleted(e.isDeleted());
        q.setVersion(e.getVersion());
    }
    
    // Multiple Choice Single
    private MultipleChoiceSingleQuestionEntity toMultipleChoiceSingleEntity(MultipleChoiceSingleQuestion q) {
        MultipleChoiceSingleQuestionEntity e = new MultipleChoiceSingleQuestionEntity();
        mapBaseToEntity(q, e);
        e.setOptions(mapOptionsToEmbeddable(q.getOptions()));
        e.setCorrectOptionId(q.getCorrectOptionId());
        return e;
    }
    
    private MultipleChoiceSingleQuestion toMultipleChoiceSingleDomain(MultipleChoiceSingleQuestionEntity e) {
        MultipleChoiceSingleQuestion q = new MultipleChoiceSingleQuestion();
        mapBaseToDomain(e, q);
        q.setOptions(mapOptionsToDomain(e.getOptions()));
        q.setCorrectOptionId(e.getCorrectOptionId());
        return q;
    }
    
    // Multiple Choice Multi
    private MultipleChoiceMultiQuestionEntity toMultipleChoiceMultiEntity(MultipleChoiceMultiQuestion q) {
        MultipleChoiceMultiQuestionEntity e = new MultipleChoiceMultiQuestionEntity();
        mapBaseToEntity(q, e);
        e.setOptions(mapOptionsToEmbeddable(q.getOptions()));
        e.setCorrectOptionIds(q.getCorrectOptionIds());
        e.setMinSelections(q.getMinSelections());
        e.setMaxSelections(q.getMaxSelections());
        return e;
    }
    
    private MultipleChoiceMultiQuestion toMultipleChoiceMultiDomain(MultipleChoiceMultiQuestionEntity e) {
        MultipleChoiceMultiQuestion q = new MultipleChoiceMultiQuestion();
        mapBaseToDomain(e, q);
        q.setOptions(mapOptionsToDomain(e.getOptions()));
        q.setCorrectOptionIds(e.getCorrectOptionIds());
        q.setMinSelections(e.getMinSelections());
        q.setMaxSelections(e.getMaxSelections());
        return q;
    }
    
    // True/False
    private TrueFalseQuestionEntity toTrueFalseEntity(TrueFalseQuestion q) {
        TrueFalseQuestionEntity e = new TrueFalseQuestionEntity();
        mapBaseToEntity(q, e);
        e.setCorrectAnswer(q.isCorrectAnswer());
        return e;
    }
    
    private TrueFalseQuestion toTrueFalseDomain(TrueFalseQuestionEntity e) {
        TrueFalseQuestion q = new TrueFalseQuestion();
        mapBaseToDomain(e, q);
        q.setCorrectAnswer(e.isCorrectAnswer());
        return q;
    }
    
    // Open Short
    private OpenShortQuestionEntity toOpenShortEntity(OpenShortQuestion q) {
        OpenShortQuestionEntity e = new OpenShortQuestionEntity();
        mapBaseToEntity(q, e);
        e.setAcceptedAnswers(q.getAcceptedAnswers());
        e.setCaseSensitive(q.isCaseSensitive());
        e.setMaxLength(q.getMaxLength());
        return e;
    }
    
    private OpenShortQuestion toOpenShortDomain(OpenShortQuestionEntity e) {
        OpenShortQuestion q = new OpenShortQuestion();
        mapBaseToDomain(e, q);
        q.setAcceptedAnswers(e.getAcceptedAnswers());
        q.setCaseSensitive(e.isCaseSensitive());
        q.setMaxLength(e.getMaxLength());
        return q;
    }
    
    // Open Long
    private OpenLongQuestionEntity toOpenLongEntity(OpenLongQuestion q) {
        OpenLongQuestionEntity e = new OpenLongQuestionEntity();
        mapBaseToEntity(q, e);
        e.setRubric(mapRubricToEmbeddable(q.getRubric()));
        e.setMinWords(q.getMinWords());
        e.setMaxWords(q.getMaxWords());
        e.setAllowAttachments(q.isAllowAttachments());
        return e;
    }
    
    private OpenLongQuestion toOpenLongDomain(OpenLongQuestionEntity e) {
        OpenLongQuestion q = new OpenLongQuestion();
        mapBaseToDomain(e, q);
        q.setRubric(mapRubricToDomain(e.getRubric()));
        q.setMinWords(e.getMinWords());
        q.setMaxWords(e.getMaxWords());
        q.setAllowAttachments(e.isAllowAttachments());
        return q;
    }
    
    // Numeric
    private NumericQuestionEntity toNumericEntity(NumericQuestion q) {
        NumericQuestionEntity e = new NumericQuestionEntity();
        mapBaseToEntity(q, e);
        e.setCorrectValue(q.getCorrectValue());
        e.setTolerance(q.getTolerance());
        e.setUnit(q.getUnit());
        e.setDecimalPlaces(q.getDecimalPlaces());
        return e;
    }
    
    private NumericQuestion toNumericDomain(NumericQuestionEntity e) {
        NumericQuestion q = new NumericQuestion();
        mapBaseToDomain(e, q);
        q.setCorrectValue(e.getCorrectValue());
        q.setTolerance(e.getTolerance());
        q.setUnit(e.getUnit());
        q.setDecimalPlaces(e.getDecimalPlaces());
        return q;
    }
    
    // Scale
    private ScaleQuestionEntity toScaleEntity(ScaleQuestion q) {
        ScaleQuestionEntity e = new ScaleQuestionEntity();
        mapBaseToEntity(q, e);
        e.setScaleConfig(mapScaleConfigToEmbeddable(q.getScaleConfig()));
        e.setExpectedValue(q.getExpectedValue());
        return e;
    }
    
    private ScaleQuestion toScaleDomain(ScaleQuestionEntity e) {
        ScaleQuestion q = new ScaleQuestion();
        mapBaseToDomain(e, q);
        q.setScaleConfig(mapScaleConfigToDomain(e.getScaleConfig()));
        q.setExpectedValue(e.getExpectedValue());
        return q;
    }
    
    // Ordering
    private OrderingQuestionEntity toOrderingEntity(OrderingQuestion q) {
        OrderingQuestionEntity e = new OrderingQuestionEntity();
        mapBaseToEntity(q, e);
        e.setItems(mapOrderingItemsToEmbeddable(q.getItems()));
        e.setPartialCredit(q.isPartialCredit());
        return e;
    }
    
    private OrderingQuestion toOrderingDomain(OrderingQuestionEntity e) {
        OrderingQuestion q = new OrderingQuestion();
        mapBaseToDomain(e, q);
        q.setItems(mapOrderingItemsToDomain(e.getItems()));
        q.setPartialCredit(e.isPartialCredit());
        return q;
    }
    
    // Matching
    private MatchingQuestionEntity toMatchingEntity(MatchingQuestion q) {
        MatchingQuestionEntity e = new MatchingQuestionEntity();
        mapBaseToEntity(q, e);
        e.setPairs(mapMatchingPairsToEmbeddable(q.getPairs()));
        e.setPartialCredit(q.isPartialCredit());
        return e;
    }
    
    private MatchingQuestion toMatchingDomain(MatchingQuestionEntity e) {
        MatchingQuestion q = new MatchingQuestion();
        mapBaseToDomain(e, q);
        q.setPairs(mapMatchingPairsToDomain(e.getPairs()));
        q.setPartialCredit(e.isPartialCredit());
        return q;
    }
    
    // Helper methods for nested objects
    private List<MediaEmbeddable> mapMediaToEmbeddable(List<Media> media) {
        if (media == null) return null;
        return media.stream()
            .map(m -> new MediaEmbeddable(m.getId(), m.getType(), m.getUrl(), m.getAltText()))
            .collect(Collectors.toList());
    }
    
    private List<Media> mapMediaToDomain(List<MediaEmbeddable> media) {
        if (media == null) return null;
        return media.stream()
            .map(m -> Media.create(m.getId(), m.getType(), m.getUrl(), m.getAltText()))
            .collect(Collectors.toList());
    }
    
    private List<OptionEmbeddable> mapOptionsToEmbeddable(List<Option> options) {
        if (options == null) return null;
        return options.stream()
            .map(o -> new OptionEmbeddable(o.getId(), o.getText(),
                o.getMedia() != null ? new MediaEmbeddable(o.getMedia().getId(), o.getMedia().getType(),
                    o.getMedia().getUrl(), o.getMedia().getAltText()) : null,
                o.isCorrect()))
            .collect(Collectors.toList());
    }
    
    private List<Option> mapOptionsToDomain(List<OptionEmbeddable> options) {
        if (options == null) return null;
        return options.stream()
            .map(o -> Option.create(o.getId(), o.getText(),
                o.getMedia() != null ? Media.create(o.getMedia().getId(), o.getMedia().getType(),
                    o.getMedia().getUrl(), o.getMedia().getAltText()) : null,
                o.isCorrect()))
            .collect(Collectors.toList());
    }
    
    private RubricEmbeddable mapRubricToEmbeddable(Rubric rubric) {
        if (rubric == null) return null;
        return new RubricEmbeddable(rubric.getCriteria().stream()
            .map(c -> new RubricEmbeddable.CriterionEmbeddable(c.name(), c.description(), c.maxScore(),
                c.levels().stream()
                    .map(l -> new RubricEmbeddable.LevelEmbeddable(l.name(), l.description(), l.score()))
                    .collect(Collectors.toList())))
            .collect(Collectors.toList()));
    }
    
    private Rubric mapRubricToDomain(RubricEmbeddable rubric) {
        if (rubric == null) return null;
        return Rubric.create(rubric.getCriteria().stream()
            .map(c -> new Rubric.RubricCriterion(c.getName(), c.getDescription(), c.getMaxScore(),
                c.getLevels().stream()
                    .map(l -> new Rubric.RubricLevel(l.getName(), l.getDescription(), l.getScore()))
                    .collect(Collectors.toList())))
            .collect(Collectors.toList()));
    }
    
    private ScaleConfigEmbeddable mapScaleConfigToEmbeddable(ScaleConfig config) {
        if (config == null) return null;
        return new ScaleConfigEmbeddable(config.getMinValue(), config.getMaxValue(),
            config.getMinLabel(), config.getMaxLabel(), config.getLabels());
    }
    
    private ScaleConfig mapScaleConfigToDomain(ScaleConfigEmbeddable config) {
        if (config == null) return null;
        return ScaleConfig.create(config.getMinValue(), config.getMaxValue(),
            config.getMinLabel(), config.getMaxLabel(), config.getLabels());
    }
    
    private List<OrderingItemEmbeddable> mapOrderingItemsToEmbeddable(List<OrderingItem> items) {
        if (items == null) return null;
        return items.stream()
            .map(i -> new OrderingItemEmbeddable(i.getId(), i.getText(), i.getCorrectPosition(),
                i.getMedia() != null ? new MediaEmbeddable(i.getMedia().getId(), i.getMedia().getType(),
                    i.getMedia().getUrl(), i.getMedia().getAltText()) : null))
            .collect(Collectors.toList());
    }
    
    private List<OrderingItem> mapOrderingItemsToDomain(List<OrderingItemEmbeddable> items) {
        if (items == null) return null;
        return items.stream()
            .map(i -> OrderingItem.create(i.getId(), i.getText(), i.getCorrectPosition(),
                i.getMedia() != null ? Media.create(i.getMedia().getId(), i.getMedia().getType(),
                    i.getMedia().getUrl(), i.getMedia().getAltText()) : null))
            .collect(Collectors.toList());
    }
    
    private List<MatchingPairEmbeddable> mapMatchingPairsToEmbeddable(List<MatchingPair> pairs) {
        if (pairs == null) return null;
        return pairs.stream()
            .map(p -> new MatchingPairEmbeddable(p.getId(), p.getLeftItem(), p.getRightItem(),
                p.getLeftMedia() != null ? new MediaEmbeddable(p.getLeftMedia().getId(), p.getLeftMedia().getType(),
                    p.getLeftMedia().getUrl(), p.getLeftMedia().getAltText()) : null,
                p.getRightMedia() != null ? new MediaEmbeddable(p.getRightMedia().getId(), p.getRightMedia().getType(),
                    p.getRightMedia().getUrl(), p.getRightMedia().getAltText()) : null))
            .collect(Collectors.toList());
    }
    
    private List<MatchingPair> mapMatchingPairsToDomain(List<MatchingPairEmbeddable> pairs) {
        if (pairs == null) return null;
        return pairs.stream()
            .map(p -> MatchingPair.create(p.getId(), p.getLeftItem(), p.getRightItem(),
                p.getLeftMedia() != null ? Media.create(p.getLeftMedia().getId(), p.getLeftMedia().getType(),
                    p.getLeftMedia().getUrl(), p.getLeftMedia().getAltText()) : null,
                p.getRightMedia() != null ? Media.create(p.getRightMedia().getId(), p.getRightMedia().getType(),
                    p.getRightMedia().getUrl(), p.getRightMedia().getAltText()) : null))
            .collect(Collectors.toList());
    }
}
