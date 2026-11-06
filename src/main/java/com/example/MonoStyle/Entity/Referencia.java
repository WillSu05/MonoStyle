package com.example.MonoStyle.Entity;
import jakarta.persistence.*;
import lombok.*;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCategoria", nullable = false)
    private Categoria categoria;
}
