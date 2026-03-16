package com.fantasylol.fantasy_api.controller;

import com.fantasylol.fantasy_api.model.Usuario;
import com.fantasylol.fantasy_api.repository.UsuarioRepository;
import com.fantasylol.fantasy_api.service.JwtService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    // ======================
    // REGISTER
    // ======================

    @PostMapping("/register")
    public Map<String, String> register(@RequestBody Usuario usuario) {

        if (usuarioRepository.findByUsername(usuario.getUsername()).isPresent()) {
            throw new RuntimeException("El usuario ya existe");
        }

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        usuarioRepository.save(usuario);

        return Map.of("message", "Usuario registrado correctamente");
    }

    // ======================
    // LOGIN
    // ======================

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Usuario request) {

        Usuario usuario = usuarioRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Credenciales incorrectas");
        }

        String token = jwtService.generateToken(usuario.getUsername());

        return Map.of("token", token);
    }
}