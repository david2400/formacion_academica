package com.kleverkids.formacion_academica.config;

import com.kleverkids.formacion_academica.modules.control_academico.domain.exception.ExamNotFoundException;
import com.kleverkids.formacion_academica.modules.control_academico.domain.exception.QuestionNotFoundException;
import com.kleverkids.formacion_academica.shared.exceptions.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(QuestionNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleQuestionNotFound(QuestionNotFoundException ex, HttpServletRequest request) {
        return notFound(ex, request);
    }

    @ExceptionHandler(ExamNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleExamNotFound(ExamNotFoundException ex, HttpServletRequest request) {
        return notFound(ex, request);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ProblemDetail> handleNotFound(NotFoundException ex, HttpServletRequest request) {
        return notFound(ex, request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ProblemDetail> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {
        return badRequest(ex, request);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ProblemDetail> handleIllegalState(IllegalStateException ex, HttpServletRequest request) {
        return conflict(ex, request);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ProblemDetail> handleArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        ProblemDetail pd = baseProblemDetail(HttpStatus.BAD_REQUEST, "Solicitud Inválida", ex.getMessage(), request);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(pd);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<Map<String, Object>> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(this::toFieldError)
                .toList();

        ProblemDetail pd = baseProblemDetail(HttpStatus.BAD_REQUEST, "Solicitud Inválida", "La solicitud contiene errores de validación", request);
        pd.setProperty("fieldErrors", fieldErrors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(pd);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ProblemDetail> handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest request) {
        ProblemDetail pd = baseProblemDetail(HttpStatus.BAD_REQUEST, "Solicitud Inválida", ex.getMessage(), request);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(pd);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ProblemDetail> handleDataIntegrityViolation(DataIntegrityViolationException ex, HttpServletRequest request) {
        ProblemDetail pd = baseProblemDetail(HttpStatus.CONFLICT, "Conflicto", "Violación de integridad de datos", request);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(pd);
    }

    @ExceptionHandler({NoResourceFoundException.class, NoHandlerFoundException.class})
    public ResponseEntity<ProblemDetail> handleSpringNotFound(Exception ex, HttpServletRequest request) {
        return notFound(ex, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleGenericException(Exception ex, HttpServletRequest request) {
        log.error("Unhandled exception", ex);
        ProblemDetail pd = baseProblemDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Error Interno del Servidor", "Ha ocurrido un error inesperado", request);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(pd);
    }

    private ResponseEntity<ProblemDetail> notFound(Exception ex, HttpServletRequest request) {
        ProblemDetail pd = baseProblemDetail(HttpStatus.NOT_FOUND, "No Encontrado", ex.getMessage(), request);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(pd);
    }

    private ResponseEntity<ProblemDetail> badRequest(Exception ex, HttpServletRequest request) {
        ProblemDetail pd = baseProblemDetail(HttpStatus.BAD_REQUEST, "Solicitud Inválida", ex.getMessage(), request);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(pd);
    }

    private ResponseEntity<ProblemDetail> conflict(Exception ex, HttpServletRequest request) {
        ProblemDetail pd = baseProblemDetail(HttpStatus.CONFLICT, "Conflicto", ex.getMessage(), request);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(pd);
    }

    private ProblemDetail baseProblemDetail(HttpStatus status, String title, String detail, HttpServletRequest request) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(status, detail);
        pd.setTitle(title);

        pd.setProperty("timestamp", Instant.now());
        if (request != null) {
            pd.setInstance(java.net.URI.create(request.getRequestURI()));
            pd.setProperty("path", request.getRequestURI());
            String requestId = request.getHeader("X-Request-Id");
            if (requestId == null || requestId.isBlank()) {
                Object attr = request.getAttribute("requestId");
                if (attr instanceof String s) {
                    requestId = s;
                }
            }
            if (requestId == null || requestId.isBlank()) {
                requestId = MDC.get("requestId");
            }
            if (requestId != null && !requestId.isBlank()) {
                pd.setProperty("requestId", requestId);
            }
        }
        return pd;
    }

    private Map<String, Object> toFieldError(FieldError fe) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("field", fe.getField());
        m.put("rejectedValue", fe.getRejectedValue());
        m.put("message", fe.getDefaultMessage());
        return m;
    }
}
