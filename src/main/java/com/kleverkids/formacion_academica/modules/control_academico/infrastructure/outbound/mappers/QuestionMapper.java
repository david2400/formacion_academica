package com.kleverkids.formacion_academica.modules.control_academico.application.services.pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.*;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.pregunta.*;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class QuestionMapper {
    
    public Question toDomain(CreateQuestionCommand command) {
        Difficulty difficulty = command.difficulty() != null 
            ? Difficulty.fromValue(command.difficulty()) 
            : Difficulty.BASIC;
        List<Media> mediaList = mapMediaList(command.media());
        
        return switch (command) {
            case CreateQuestionCommand.MultipleChoiceSingle cmd -> new MultipleChoiceSingleQuestion(
                null, cmd.questionText(), difficulty, cmd.maxScore(), cmd.themeId(),
                mediaList, cmd.hint(), cmd.explanation(), cmd.tags(), cmd.metadata(),
                mapOptions(cmd.options()), cmd.correctOptionId()
            );
            case CreateQuestionCommand.MultipleChoiceMulti cmd -> new MultipleChoiceMultiQuestion(
                null, cmd.questionText(), difficulty, cmd.maxScore(), cmd.themeId(),
                mediaList, cmd.hint(), cmd.explanation(), cmd.tags(), cmd.metadata(),
                mapOptions(cmd.options()), cmd.correctOptionIds(), cmd.minSelections(), cmd.maxSelections()
            );
            case CreateQuestionCommand.TrueFalse cmd -> new TrueFalseQuestion(
                null, cmd.questionText(), difficulty, cmd.maxScore(), cmd.themeId(),
                mediaList, cmd.hint(), cmd.explanation(), cmd.tags(), cmd.metadata(),
                cmd.correctAnswer()
            );
            case CreateQuestionCommand.OpenShort cmd -> new OpenShortQuestion(
                null, cmd.questionText(), difficulty, cmd.maxScore(), cmd.themeId(),
                mediaList, cmd.hint(), cmd.explanation(), cmd.tags(), cmd.metadata(),
                cmd.acceptedAnswers(), cmd.caseSensitive(), cmd.maxLength()
            );
            case CreateQuestionCommand.OpenLong cmd -> new OpenLongQuestion(
                null, cmd.questionText(), difficulty, cmd.maxScore(), cmd.themeId(),
                mediaList, cmd.hint(), cmd.explanation(), cmd.tags(), cmd.metadata(),
                mapRubric(cmd.rubric()), cmd.minWords(), cmd.maxWords(), cmd.allowAttachments()
            );
            case CreateQuestionCommand.Numeric cmd -> new NumericQuestion(
                null, cmd.questionText(), difficulty, cmd.maxScore(), cmd.themeId(),
                mediaList, cmd.hint(), cmd.explanation(), cmd.tags(), cmd.metadata(),
                cmd.correctValue(), cmd.tolerance(), cmd.unit(), cmd.decimalPlaces()
            );
            case CreateQuestionCommand.Scale cmd -> new ScaleQuestion(
                null, cmd.questionText(), difficulty, cmd.maxScore(), cmd.themeId(),
                mediaList, cmd.hint(), cmd.explanation(), cmd.tags(), cmd.metadata(),
                mapScaleConfig(cmd.scaleConfig()), cmd.expectedValue()
            );
            case CreateQuestionCommand.Ordering cmd -> new OrderingQuestion(
                null, cmd.questionText(), difficulty, cmd.maxScore(), cmd.themeId(),
                mediaList, cmd.hint(), cmd.explanation(), cmd.tags(), cmd.metadata(),
                mapOrderingItems(cmd.items()), cmd.partialCredit()
            );
            case CreateQuestionCommand.Matching cmd -> new MatchingQuestion(
                null, cmd.questionText(), difficulty, cmd.maxScore(), cmd.themeId(),
                mediaList, cmd.hint(), cmd.explanation(), cmd.tags(), cmd.metadata(),
                mapMatchingPairs(cmd.pairs()), cmd.partialCredit()
            );
        };
    }
    
    public void updateDomain(Question question, UpdateQuestionCommand cmd) {
        if (cmd.questionText() != null) question.setQuestionText(cmd.questionText());
        if (cmd.difficulty() != null) question.setDifficulty(Difficulty.fromValue(cmd.difficulty()));
        if (cmd.maxScore() != null) question.setMaxScore(cmd.maxScore());
        if (cmd.themeId() != null) question.setThemeId(cmd.themeId());
        if (cmd.media() != null) question.setMedia(mapMediaList(cmd.media()));
        if (cmd.hint() != null) question.setHint(cmd.hint());
        if (cmd.explanation() != null) question.setExplanation(cmd.explanation());
        if (cmd.tags() != null) question.setTags(cmd.tags());
        if (cmd.metadata() != null) question.setMetadata(cmd.metadata());
        
        switch (question) {
            case MultipleChoiceSingleQuestion q -> {
                if (cmd.options() != null) q.setOptions(mapOptions(cmd.options()));
                if (cmd.correctOptionId() != null) q.setCorrectOptionId(cmd.correctOptionId());
            }
            case MultipleChoiceMultiQuestion q -> {
                if (cmd.options() != null) q.setOptions(mapOptions(cmd.options()));
                if (cmd.correctOptionIds() != null) q.setCorrectOptionIds(cmd.correctOptionIds());
                if (cmd.minSelections() != null) q.setMinSelections(cmd.minSelections());
                if (cmd.maxSelections() != null) q.setMaxSelections(cmd.maxSelections());
            }
            case TrueFalseQuestion q -> {
                if (cmd.correctAnswer() != null) q.setCorrectAnswer(cmd.correctAnswer());
            }
            case OpenShortQuestion q -> {
                if (cmd.acceptedAnswers() != null) q.setAcceptedAnswers(cmd.acceptedAnswers());
                if (cmd.caseSensitive() != null) q.setCaseSensitive(cmd.caseSensitive());
                if (cmd.maxLength() != null) q.setMaxLength(cmd.maxLength());
            }
            case OpenLongQuestion q -> {
                if (cmd.rubric() != null) q.setRubric(mapRubric(cmd.rubric()));
                if (cmd.minWords() != null) q.setMinWords(cmd.minWords());
                if (cmd.maxWords() != null) q.setMaxWords(cmd.maxWords());
                if (cmd.allowAttachments() != null) q.setAllowAttachments(cmd.allowAttachments());
            }
            case NumericQuestion q -> {
                if (cmd.correctValue() != null) q.setCorrectValue(cmd.correctValue());
                if (cmd.tolerance() != null) q.setTolerance(cmd.tolerance());
                if (cmd.unit() != null) q.setUnit(cmd.unit());
                if (cmd.decimalPlaces() != null) q.setDecimalPlaces(cmd.decimalPlaces());
            }
            case ScaleQuestion q -> {
                if (cmd.scaleConfig() != null) q.setScaleConfig(mapScaleConfig(cmd.scaleConfig()));
                if (cmd.expectedValue() != null) q.setExpectedValue(cmd.expectedValue());
            }
            case OrderingQuestion q -> {
                if (cmd.items() != null) q.setItems(mapOrderingItems(cmd.items()));
                if (cmd.partialCredit() != null) q.setPartialCredit(cmd.partialCredit());
            }
            case MatchingQuestion q -> {
                if (cmd.pairs() != null) q.setPairs(mapMatchingPairs(cmd.pairs()));
                if (cmd.partialCredit() != null) q.setPartialCredit(cmd.partialCredit());
            }
            default -> {}
        }
    }
    
    private List<Media> mapMediaList(List<MediaDto> dtos) {
        if (dtos == null) return null;
        return dtos.stream()
            .map(d -> Media.create(d.id(), d.type(), d.url(), d.altText()))
            .collect(Collectors.toList());
    }
    
    private List<Option> mapOptions(List<OptionDto> dtos) {
        if (dtos == null) return null;
        return dtos.stream()
            .map(d -> Option.create(
                d.id() != null ? d.id() : UUID.randomUUID(),
                d.text(),
                d.media() != null ? Media.create(d.media().id(), d.media().type(), d.media().url(), d.media().altText()) : null,
                d.isCorrect()
            ))
            .collect(Collectors.toList());
    }
    
    private Rubric mapRubric(RubricDto dto) {
        if (dto == null) return null;
        return Rubric.create(dto.criteria().stream()
            .map(c -> new Rubric.RubricCriterion(
                c.name(), c.description(), c.maxScore(),
                c.levels().stream()
                    .map(l -> new Rubric.RubricLevel(l.name(), l.description(), l.score()))
                    .collect(Collectors.toList())
            ))
            .collect(Collectors.toList()));
    }
    
    private ScaleConfig mapScaleConfig(ScaleConfigDto dto) {
        if (dto == null) return null;
        return ScaleConfig.create(dto.minValue(), dto.maxValue(), dto.minLabel(), dto.maxLabel(), dto.labels());
    }
    
    private List<OrderingItem> mapOrderingItems(List<OrderingItemDto> dtos) {
        if (dtos == null) return null;
        return dtos.stream()
            .map(d -> OrderingItem.create(
                d.id() != null ? d.id() : UUID.randomUUID(),
                d.text(), d.correctPosition(),
                d.media() != null ? Media.create(d.media().id(), d.media().type(), d.media().url(), d.media().altText()) : null
            ))
            .collect(Collectors.toList());
    }
    
    private List<MatchingPair> mapMatchingPairs(List<MatchingPairDto> dtos) {
        if (dtos == null) return null;
        return dtos.stream()
            .map(d -> MatchingPair.create(
                d.id() != null ? d.id() : UUID.randomUUID(),
                d.leftItem(), d.rightItem(),
                d.leftMedia() != null ? Media.create(d.leftMedia().id(), d.leftMedia().type(), d.leftMedia().url(), d.leftMedia().altText()) : null,
                d.rightMedia() != null ? Media.create(d.rightMedia().id(), d.rightMedia().type(), d.rightMedia().url(), d.rightMedia().altText()) : null
            ))
            .collect(Collectors.toList());
    }
}
