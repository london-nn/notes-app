package com.group.notes_app.exceptions;

import com.group.notes_app.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    GenericConstructor genericConstructor;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse<Map<String,String>> handle(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        ErrorResponse<Map<String,String>> errorResponse =  genericConstructor.construct(
                ex.getClass().getName(), errors,HttpStatus.BAD_REQUEST, request);
        return errorResponse;
    }

    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse<String> handleTaskNotFound(TaskNotFoundException ex, HttpServletRequest request) {
        ErrorResponse<String> errorResponse =  genericConstructor.construct(
                ex.getClass().getName(), ex.getMessage(),HttpStatus.NOT_FOUND, request);
        return errorResponse;
    }

    @ExceptionHandler(TaskListIsEmpty.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse<String> handleTaskListIsEmpty(TaskListIsEmpty ex, HttpServletRequest request) {
        ErrorResponse<String> errorResponse =  genericConstructor.construct(
                ex.getClass().getName(), ex.getMessage(),HttpStatus.BAD_REQUEST, request);
        return errorResponse;
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse<String> handleUsernameAlreadyExists(UsernameAlreadyExistsException ex, HttpServletRequest request) {
        ErrorResponse<String> errorResponse =  genericConstructor.construct(
                ex.getClass().getName(), ex.getMessage(),HttpStatus.BAD_REQUEST, request);
        return errorResponse;
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse<String> handleUsernameNotFound(UsernameAlreadyExistsException ex, HttpServletRequest request) {
        ErrorResponse<String> errorResponse =  genericConstructor.construct(
                ex.getClass().getName(), ex.getMessage(),HttpStatus.NOT_FOUND, request);
        return errorResponse;
    }

}
