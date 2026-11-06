package com.example.MonoStyle.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "administrador")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Administrador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAdministrador;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false)
    private int nivelAcceso;

    @Column(nullable = false, length = 255)
    private String credenciales;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rol", nullable = false)
    private Rol rol;
}
