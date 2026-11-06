package com.example.MonoStyle.DTOS;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpleadoDTO {
    private Long idEmpleado;

    @NotBlank(message = "El nombre del empleado es obligatorio")
    private String nombre;

    private String especialidad;

    private Long idDisponibilidadEmpleado; // Puede ser null si aún no se le asigna un estado

    @NotNull(message = "El ID del rol es obligatorio")
    private Long idRol;
}
