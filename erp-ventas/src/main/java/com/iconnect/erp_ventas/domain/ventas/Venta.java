package com.iconnect.erp_ventas.domain.ventas;

import com.iconnect.erp_ventas.domain.core.CanalVenta;
import com.iconnect.erp_ventas.domain.core.TipoVenta;
import com.iconnect.erp_ventas.domain.usuarios.Cliente;
import com.iconnect.erp_ventas.domain.usuarios.Usuario;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "ventas")
@Entity
@Data
@Getter
@Setter
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="id_cliente")
    private Cliente cliente;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleVenta> detalles = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="id_vendedor")
    private Usuario vendedor;

    private TipoVenta tipoVenta;

    private LocalDateTime fecha;

    private LocalDateTime fechaUltimaModificacion;

    @ManyToOne
    @JoinColumn(name="id_vendedor_mod")
    private Usuario vendedorModificacion;

    @Enumerated(EnumType.STRING)
    private CanalVenta canalVenta;

    private BigDecimal profit;

    private BigDecimal total;

    public void calcularTotal() {
        this.total = BigDecimal.ZERO;

        // Recorremos la lista de hijos sumando sus subtotales
        for (DetalleVenta detalle : detalles) {
            this.total = this.total.add(detalle.getSubtotal());
        }
    }



}
