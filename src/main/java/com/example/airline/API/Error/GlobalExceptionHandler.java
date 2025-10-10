package com.example.airline.API.Error;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class) //@ExceptionHandler se encarga de tomar la excepcion indicada como argumento y ejecutar la funcion que tiene asignada
    public ResponseEntity<ApiError>  handleEntityNotFoundException(EntityNotFoundException e, WebRequest req) {
        var body = ApiError.of(HttpStatus.NOT_FOUND, e.getMessage(), req.getDescription(false), List.of());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) // MethodArgumentNotValidException esta es una excepcion que ocurre cuando se pasa un parametro que se ha anotado como debe ser el parametro (Validation)
    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, WebRequest req) {
        List<ApiError.fieldViolation> violations = e.getBindingResult().getFieldErrors().stream().map(fe -> new ApiError.fieldViolation(
                fe.getField(), fe.getDefaultMessage()
        )).toList();
        var body = ApiError.of(HttpStatus.BAD_REQUEST, e.getMessage(), req.getDescription(false), violations);
        return ResponseEntity.badRequest().body(body);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, WebRequest req) {
        var violations = ex.getBindingResult().getFieldErrors()
                .stream().map(fe -> new ApiError.fieldViolation(fe.getField(), fe.getDefaultMessage())).toList();
        var body = ApiError.of(HttpStatus.BAD_REQUEST, "Validation failed", req.getDescription(false), violations);
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraint(ConstraintViolationException ex, WebRequest req) {
        var violations = ex.getConstraintViolations().stream()
                .map(cv -> new ApiError.fieldViolation(cv.getPropertyPath().toString(), cv.getMessage()))
                .toList();
        var body = ApiError.of(HttpStatus.BAD_REQUEST, "Constraint violation", req.getDescription(false), violations);
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArg(IllegalArgumentException ex, WebRequest req) {
        var body = ApiError.of(HttpStatus.BAD_REQUEST, ex.getMessage(), req.getDescription(false), List.of());
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiError> handleIllegalState(IllegalStateException ex, WebRequest req) {
        var body = ApiError.of(HttpStatus.CONFLICT, ex.getMessage(), req.getDescription(false), List.of());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex, WebRequest req) {
        var body = ApiError.of(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error", req.getDescription(false), List.of());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

}
