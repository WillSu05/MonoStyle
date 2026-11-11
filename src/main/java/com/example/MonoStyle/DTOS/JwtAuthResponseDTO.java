package com.example.MonoStyle.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtAuthResponseDTO {
    private String token;
    private String tipoToken; // Siempre será "Bearer"
}
