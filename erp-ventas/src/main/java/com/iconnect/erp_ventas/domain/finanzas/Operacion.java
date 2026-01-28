package com.iconnect.erp_ventas.domain.finanzas;

import com.iconnect.erp_ventas.domain.core.Moneda;
import com.iconnect.erp_ventas.domain.core.TipoOperacion;
import com.iconnect.erp_ventas.domain.usuarios.Usuario;
import com.iconnect.erp_ventas.domain.ventas.VentaCobro;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "operaciones")
@Data
@Getter
@Setter
public class Operacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    @NotNull
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "id_venta_cobro")
    private VentaCobro ventaCobro;

    @ManyToOne
    @JoinColumn(name = "id_caja")
    private Caja cajaAsociada;

    @ManyToOne
    @JoinColumn(name = "id_vendedor")
    private Usuario vendedor;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TipoOperacion tipoOperacion;

    @NotNull
    private BigDecimal monto;

    private LocalDateTime fecha;

    private String descripcion;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Moneda moneda;


}
