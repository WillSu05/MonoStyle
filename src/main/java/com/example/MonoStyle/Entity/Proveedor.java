package com.example.MonoStyle.Entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "proveedor")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Proveedor {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProveedor;

    @Column(nullable = false, length = 100)
    private String nombreEmpresa;

    @Column(length = 50)
    private String categoriaRepuestos;

    @Column(length = 50)
    private String tiempoPromedioEntrega;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_referencia")
    private Referencia referencia;
}