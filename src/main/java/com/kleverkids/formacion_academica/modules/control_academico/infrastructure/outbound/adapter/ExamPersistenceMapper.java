package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.*;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.EvaluationCriteria;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.ExamStatus;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.TimeConfig;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ExamPersistenceMapper {
    
    public ExamEntity toEntity(Exam exam) {
        ExamEntity entity = new ExamEntity();
        entity.setId(exam.getId());
        entity.setName(exam.getName());
        entity.setCode(exam.getCode());
        entity.setSubject(exam.getSubject());
        entity.setGradeLevel(exam.getGradeLevel());
        entity.setInstructions(exam.getInstructions());
        entity.setStatus(exam.getStatus().getValue());
        
        if (exam.getTimeConfig() != null) {
            entity.setDurationMinutes(exam.getTimeConfig().getDuration());
            entity.setScheduledDate(exam.getTimeConfig().getScheduledDate());
            entity.setStartTime(exam.getTimeConfig().getStartTime());
            entity.setEndTime(exam.getTimeConfig().getEndTime());
        }
        
        entity.setQuestions(mapQuestionsToEmbeddable(exam.getQuestions()));
        entity.setCriteria(mapCriteriaToEmbeddable(exam.getCriteria()));
        entity.setTotalPoints(exam.getTotalPoints());
        entity.setCreatedAt(exam.getCreatedAt());
        entity.setUpdatedAt(exam.getUpdatedAt());
        entity.setDeleted(exam.isDeleted());
        
        return entity;
    }
    
    public Exam toDomain(ExamEntity entity) {
        Exam exam = new Exam();
        exam.setId(entity.getId());
        exam.setName(entity.getName());
        exam.setCode(entity.getCode());
        exam.setSubject(entity.getSubject());
        exam.setGradeLevel(entity.getGradeLevel());
        exam.setInstructions(entity.getInstructions());
        exam.setStatus(ExamStatus.fromValue(entity.getStatus()));
        
        exam.setTimeConfig(TimeConfig.create(
            entity.getDurationMinutes(),
            entity.getScheduledDate(),
            entity.getStartTime(),
            entity.getEndTime()
        ));
        
        exam.setQuestions(mapQuestionsToDomain(entity.getQuestions()));
        exam.setCriteria(mapCriteriaToDomain(entity.getCriteria()));
        exam.setCreatedAt(entity.getCreatedAt());
        exam.setUpdatedAt(entity.getUpdatedAt());
        exam.setDeleted(entity.isDeleted());
        
        return exam;
    }
    
    public ExamSubmissionEntity toEntity(ExamSubmission submission) {
        ExamSubmissionEntity entity = new ExamSubmissionEntity();
        entity.setId(submission.getId());
        entity.setExamId(submission.getExamId());
        entity.setStudentId(submission.getStudentId());
        entity.setStartedAt(submission.getStartedAt());
        entity.setSubmittedAt(submission.getSubmittedAt());
        entity.setAnswers(mapAnswersToEmbeddable(submission.getAnswers()));
        entity.setStatus(submission.getStatus().name());
        entity.setTotalScore(submission.getTotalScore());
        entity.setGraded(submission.isGraded());
        return entity;
    }
    
    public ExamSubmission toDomain(ExamSubmissionEntity entity) {
        ExamSubmission submission = new ExamSubmission();
        submission.setId(entity.getId());
        submission.setExamId(entity.getExamId());
        submission.setStudentId(entity.getStudentId());
        submission.setStartedAt(entity.getStartedAt());
        submission.setSubmittedAt(entity.getSubmittedAt());
        submission.setAnswers(mapAnswersToDomain(entity.getAnswers()));
        submission.setStatus(ExamSubmission.SubmissionStatus.valueOf(entity.getStatus()));
        submission.setTotalScore(entity.getTotalScore());
        submission.setGraded(entity.isGraded());
        return submission;
    }
    
    public ExamResultEntity toEntity(ExamResult result) {
        ExamResultEntity entity = new ExamResultEntity();
        entity.setId(result.getId());
        entity.setExamId(result.getExamId());
        entity.setStudentId(result.getStudentId());
        entity.setSubmissionId(result.getSubmissionId());
        entity.setTotalScore(result.getTotalScore());
        entity.setMaxScore(result.getMaxScore());
        entity.setPercentage(result.getPercentage());
        entity.setGrade(result.getGrade());
        entity.setQuestionResults(mapQuestionResultsToEmbeddable(result.getQuestionResults()));
        entity.setGradedAt(result.getGradedAt());
        entity.setGradedBy(result.getGradedBy());
        return entity;
    }
    
    public ExamResult toDomain(ExamResultEntity entity) {
        ExamResult result = new ExamResult();
        result.setId(entity.getId());
        result.setExamId(entity.getExamId());
        result.setStudentId(entity.getStudentId());
        result.setSubmissionId(entity.getSubmissionId());
        result.setTotalScore(entity.getTotalScore());
        result.setMaxScore(entity.getMaxScore());
        result.setGrade(entity.getGrade());
        result.setQuestionResults(mapQuestionResultsToDomain(entity.getQuestionResults()));
        result.setGradedAt(entity.getGradedAt());
        result.setGradedBy(entity.getGradedBy());
        return result;
    }
    
    private List<ExamQuestionEmbeddable> mapQuestionsToEmbeddable(List<ExamQuestion> questions) {
        if (questions == null) return null;
        return questions.stream()
            .map(q -> new ExamQuestionEmbeddable(q.getId(), q.getQuestionId(), q.getOrder(), q.getPoints(), q.isRequired()))
            .collect(Collectors.toList());
    }
    
    private List<ExamQuestion> mapQuestionsToDomain(List<ExamQuestionEmbeddable> questions) {
        if (questions == null) return null;
        return questions.stream()
            .map(q -> new ExamQuestion(q.getId(), q.getQuestionId(), q.getOrder(), q.getPoints(), q.isRequired()))
            .collect(Collectors.toList());
    }
    
    private List<EvaluationCriteriaEmbeddable> mapCriteriaToEmbeddable(List<EvaluationCriteria> criteria) {
        if (criteria == null) return null;
        return criteria.stream()
            .map(c -> new EvaluationCriteriaEmbeddable(c.getId(), c.getName(), c.getDescription(), c.getWeight(), c.getMaxScore()))
            .collect(Collectors.toList());
    }
    
    private List<EvaluationCriteria> mapCriteriaToDomain(List<EvaluationCriteriaEmbeddable> criteria) {
        if (criteria == null) return null;
        return criteria.stream()
            .map(c -> EvaluationCriteria.create(c.getId(), c.getName(), c.getDescription(), c.getWeight(), c.getMaxScore()))
            .collect(Collectors.toList());
    }
    
    private List<QuestionAnswerEmbeddable> mapAnswersToEmbeddable(List<QuestionAnswer> answers) {
        if (answers == null) return null;
        return answers.stream()
            .map(a -> new QuestionAnswerEmbeddable(a.getId(), a.getQuestionId(), a.getAnswer(), a.getScore(), a.isGraded(), a.getFeedback()))
            .collect(Collectors.toList());
    }
    
    private List<QuestionAnswer> mapAnswersToDomain(List<QuestionAnswerEmbeddable> answers) {
        if (answers == null) return null;
        return answers.stream()
            .map(a -> {
                QuestionAnswer qa = new QuestionAnswer(a.getId(), a.getQuestionId(), a.getAnswer());
                qa.setScore(a.getScore());
                qa.setGraded(a.isGraded());
                qa.setFeedback(a.getFeedback());
                return qa;
            })
            .collect(Collectors.toList());
    }
    
    private List<QuestionResultEmbeddable> mapQuestionResultsToEmbeddable(List<ExamResult.QuestionResult> results) {
        if (results == null) return null;
        return results.stream()
            .map(r -> new QuestionResultEmbeddable(r.getQuestionId(), r.getScore(), r.getMaxScore(), r.isCorrect(), r.getFeedback()))
            .collect(Collectors.toList());
    }
    
    private List<ExamResult.QuestionResult> mapQuestionResultsToDomain(List<QuestionResultEmbeddable> results) {
        if (results == null) return null;
        return results.stream()
            .map(r -> new ExamResult.QuestionResult(r.getQuestionId(), r.getScore(), r.getMaxScore(), r.isCorrect(), r.getFeedback()))
            .collect(Collectors.toList());
    }
}
