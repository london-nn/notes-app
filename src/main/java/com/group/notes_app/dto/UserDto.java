package com.group.notes_app.dto;

import lombok.*;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String token;
}
