package com.example.MonoStyle.Security;
import com.example.MonoStyle.DTOS.JwtAuthResponseDTO;
import com.example.MonoStyle.DTOS.LoginDTO;
import com.example.MonoStyle.DTOS.RegistroClienteDTO;
import com.example.MonoStyle.Entity.Cliente;
import com.example.MonoStyle.Entity.Rol;
import com.example.MonoStyle.Repository.ClienteRepository;
import com.example.MonoStyle.Repository.RolRepository;
import io.jsonwebtoken.Jwt;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponseDTO> authenticateUser(@RequestBody LoginDTO loginDTO) {

        // 1. Spring Security usa el CustomUserDetailsService para buscar en las 3 tablas y validar la clave
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getNombre(), loginDTO.getCredenciales())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 2. Extraer dinámicamente el rol que Spring le asignó (ADMIN, EMPLEADO o CLIENTE)
        String rolDetectado = authentication.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");

        // 3. Generar el token inyectando ese rol exacto
        String token = jwtTokenProvider.generarToken(authentication.getName(), rolDetectado);

        return ResponseEntity.ok(new JwtAuthResponseDTO(token, "Bearer"));
    }
}
