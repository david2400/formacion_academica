package com.kleverkids.formacion_academica.modules.control_academico.exams.domain.model;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.Exam;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.ExamQuestion;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.ExamStatus;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.TimeConfig;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ExamTest {
    
    @Test
    void createExam_withValidData_setsDefaultStatus() {
        Exam exam = new Exam(
            null,
            "Math Final Exam",
            "MATH-101",
            "Mathematics",
            "Grade 10",
            "Answer all questions",
            null,
            TimeConfig.create(60, LocalDate.now(), LocalTime.of(9, 0), LocalTime.of(10, 0)),
            new ArrayList<>(),
            new ArrayList<>()
        );
        
        assertNotNull(exam.getId());
        assertEquals("Math Final Exam", exam.getName());
        assertEquals(ExamStatus.DRAFT, exam.getStatus());
    }
    
    @Test
    void addQuestion_toDraftExam_updatesQuestionsList() {
        Exam exam = new Exam();
        exam.setId(UUID.randomUUID());
        exam.setName("Test Exam");
        exam.setStatus(ExamStatus.DRAFT);
        exam.setTimeConfig(TimeConfig.create(30, null, null, null));
        exam.setQuestions(new ArrayList<>());
        
        ExamQuestion question = ExamQuestion.create(UUID.randomUUID(), 1, BigDecimal.TEN, true);
        exam.addQuestion(question);
        
        assertEquals(1, exam.getQuestions().size());
        assertEquals(BigDecimal.TEN, exam.getTotalPoints());
    }
    
    @Test
    void addQuestion_toActiveExam_throwsException() {
        Exam exam = new Exam();
        exam.setId(UUID.randomUUID());
        exam.setName("Test Exam");
        exam.setStatus(ExamStatus.ACTIVE);
        exam.setQuestions(new ArrayList<>());
        
        ExamQuestion question = ExamQuestion.create(UUID.randomUUID(), 1, BigDecimal.TEN, true);
        
        assertThrows(IllegalStateException.class, () -> exam.addQuestion(question));
    }
    
    @Test
    void schedule_draftExamWithQuestions_changesStatusToScheduled() {
        Exam exam = new Exam();
        exam.setId(UUID.randomUUID());
        exam.setName("Test Exam");
        exam.setStatus(ExamStatus.DRAFT);
        exam.setTimeConfig(TimeConfig.create(30, null, null, null));
        exam.setQuestions(new ArrayList<>());
        exam.addQuestion(ExamQuestion.create(UUID.randomUUID(), 1, BigDecimal.TEN, true));
        
        exam.schedule();
        
        assertEquals(ExamStatus.SCHEDULED, exam.getStatus());
    }
    
    @Test
    void schedule_draftExamWithoutQuestions_throwsException() {
        Exam exam = new Exam();
        exam.setId(UUID.randomUUID());
        exam.setName("Test Exam");
        exam.setStatus(ExamStatus.DRAFT);
        exam.setQuestions(new ArrayList<>());
        
        assertThrows(IllegalStateException.class, exam::schedule);
    }
    
    @Test
    void activate_scheduledExam_changesStatusToActive() {
        Exam exam = new Exam();
        exam.setId(UUID.randomUUID());
        exam.setName("Test Exam");
        exam.setStatus(ExamStatus.SCHEDULED);
        
        exam.activate();
        
        assertEquals(ExamStatus.ACTIVE, exam.getStatus());
    }
    
    @Test
    void complete_activeExam_changesStatusToCompleted() {
        Exam exam = new Exam();
        exam.setId(UUID.randomUUID());
        exam.setName("Test Exam");
        exam.setStatus(ExamStatus.ACTIVE);
        
        exam.complete();
        
        assertEquals(ExamStatus.COMPLETED, exam.getStatus());
    }
    
    @Test
    void validate_examWithoutName_throwsException() {
        Exam exam = new Exam();
        exam.setId(UUID.randomUUID());
        exam.setTimeConfig(TimeConfig.create(30, null, null, null));
        
        assertThrows(IllegalArgumentException.class, exam::validate);
    }
    
    @Test
    void validate_examWithoutTimeConfig_throwsException() {
        Exam exam = new Exam();
        exam.setId(UUID.randomUUID());
        exam.setName("Test Exam");
        
        assertThrows(IllegalArgumentException.class, exam::validate);
    }
}
