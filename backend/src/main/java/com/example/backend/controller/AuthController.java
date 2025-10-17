package com.example.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.backend.model.User;
import com.example.backend.service.UserService;
import com.example.backend.util.JwtUtil;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    
    public AuthController (UserService userService, JwtUtil jwtUtil){
        this.userService=userService;
        this.jwtUtil=jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?>register(@RequestBody User user){
        try{
            User savedUser=userService.registerUser(user);
            String token=jwtUtil.generateToken(savedUser.getUsername());
            return ResponseEntity.ok().body("Jwt token " + token);
        } catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/Login")
    public ResponseEntity<?> login(@RequestBody User user){
        return userService.findByUsername(user.getUsername())
                .filter(u ->jwtUtil.getPasswordEncoder().matches(user.getPassword(), u.getPassword()))
                .map(u-> ResponseEntity.ok().body("Jwt token " + jwtUtil.generateToken(u.getUsername())))
                .orElse(ResponseEntity.status(401).body("invalid username or password"));
    }

}
