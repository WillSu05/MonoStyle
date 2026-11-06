package com.example.MonoStyle.Entity;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "marcas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Marca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMarca;

    @Column(nullable = false, length = 50)
    private String nombre;
}
