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

    private final String jwtSecret = "MonoStyle2026!TallerMecanicoSeguridadSuperAvanzadaJWT-Secreto12345";

    private final long jwtExpirationDate = 86400000;

    private Key key() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

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

    public String obtenerUsernameDelToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validarToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("Token JWT inválido o expirado: " + e.getMessage());
        }
        return false;
    }

    public Authentication obtenerAutenticacion(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody();
        String rol = claims.get("rol", String.class);

        User principal = new User(claims.getSubject(), "", Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + rol)));
        return new UsernamePasswordAuthenticationToken(principal, token, principal.getAuthorities());
    }
}