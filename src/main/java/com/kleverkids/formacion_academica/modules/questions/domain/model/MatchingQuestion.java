package com.kleverkids.formacion_academica.modules.questions.domain.model;

import com.kleverkids.formacion_academica.modules.questions.domain.exception.InvalidQuestionException;
import com.kleverkids.formacion_academica.modules.questions.domain.valueobject.Difficulty;
import com.kleverkids.formacion_academica.modules.questions.domain.valueobject.MatchingPair;
import com.kleverkids.formacion_academica.modules.questions.domain.valueobject.Media;
import com.kleverkids.formacion_academica.modules.questions.domain.valueobject.QuestionType;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MatchingQuestion extends Question {
    
    private List<MatchingPair> pairs;
    private boolean partialCredit;
    
    public MatchingQuestion() {
        super();
        this.questionType = QuestionType.MATCHING;
    }
    
    public MatchingQuestion(UUID id, String questionText, Difficulty difficulty, int maxScore,
                             UUID themeId, List<Media> media, String hint, String explanation,
                             List<String> tags, Map<String, Object> metadata,
                             List<MatchingPair> pairs, boolean partialCredit) {
        super(id, questionText, QuestionType.MATCHING, difficulty, maxScore,
              themeId, media, hint, explanation, tags, metadata);
        this.pairs = pairs;
        this.partialCredit = partialCredit;
        validate();
    }
    
    public List<MatchingPair> getPairs() {
        return pairs;
    }
    
    public void setPairs(List<MatchingPair> pairs) {
        this.pairs = pairs;
    }
    
    public boolean isPartialCredit() {
        return partialCredit;
    }
    
    public void setPartialCredit(boolean partialCredit) {
        this.partialCredit = partialCredit;
    }
    
    @Override
    public void validate() {
        if (pairs == null || pairs.size() < 2) {
            throw new InvalidQuestionException("Matching question must have at least 2 pairs");
        }
    }
}
