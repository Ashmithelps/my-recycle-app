package com.example.backend.service;

import java.util.Optional;

import com.example.backend.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.backend.repository.UserRepository;
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;

    }
    public User registerUser(User user ) throws Exception{
        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new Exception("Username already exists");
        }
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new Exception("Email already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        return userRepository.save(user);

    }
    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }
}
