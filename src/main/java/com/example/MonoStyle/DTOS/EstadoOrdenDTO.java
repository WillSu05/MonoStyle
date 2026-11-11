package com.example.MonoStyle.DTOS;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadoOrdenDTO {
    @JsonIgnore
    private Long idEstadoOrden;

    @NotBlank(message = "La descripción del estado es obligatoria")
    private String descripcion;
}
