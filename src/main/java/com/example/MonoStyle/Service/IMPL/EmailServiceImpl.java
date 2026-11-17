package com.example.MonoStyle.Service.IMPL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Map;

@Service
public class EmailServiceImpl {

    @Value("${brevo.api-key}") private String apiKey;
    @Value("${brevo.sender-email}") private String sender;

    public void enviar(String para, String asunto, String html) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("api-key", apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = Map.of(
                "sender", Map.of("name", "MonoStyle", "email", sender),
                "to", List.of(Map.of("email", para)),
                "subject", asunto,
                "htmlContent", html
        );

        new RestTemplate().postForEntity(
                "https://api.brevo.com/v3/smtp/email",
                new HttpEntity<>(body, headers),
                String.class);
    }
}