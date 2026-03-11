package com.fantasylol.fantasy_api.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @GetMapping("/me")
    public String getCurrentUser() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
    }
}