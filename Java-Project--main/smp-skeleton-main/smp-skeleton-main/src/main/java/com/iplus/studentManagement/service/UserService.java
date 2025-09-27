package com.iplus.studentManagement.service;

import com.iplus.studentManagement.entity.User;
import com.iplus.studentManagement.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Handles user registration from signup.html
    public User registerUser(String username, String password, String role) {
        
        // Check if username already exists
        if (userRepository.findByUsername(username) != null) {
            throw new IllegalStateException("Username already exists.");
        }

        User user = new User();
        user.setUsername(username);
        // Hashing the password is a critical security step
        user.setPassword(passwordEncoder.encode(password)); 
        user.setRole(role);
        
        return userRepository.save(user);
    }
    
    // Used by Spring Security to load user details by username
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}