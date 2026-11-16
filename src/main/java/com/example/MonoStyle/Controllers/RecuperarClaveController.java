package com.example.MonoStyle.Controllers;

import com.example.MonoStyle.Repository.ClienteRepository;
import com.example.MonoStyle.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class RecuperarClaveController {

    @Autowired private ClienteRepository clienteRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private EmailService emailService;

    @PostMapping("/olvide-clave")
    public ResponseEntity<Void> olvideClave(@RequestBody Map<String, String> body) {
        String email = body.getOrDefault("email", "").trim();

        // Siempre responde 200 para no revelar si el correo existe
        clienteRepository.findByEmail(email).ifPresent(c -> {
            String codigo = String.format("%06d", new SecureRandom().nextInt(1_000_000));
            c.setResetCodigo(passwordEncoder.encode(codigo));
            c.setResetExpira(LocalDateTime.now().plusMinutes(15));
            clienteRepository.save(c);
            try {
                emailService.enviar(email, "Código de recuperación MonoStyle",
                        "<p>Tu código es: <b style='font-size:22px'>" + codigo + "</b></p>"
                                + "<p>Vence en 15 minutos. Si no lo pediste, ignora este correo.</p>");
            } catch (Exception e) {
                System.out.println("Error enviando correo: " + e.getMessage());
            }
        });
        return ResponseEntity.ok().build();
    }

    @PostMapping("/restablecer-clave")
    public ResponseEntity<Void> restablecer(@RequestBody Map<String, String> body) {
        String email = body.getOrDefault("email", "").trim();
        String codigo = body.getOrDefault("codigo", "").trim();
        String nueva = body.getOrDefault("claveNueva", "");

        if (nueva.length() < 6) return ResponseEntity.badRequest().build();

        return clienteRepository.findByEmail(email).map(c -> {
            boolean valido = c.getResetCodigo() != null
                    && c.getResetExpira() != null
                    && c.getResetExpira().isAfter(LocalDateTime.now())
                    && passwordEncoder.matches(codigo, c.getResetCodigo());

            if (!valido) return ResponseEntity.status(HttpStatus.BAD_REQUEST).<Void>build();

            c.setCredenciales(passwordEncoder.encode(nueva));
            c.setResetCodigo(null);
            c.setResetExpira(null);
            clienteRepository.save(c);
            return ResponseEntity.ok().<Void>build();
        }).orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }
}