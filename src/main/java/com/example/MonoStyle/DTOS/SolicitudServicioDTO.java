package com.example.MonoStyle.DTOS;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SolicitudServicioDTO {
    @NotNull(message = "El ID del cliente es obligatorio")
    private Long idCliente;

    @NotBlank(message = "La descripción del problema es obligatoria")
    private String descripcionProblema;
}
