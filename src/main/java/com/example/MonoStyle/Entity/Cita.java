package com.example.MonoStyle.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cita")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cita")
    private Long idCita;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @Column(nullable = false, length = 150)
    private String vehiculo;

    @Column(nullable = false, length = 100)
    private String servicio;

    @Column(nullable = false, length = 20)
    private String fecha;

    @Column(nullable = false, length = 10)
    private String hora;

    @Column(length = 500)
    private String observaciones;

    @Column(nullable = false, length = 20)
    private String estado;
}
