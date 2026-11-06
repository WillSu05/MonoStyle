package com.example.MonoStyle.Security;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Obtener el token de la solicitud HTTP
        String token = obtenerTokenDeLaSolicitud(request);

        // 2. Validar el token
        if (StringUtils.hasText(token) && jwtTokenProvider.validarToken(token)) {
            // 3. Obtener los datos del usuario y establecer la seguridad en el contexto
            Authentication authentication = jwtTokenProvider.obtenerAutenticacion(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 4. Continuar con la cadena de filtros
        filterChain.doFilter(request, response);
    }

    // Extrae el token quitando la palabra "Bearer "
    private String obtenerTokenDeLaSolicitud(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
