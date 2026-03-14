package com.servicehub.user.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/health")
    public String healthCheck() {
        return "User Service Running";
    }

    public String registerUser(@RequestBody Map<String, String> user) {
        // TODO: Implement user registration logic
        return "User registered";
    }

    @GetMapping("/{id}")
    public String getUserById(@PathVariable Long id) {
        // TODO: Implement get user by id logic
        return "User with id " + id;
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        // TODO: Implement delete user logic
        return "User deleted";
    }

}