package com.fantasylol.fantasy_api.service;

import com.fantasylol.fantasy_api.dto.LoginRequest;
import com.fantasylol.fantasy_api.dto.RegisterRequest;
import com.fantasylol.fantasy_api.exception.ResourceNotFoundException;
import com.fantasylol.fantasy_api.model.Role;
import com.fantasylol.fantasy_api.model.Usuario;
import com.fantasylol.fantasy_api.repository.UsuarioRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UsuarioRepository usuarioRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    // =====================================
    // REGISTER
    // =====================================
    public Usuario register(RegisterRequest request) {

        if (usuarioRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("El username ya está en uso");
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(request.getUsername());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setRole(Role.ROLE_USER); // 🔐 Asignamos rol por defecto

        return usuarioRepository.save(usuario);
    }

    // =====================================
    // LOGIN
    // =====================================
    public String login(LoginRequest request) {

        Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new IllegalArgumentException("Credenciales inválidas");
        }

        // 🔐 Ahora enviamos también el rol al JWT
        return jwtService.generateToken(
                usuario.getUsername(),
                usuario.getRole().name()
        );
    }
}