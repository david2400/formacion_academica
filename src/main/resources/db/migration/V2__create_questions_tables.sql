-- Questions module tables
-- V2__create_questions_tables.sql

CREATE TABLE questions (
    id UUID PRIMARY KEY,
    question_type VARCHAR(50) NOT NULL,
    question_text TEXT NOT NULL,
    difficulty VARCHAR(20) NOT NULL,
    max_score INTEGER NOT NULL DEFAULT 1,
    theme_id UUID,
    hint TEXT,
    explanation TEXT,
    tags JSON,
    metadata JSON,
    media JSON,
    
    -- Multiple Choice Single fields
    options JSON,
    correct_option_id UUID,
    
    -- Multiple Choice Multi fields
    correct_option_ids JSON,
    min_selections INTEGER,
    max_selections INTEGER,
    
    -- True/False fields
    correct_answer BOOLEAN,
    
    -- Open Short fields
    accepted_answers JSON,
    case_sensitive BOOLEAN DEFAULT FALSE,
    max_length INTEGER,
    
    -- Open Long fields
    rubric JSON,
    min_words INTEGER,
    max_words INTEGER,
    allow_attachments BOOLEAN DEFAULT FALSE,
    
    -- Numeric fields
    correct_value DECIMAL(20, 10),
    tolerance DECIMAL(20, 10),
    unit VARCHAR(50),
    decimal_places INTEGER,
    
    -- Scale fields
    scale_config JSON,
    expected_value INTEGER,
    
    -- Ordering fields
    items JSON,
    partial_credit BOOLEAN DEFAULT FALSE,
    
    -- Matching fields
    pairs JSON,
    
    -- Auditing fields
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE,
    created_by UUID,
    updated_by UUID,
    deleted BOOLEAN DEFAULT FALSE,
    version BIGINT DEFAULT 0
);

CREATE INDEX idx_questions_type ON questions(question_type);
CREATE INDEX idx_questions_difficulty ON questions(difficulty);
CREATE INDEX idx_questions_theme_id ON questions(theme_id);
CREATE INDEX idx_questions_deleted ON questions(deleted);
CREATE INDEX idx_questions_created_at ON questions(created_at);
