package com.example.MonoStyle.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "motocicletas")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Motocicleta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMotocicleta;

    @Column(nullable = false, length = 10, unique = true)
    private String placa;

    @Column(nullable = false, length = 30)
    private String color;

    @Column(nullable = false, length = 50, unique = true)
    private String chasis;

    @Column(nullable = false)
    private Integer modelo;

    @Column(nullable = false)
    private Integer cilindraje;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_marca", nullable = false)
    private Marca marca;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;
}