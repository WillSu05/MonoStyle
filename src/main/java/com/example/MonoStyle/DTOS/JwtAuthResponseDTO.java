package com.example.MonoStyle.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthResponseDTO {
    private String token;
    private String tipoToken;
    private Long idCliente;
    private String nombre;
    private String email;
    private String telefono;

    public JwtAuthResponseDTO(String token, String tipoToken) {
        this.token = token;
        this.tipoToken = tipoToken;
    }
}
