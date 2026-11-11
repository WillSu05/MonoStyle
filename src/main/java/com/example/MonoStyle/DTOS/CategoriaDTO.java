package com.example.MonoStyle.DTOS;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoriaDTO {
    @JsonIgnore
    private Long idCategoria;

    @NotBlank(message = "La descripción de la categoría es obligatoria")
    private String descripcion;
}