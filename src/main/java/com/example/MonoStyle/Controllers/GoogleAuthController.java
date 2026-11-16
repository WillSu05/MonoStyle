package com.example.MonoStyle.Controllers;

import com.example.MonoStyle.DTOS.JwtAuthResponseDTO;
import com.example.MonoStyle.Entity.Cliente;
import com.example.MonoStyle.Entity.Rol;
import com.example.MonoStyle.Repository.ClienteRepository;
import com.example.MonoStyle.Repository.RolRepository;
import com.example.MonoStyle.Security.JwtTokenProvider;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class GoogleAuthController {

    @Value("${google.client-id}") private String googleClientId;
    @Autowired private ClienteRepository clienteRepository;
    @Autowired private RolRepository rolRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/google")
    public ResponseEntity<JwtAuthResponseDTO> google(@RequestBody Map<String, String> body) throws Exception {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                new NetHttpTransport(), GsonFactory.getDefaultInstance())
                .setAudience(Collections.singletonList(googleClientId))
                .build();

        GoogleIdToken idToken = verifier.verify(body.get("idToken"));
        if (idToken == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        String email = idToken.getPayload().getEmail();
        String nombre = (String) idToken.getPayload().get("name");

        Cliente cliente = clienteRepository.findByEmail(email).orElseGet(() -> {
            Rol rol = rolRepository.findAll().stream()
                    .filter(r -> r.getDescripcion().equalsIgnoreCase("CLIENTE"))
                    .findFirst().orElseThrow();
            return clienteRepository.save(Cliente.builder()
                    .nombre(nombre != null ? nombre : email)
                    .email(email)
                    .credenciales(passwordEncoder.encode(UUID.randomUUID().toString()))
                    .rol(rol)
                    .build());
        });

        String token = jwtTokenProvider.generarToken(cliente.getNombre(), "CLIENTE");
        JwtAuthResponseDTO resp = new JwtAuthResponseDTO(token, "Bearer");
        resp.setIdCliente(cliente.getIdCliente());
        resp.setNombre(cliente.getNombre());
        resp.setEmail(cliente.getEmail());
        resp.setTelefono(cliente.getTelefono());
        return ResponseEntity.ok(resp);
    }
}