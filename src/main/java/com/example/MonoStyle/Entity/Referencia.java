package com.example.MonoStyle.Entity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "referencia")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Referencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String descripcion;

    @Column(nullable = false)
    private Double stock; // Ajustado a Double según tu diagrama

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;
}
