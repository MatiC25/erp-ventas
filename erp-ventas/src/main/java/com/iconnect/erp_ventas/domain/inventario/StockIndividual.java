package com.iconnect.erp_ventas.domain.inventario;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name="usados")
@Entity
@Data
public class StockIndividual {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long serialNumber;

    private BigDecimal valor;

    private Boolean sigueDisponible;

    @ManyToOne
    @JoinColumn(name="id_producto")
    private Producto producto;

    private LocalDateTime fechaIngreso;
    private LocalDateTime fechaSalida;
}
