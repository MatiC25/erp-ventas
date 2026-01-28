package com.iconnect.erp_ventas.domain.usuarios;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password; // Aquí guardaremos el hash

    private String nombreCompleto;

    private String rol; // "ADMIN", "VENDEDOR"

    private boolean activo = true;
}