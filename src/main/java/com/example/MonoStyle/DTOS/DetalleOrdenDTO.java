package com.example.MonoStyle.DTOS;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.math.BigDecimal;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class DetalleOrdenDTO {
    private Long idDetalleOrden;

    @NotNull(message = "El ID de la orden es obligatorio")
    private Long idOrden;

    @NotNull(message = "El ID del repuesto (referencia) es obligatorio")
    private Long idReferencia;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Double cantidad;

    private BigDecimal precioUnitario; // Se calculará en el backend con el precio vigente
}
