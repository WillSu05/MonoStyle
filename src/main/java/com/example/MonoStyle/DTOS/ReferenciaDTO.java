package com.example.MonoStyle.DTOS;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReferenciaDTO {
    private Long id;

    @NotBlank(message = "La descripción de la referencia es obligatoria")
    private String descripcion;

    @NotNull(message = "El ID de la categoría es obligatorio")
    private Long idCategoria;
}
