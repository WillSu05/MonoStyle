package com.example.MonoStyle.Entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "estado_orden")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadoOrden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEstadoOrden;

    @Column(nullable = false, length = 50)
    private String descripcion;
}
