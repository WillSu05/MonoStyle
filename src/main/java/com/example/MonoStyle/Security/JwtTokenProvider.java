package com.example.MonoStyle.Security;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collections;
import java.util.Date;

@Component
public class JwtTokenProvider {

    // Se asigna la clave directamente en el código para evitar problemas con application.properties.
    // Esta clave tiene más de 32 caracteres (256 bits), cumpliendo con RFC 7518.
    private final String jwtSecret = "MonoStyle2026!TallerMecanicoSeguridadSuperAvanzadaJWT-Secreto12345";

    // Tiempo de expiración definido directamente (ej. 24 horas en milisegundos)
    private final long jwtExpirationDate = 86400000;

    // Genera la llave encriptada a partir de tu secreto
    private Key key() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    // Generar el token cuando el usuario inicia sesión
    public String generarToken(String username, String rol) {
        Date fechaActual = new Date();
        Date fechaExpiracion = new Date(fechaActual.getTime() + jwtExpirationDate);

        return Jwts.builder()
                .setSubject(username)
                .claim("rol", rol)
                .setIssuedAt(fechaActual)
                .setExpiration(fechaExpiracion)
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Extraer el usuario (subject) del token
    public String obtenerUsernameDelToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Validar que el token no esté modificado ni expirado
    public boolean validarToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("Token JWT inválido o expirado: " + e.getMessage());
        }
        return false;
    }

    // Generar la autenticación de Spring Security a partir del token
    public Authentication obtenerAutenticacion(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody();
        String rol = claims.get("rol", String.class);

        // Se crea un usuario de Spring Security en memoria solo para el contexto de la petición
        User principal = new User(claims.getSubject(), "", Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + rol)));
        return new UsernamePasswordAuthenticationToken(principal, token, principal.getAuthorities());
    }
}