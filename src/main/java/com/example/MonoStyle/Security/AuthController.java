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
@CrossOrigin(origins = {"http://localhost:5173", "http://192.168.1.39:5173"})
public class AuthController {

    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private JwtTokenProvider jwtTokenProvider;
    @Autowired private ClienteRepository clienteRepository;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponseDTO> authenticateUser(@RequestBody LoginDTO loginDTO) {

        String usuario = loginDTO.getNombre();
        if (usuario != null && usuario.contains("@")) {
            usuario = clienteRepository.findByEmail(usuario)
                    .map(Cliente::getNombre)
                    .orElse(usuario);
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuario, loginDTO.getCredenciales())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String rolDetectado = authentication.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");

        String token = jwtTokenProvider.generarToken(authentication.getName(), rolDetectado);

        return ResponseEntity.ok(new JwtAuthResponseDTO(token, "Bearer"));
    }
}
