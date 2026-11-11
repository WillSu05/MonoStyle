package com.example.MonoStyle.DTOS;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegistroClienteDTO {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El teléfono es obligatorio")
    private String telefono;

    @NotBlank(message = "La contraseña (credenciales) es obligatoria")
    private String credenciales;
}
