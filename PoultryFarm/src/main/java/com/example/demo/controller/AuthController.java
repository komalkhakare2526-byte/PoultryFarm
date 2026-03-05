package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.AppUser;
import com.example.demo.service.UserService;

import ch.qos.logback.core.model.Model;
import jakarta.validation.Valid;


@Controller
public class AuthController {

    @Autowired
    private UserService service;

    @PostMapping("/saveUser")
    public String registerUser(
            @Valid @ModelAttribute("user") AppUser user,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            return "register";
        }

        service.register(user);
        return "redirect:/login?success";
    }
    
}
