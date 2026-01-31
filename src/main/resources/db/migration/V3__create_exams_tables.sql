-- Exams module tables
-- V3__create_exams_tables.sql

CREATE TABLE exams (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    code VARCHAR(100) UNIQUE,
    subject VARCHAR(100),
    grade_level VARCHAR(50),
    instructions TEXT,
    status VARCHAR(20) NOT NULL DEFAULT 'draft',
    duration_minutes INTEGER NOT NULL,
    scheduled_date DATE,
    start_time TIME,
    end_time TIME,
    questions JSON,
    criteria JSON,
    total_points DECIMAL(10, 2),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE,
    deleted BOOLEAN DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

CREATE INDEX idx_exams_status ON exams(status);
CREATE INDEX idx_exams_subject ON exams(subject);
CREATE INDEX idx_exams_grade_level ON exams(grade_level);
CREATE INDEX idx_exams_scheduled_date ON exams(scheduled_date);
CREATE INDEX idx_exams_deleted ON exams(deleted);

CREATE TABLE exam_submissions (
    id UUID PRIMARY KEY,
    exam_id UUID NOT NULL REFERENCES exams(id),
    student_id UUID NOT NULL,
    started_at TIMESTAMP WITH TIME ZONE,
    submitted_at TIMESTAMP WITH TIME ZONE,
    answers JSON,
    status VARCHAR(20) NOT NULL DEFAULT 'IN_PROGRESS',
    total_score DECIMAL(10, 2),
    graded BOOLEAN DEFAULT FALSE
);

CREATE INDEX idx_exam_submissions_exam_id ON exam_submissions(exam_id);
CREATE INDEX idx_exam_submissions_student_id ON exam_submissions(student_id);
CREATE INDEX idx_exam_submissions_status ON exam_submissions(status);
CREATE UNIQUE INDEX idx_exam_submissions_exam_student ON exam_submissions(exam_id, student_id);

CREATE TABLE exam_results (
    id UUID PRIMARY KEY,
    exam_id UUID NOT NULL REFERENCES exams(id),
    student_id UUID NOT NULL,
    submission_id UUID NOT NULL REFERENCES exam_submissions(id),
    total_score DECIMAL(10, 2),
    max_score DECIMAL(10, 2),
    percentage DECIMAL(5, 2),
    grade VARCHAR(10),
    question_results JSON,
    graded_at TIMESTAMP WITH TIME ZONE,
    graded_by UUID
);

CREATE INDEX idx_exam_results_exam_id ON exam_results(exam_id);
CREATE INDEX idx_exam_results_student_id ON exam_results(student_id);
CREATE UNIQUE INDEX idx_exam_results_exam_student ON exam_results(exam_id, student_id);
