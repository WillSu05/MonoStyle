package com.example.MonoStyle.DTOS;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadoDisponibilidadEmpleadoDTO {
    private Long idDisponibilidadEmpleado;

    @NotBlank(message = "La descripción del estado es obligatoria")
    private String descripcion;
}