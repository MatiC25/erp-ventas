package com.iconnect.erp_ventas.domain.usuarios;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@Table(name = "clientes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String nombre;

    private String apellido;

    private String telefono;

    private String email;

    // ¿Cuánto nos debe?
    @Column(precision = 19, scale = 2) // 19 dígitos total, 2 decimales
    private BigDecimal saldoCuentaCorriente = BigDecimal.ZERO;
}