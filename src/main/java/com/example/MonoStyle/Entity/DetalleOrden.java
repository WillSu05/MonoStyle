package com.example.MonoStyle.Entity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "detalle_orden")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DetalleOrden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetalleOrden;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_orden", nullable = false)
    private Orden orden;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_referencia", nullable = false)
    private Referencia referencia;

    @Column(nullable = false)
    private Double cantidad; // Ajustado a Double

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio; // Mapeado como Numeric/BigDecimal
}