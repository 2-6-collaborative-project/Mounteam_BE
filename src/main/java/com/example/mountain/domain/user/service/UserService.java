package com.example.mountain.domain.user.service;

import com.example.mountain.domain.user.entity.User;
import com.example.mountain.domain.user.repository.UserRepository;
import com.example.mountain.oauth.jwt.JwtTokenProvider;
import com.example.mountain.oauth.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final JwtTokenProvider jwtTokenProvider;

    public User getUserFromToken(String token) {
        if (token != null) {
            String userIdString = jwtTokenProvider.extractSubject(token);
            Long userId = Long.parseLong(userIdString);
            return userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
        } else {
            throw new RuntimeException("Token not found");
        }
    }
}
