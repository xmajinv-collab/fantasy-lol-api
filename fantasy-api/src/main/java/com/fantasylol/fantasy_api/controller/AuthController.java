package com.fantasylol.fantasy_api.controller;

import com.fantasylol.fantasy_api.dto.ApiResponse;
import com.fantasylol.fantasy_api.dto.LoginRequest;
import com.fantasylol.fantasy_api.dto.RegisterRequest;
import com.fantasylol.fantasy_api.model.Usuario;
import com.fantasylol.fantasy_api.service.AuthService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ApiResponse<Usuario> register(@Valid @RequestBody RegisterRequest request) {
        Usuario usuario = authService.register(request);
        return ApiResponse.success("Usuario registrado correctamente", usuario);
    }

    @PostMapping("/login")
    public ApiResponse<String> login(@Valid @RequestBody LoginRequest request) {
        String token = authService.login(request);
        return ApiResponse.success("Login correcto", token);
    }
}