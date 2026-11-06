package com.example.MonoStyle.DTOS;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdministradorDTO {
    private Long idAdministrador;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotNull(message = "El nivel de acceso es obligatorio")
    private Integer nivelAcceso;

    @NotBlank(message = "Las credenciales (contraseña) son obligatorias")
    private String credenciales;

    @NotNull(message = "El ID del rol es obligatorio")
    private Long idRol;
}