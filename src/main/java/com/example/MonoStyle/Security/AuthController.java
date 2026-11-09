package com.example.MonoStyle.Security;
import com.example.MonoStyle.DTOS.JwtAuthResponseDTO;
import com.example.MonoStyle.DTOS.LoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponseDTO> authenticateUser(@RequestBody LoginDTO loginDTO) {

        // 1. Validar credenciales con Spring Security
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getNombre(), loginDTO.getCredenciales())
        );

        // 2. Establecer la seguridad en el contexto
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3. Generar el Token (asumiendo rol ADMIN por defecto para el administrador)
        String token = jwtTokenProvider.generarToken(authentication.getName(), "ADMIN");

        // 4. Devolver el token al frontend
        return ResponseEntity.ok(new JwtAuthResponseDTO(token, "Bearer"));
    }
}
