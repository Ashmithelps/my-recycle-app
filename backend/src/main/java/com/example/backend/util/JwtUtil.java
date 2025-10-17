package com.example.backend.util;


import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    private final Key SECRET_KEY = Keys.hmacShaKeyFor("MySuperLongSecretKeyForJWTGeneration_1234567890_0987654321".getBytes());
    private final long EXPIRATION=1000 * 60 * 60 * 10;

    public String generateToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+ EXPIRATION))
                .signWith(SECRET_KEY,SignatureAlgorithm.HS256)
                .compact();
    }
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
