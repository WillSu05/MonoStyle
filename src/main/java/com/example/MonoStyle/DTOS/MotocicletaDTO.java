package com.example.MonoStyle.DTOS;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class MotocicletaDTO {
    private Long idMotocicleta;

    @NotNull(message = "El modelo es obligatorio")
    private Integer modelo;

    @NotNull(message = "El año es obligatorio")
    private Integer año;

    @NotBlank(message = "La placa es obligatoria")
    private String placa;

    @NotBlank(message = "El color es obligatorio")
    private String color;

    @NotNull(message = "El ID de la marca es obligatorio")
    private Long idMarca;

    @NotNull(message = "El ID del cliente es obligatorio")
    private Long idCliente;
}