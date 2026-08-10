package com.group.notes_app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@AllArgsConstructor
public class ValidationErrorResponse {
    private String error;
    private LocalDateTime timestamp;
    private String methodAndPath;
    private int status;
    private Map<String, String> details;
}
