package com.iconnect.erp_ventas.domain.ventas;

import com.iconnect.erp_ventas.domain.finanzas.Caja;
import com.iconnect.erp_ventas.domain.core.TipoOperacion;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Table
@Entity
@Data
@Getter
@Setter
public class VentaCobro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_venta")
    @NotNull
    private Venta ventaAsociada;

    @OneToOne
    @JoinColumn(name="id_caja")
    private Caja caja;

    private BigDecimal montoTotal;

    private BigDecimal montoSubtotal;

    private BigDecimal cotizacion;

    private BigDecimal cotizacionAplicada;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TipoOperacion tipoOperacion;

}
