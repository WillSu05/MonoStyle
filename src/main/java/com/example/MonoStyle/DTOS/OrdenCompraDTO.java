package com.example.MonoStyle.DTOS;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdenCompraDTO {
    @JsonIgnore
    private Long idOrdenCompra;

    @NotBlank(message = "La descripción de la orden de compra es obligatoria")
    private String descripcion;

    @NotNull(message = "El ID del proveedor es obligatorio")
    private Integer idProveedor;
}