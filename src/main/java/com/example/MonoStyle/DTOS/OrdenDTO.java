package com.example.MonoStyle.DTOS;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdenDTO {
    private Long idOrden;

    @NotBlank(message = "La descripción de la orden es obligatoria")
    private String descripcion;

    @NotNull(message = "El ID del empleado es obligatorio")
    private Long idEmpleado;

    @NotNull(message = "El ID del cliente es obligatorio")
    private Long idCliente;

    @NotNull(message = "El ID del estado de la orden es obligatorio")
    private Long idEstadoOrden;
}
