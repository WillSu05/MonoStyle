package com.example.MonoStyle.DTOS;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteDTO {
    @JsonIgnore
    private Long idCliente;

    @NotBlank(message = "El nombre del cliente es obligatorio")
    private String nombre;

    private String telefono;

    @NotBlank(message = "Las credenciales (contraseña) son obligatorias")
    private String credenciales;

    @NotNull(message = "El ID del rol es obligatorio")
    private Long idRol;
}
