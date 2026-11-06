package com.example.MonoStyle.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "motocicleta")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Motocicleta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMotocicleta;

    @Column(nullable = false)
    private Integer modelo;

    @Column(nullable = false, name = "anio") // Cambiado a 'anio' para evitar errores de codificación en BD
    private Integer año;

    @Column(nullable = false, length = 20)
    private String placa;

    @Column(nullable = false, length = 30)
    private String color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_marca", nullable = false)
    private Marca marca;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;
}