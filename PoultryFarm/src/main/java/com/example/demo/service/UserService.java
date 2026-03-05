package com.example.demo.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.AppUser;
import com.example.demo.repository.UserRepository;


@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder encoder;

    public AppUser register(AppUser user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole("USER");   // later: FARMER / BUYER / ADMIN
        return repo.save(user);
    }

    public Optional<AppUser> findByUsername(String username) {
        return repo.findByUsername(username);
    }
}
