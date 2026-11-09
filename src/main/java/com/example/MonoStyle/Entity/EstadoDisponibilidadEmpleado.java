package com.example.MonoStyle.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "estado_disponibilidad_empleado")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadoDisponibilidadEmpleado {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDisponibilidadEmpleado;

    @Column(nullable = false, length = 50)
    private String descripcion;
}
