package com.group.notes_app.exceptions;

import com.group.notes_app.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class GenericConstructor {

    public <T> ErrorResponse<T> construct(String errorName , T details, HttpStatus status, HttpServletRequest request) {
        String methodAndPath = request.getMethod() + " " + request.getRequestURI();
        return new ErrorResponse<>(
                errorName,
                LocalDateTime.now(),
                methodAndPath,
                status.value(),
                details
        );
    }

}
