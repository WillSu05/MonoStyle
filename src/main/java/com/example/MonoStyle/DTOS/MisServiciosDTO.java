package com.example.MonoStyle.DTOS;

import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MisServiciosDTO {
    private Long idOrden;
    private String tituloServicio;
    private String fecha;
    private String vehiculo;
    private String descripcion;
    private BigDecimal costo;
    private String estado;
}