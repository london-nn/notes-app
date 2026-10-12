package com.group.notes_app.controller;

import com.group.notes_app.dto.UserDto;
import com.group.notes_app.dto.UserLoginRequest;
import com.group.notes_app.dto.UserRegistrationRequest;
import com.group.notes_app.security.JwtUtils;
import com.group.notes_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/registration")
    public ResponseEntity<UserDto> registration(@RequestBody UserRegistrationRequest userRegistrationRequest) {
        UserDto userDto = userService.createUser(userRegistrationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequest userLoginRequest) {
        String jwtToken = userService.loginUser(userLoginRequest);
        Map<String,String> map = new HashMap<>();
        map.put("token", jwtToken);
        return ResponseEntity.status(HttpStatus.OK).body(jwtToken);
    }
}
