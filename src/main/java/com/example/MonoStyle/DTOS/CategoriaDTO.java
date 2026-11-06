package com.example.MonoStyle.DTOS;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoriaDTO {
    private Long idCategoria;

    @NotBlank(message = "La descripción de la categoría es obligatoria")
    private String descripcion;
}