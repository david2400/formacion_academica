package com.kleverkids.formacion_academica.config;

import com.kleverkids.formacion_academica.modules.control_academico.domain.exception.ExamNotFoundException;
import com.kleverkids.formacion_academica.modules.control_academico.domain.exception.QuestionNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    @ExceptionHandler(QuestionNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleQuestionNotFound(QuestionNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
            "timestamp", Instant.now(),
            "status", 404,
            "error", "No Encontrado",
            "message", ex.getMessage()
        ));
    }
    
    @ExceptionHandler(ExamNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleExamNotFound(ExamNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
            "timestamp", Instant.now(),
            "status", 404,
            "error", "No Encontrado",
            "message", ex.getMessage()
        ));
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
            "timestamp", Instant.now(),
            "status", 400,
            "error", "Solicitud Inválida",
            "message", ex.getMessage()
        ));
    }
    
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalState(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
            "timestamp", Instant.now(),
            "status", 409,
            "error", "Conflicto",
            "message", ex.getMessage()
        ));
    }
    
    @ExceptionHandler({NoResourceFoundException.class, NoHandlerFoundException.class})
    public ResponseEntity<Map<String, Object>> handleNotFound(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
            "timestamp", Instant.now(),
            "status", 404,
            "error", "No Encontrado",
            "message", ex.getMessage()
        ));
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        log.error("Unhandled exception", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
            "timestamp", Instant.now(),
            "status", 500,
            "error", "Error Interno del Servidor",
            "message", ex.getMessage()
        ));
    }
}
