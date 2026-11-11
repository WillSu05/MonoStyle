package com.example.MonoStyle.DTOS;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProveedorDTO {
    @JsonIgnore
    private Integer idProveedor;

    @NotBlank(message = "El nombre de la empresa proveedora es obligatorio")
    private String nombreEmpresa;

    private String categoriaRepuestos;
    private String tiempoPromedioEntrega;

    @NotNull(message = "El ID de la referencia es obligatorio")
    private Long idReferencia;
}
