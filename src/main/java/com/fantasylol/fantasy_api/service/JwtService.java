package com.fantasylol.fantasy_api.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

import java.util.Date;

@Service
public class JwtService {

    private final SecretKey key;

    private final long expiration = 1000 * 60 * 60; // 1 hora

    public JwtService(@Value("${jwt.secret}") String secret) {

        this.key = Keys.hmacShaKeyFor(secret.getBytes());

    }

    // =============================
    // GENERAR TOKEN
    // =============================

    public String generateToken(String username) {

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // =============================
    // EXTRAER USERNAME
    // =============================

    public String extractUsername(String token) {

        return extractAllClaims(token).getSubject();

    }

    // =============================
    // EXTRAER CLAIMS
    // =============================

    private Claims extractAllClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

    }

    // =============================
    // VALIDAR TOKEN
    // =============================

    public boolean isTokenValid(String jwt, UserDetails userDetails) {

        final String username = extractUsername(jwt);

        return username.equals(userDetails.getUsername()) && !isTokenExpired(jwt);

    }

    // =============================
    // TOKEN EXPIRADO
    // =============================

    private boolean isTokenExpired(String token) {

        return extractAllClaims(token)
                .getExpiration()
                .before(new Date());

    }

}