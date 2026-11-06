package com.example.MonoStyle.DTOS;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MarcaDTO {
    private Long idMarca;

    @NotBlank(message = "El nombre de la marca es obligatorio")
    private String nombre;
}