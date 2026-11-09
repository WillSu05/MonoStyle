package com.example.MonoStyle.Entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "estado_orden")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadoOrden {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEstadoOrden;

    @Column(nullable = false, length = 50)
    private String descripcion;
}
