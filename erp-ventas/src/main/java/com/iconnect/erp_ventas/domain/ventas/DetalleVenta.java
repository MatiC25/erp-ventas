package com.iconnect.erp_ventas.domain.ventas;

import com.iconnect.erp_ventas.domain.inventario.Producto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Table
@Entity
@Data
@Getter
@Setter
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="id_venta")
    private Venta venta;

    @ManyToOne
    @JoinColumn(name="id_producto")
    private Producto producto;

    private Long serialNumber;

    private int cantidad;

    public BigDecimal getSubtotal() {
        if (precioUnitario == null) {
            return BigDecimal.ZERO;
        }
        return precioUnitario.multiply(BigDecimal.valueOf(cantidad));
    }

    private BigDecimal precioUnitario;

    private BigDecimal costo;

    private BigDecimal profit;

    private String comentarios;
}
