package com.example.MonoStyle.DTOS;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CitaDTO {
    @NotBlank(message = "El vehículo es obligatorio")
    private String vehiculo;

    @NotBlank(message = "El servicio es obligatorio")
    private String servicio;

    @NotBlank(message = "La fecha es obligatoria")
    private String fecha;

    @NotBlank(message = "La hora es obligatoria")
    private String hora;

    private String observaciones;

    private String estado;
}