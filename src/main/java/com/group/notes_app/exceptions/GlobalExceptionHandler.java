package com.group.notes_app.exceptions;

import com.group.notes_app.dto.ValidationErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse handle(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        String methodAndPath = request.getMethod() + " " + request.getRequestURI();
        return new ValidationErrorResponse(
          "Validation error",
                LocalDateTime.now(),
                methodAndPath,
                HttpStatus.BAD_REQUEST.value(),
                errors
        );
    }

    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ValidationErrorResponse handleTaskNotFound(TaskNotFoundException ex, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        errors.put("message", ex.getMessage());
        String methodAndPath = request.getMethod() + " " + request.getRequestURI();
        return new ValidationErrorResponse(
                "Task not found",
                LocalDateTime.now(),
                methodAndPath,
                HttpStatus.NOT_FOUND.value(),
                errors
        );
    }

    @ExceptionHandler(TaskListIsEmpty.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse handleTaskListIsEmpty(TaskListIsEmpty ex, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        errors.put("message", ex.getMessage());
        String methodAndPath = request.getMethod() + " " + request.getRequestURI();
        return new ValidationErrorResponse(
          "Task list is empty",
          LocalDateTime.now(),
          methodAndPath,
          HttpStatus.BAD_REQUEST.value(),
          errors
        );
    }

}
