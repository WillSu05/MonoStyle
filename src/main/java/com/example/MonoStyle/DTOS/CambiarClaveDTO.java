package com.example.MonoStyle.DTOS;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CambiarClaveDTO {
    @NotBlank(message = "La contraseña actual es obligatoria")
    private String claveActual;

    @NotBlank(message = "La nueva contraseña es obligatoria")
    @Size(min = 6, message = "La nueva contraseña debe tener mínimo 6 caracteres")
    private String claveNueva;
}