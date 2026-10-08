package com.group.notes_app.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
public class UserLoginRequest {

    @NotNull
    private String username;
    @NotNull
    private String password;
}
