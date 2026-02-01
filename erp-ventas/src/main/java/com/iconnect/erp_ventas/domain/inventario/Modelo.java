package com.iconnect.erp_ventas.domain.inventario;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Modelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private CategoriaDispositivo categoriaDispositivo;
}