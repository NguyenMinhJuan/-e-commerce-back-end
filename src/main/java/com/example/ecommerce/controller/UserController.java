package com.example.ecommerce.controller;

import com.example.ecommerce.model.User;
import com.example.ecommerce.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/signUp")
    public void signUp(@RequestBody User user) {
        if(userService.existsByUsername(user.getUsername())) {
            System.out.println("User already exists!");
        }else {
            userService.save(user);
        }
    }
}
