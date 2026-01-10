package com.example.digitallibrary.controller;

import com.example.digitallibrary.dto.RegisterRequest;
import com.example.digitallibrary.dto.RegisterResponse;
import com.example.digitallibrary.dto.LoginResponse;
import com.example.digitallibrary.dto.LoginRequest;
import com.example.digitallibrary.entity.Role;
import com.example.digitallibrary.entity.User;
import com.example.digitallibrary.service.UserService;
import com.example.digitallibrary.security.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        userService.registerUser(
                request.getEmail(),
                request.getPassword(),
                Role.USER
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new RegisterResponse("User registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request
    ) {
        User user = userService.authenticateUser(
                request.getEmail(),
                request.getPassword()
        );

        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole().name()
        );

        return ResponseEntity.ok(
                new LoginResponse(token)
        );
    }
}
