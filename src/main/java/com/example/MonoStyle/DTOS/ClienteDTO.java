package com.example.MonoStyle.DTOS;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteDTO {
    private Long idCliente;

    @NotBlank(message = "El nombre del cliente es obligatorio")
    private String nombre;

    private String telefono;

    @NotNull(message = "El ID del rol es obligatorio")
    private Long idRol;
}
