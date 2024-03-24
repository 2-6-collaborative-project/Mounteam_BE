package com.example.mountain.domain.user.controller;

import com.example.mountain.domain.user.service.UserService;
import com.example.mountain.global.dto.GlobalResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private UserService userService;

    @GetMapping("/users/me")
    public GlobalResponse getMyProfile(@RequestHeader("Authorization") String token) {

        return null;
    }
}
