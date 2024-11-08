package com.example.splitit.service;
// UserService.java


import com.example.splitit.model.User;
import com.example.splitit.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public User registerUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findByUserID(Long ID) {
        return userRepository.findById(ID);
    }


    public boolean loginUser(String email, String password) {
        // Find user by email
        User user = userRepository.findByEmail(email).orElse(null);

        // Check if user exists and the password matches
        if (user != null && user.getPassword().equals(password)) {
            return true; // Login successful
        }
        return false; // Login failed
    }
}
