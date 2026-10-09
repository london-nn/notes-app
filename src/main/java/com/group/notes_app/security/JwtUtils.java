package com.group.notes_app.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {

    private String jwtSecret = "secret";
    private int expirationMs = 360000;

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .setIssuedAt(new Date())
                .expiration(new Date(new Date().getTime() + expirationMs))
                .signWith(secretKey)
                .compact();
    }
}
