package com.group.notes_app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
public class UserRegistrationRequest {
    @NotBlank
    private String username;

    @NotBlank
    @Size(min = 6, max = 20)
    private String password;

    @NotBlank
    @Email
    private String email;
}
