package com.example.MonoStyle.DTOS;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AsignacionRepuestoDTO {
    @NotNull(message = "El ID de la orden es obligatorio")
    private Long idOrden;

    @NotNull(message = "El ID de la referencia (repuesto) es obligatorio")
    private Long idReferencia;

    @NotNull(message = "La cantidad es obligatoria")
    private Integer cantidad;
}
