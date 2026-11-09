package com.example.MonoStyle.Entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "orden")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Orden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOrden;

    @Column(nullable = false, length = 255)
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empleado", nullable = false)
    private Empleado empleado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estado_orden", nullable = false)
    private EstadoOrden estadoOrden;

    // Relación de vuelta opcional para facilitar consultas bidireccionales
    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleOrden> detalles;
}
