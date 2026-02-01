package com.iconnect.erp_ventas.domain.finanzas;

import com.iconnect.erp_ventas.domain.core.Moneda;
import com.iconnect.erp_ventas.domain.core.TipoCaja;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "cajas")
@Entity
@Data
@Getter
@Setter
public class Caja {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nombre;

    @Enumerated(EnumType.STRING)
    private Moneda moneda;

    @Column(precision = 19, scale = 2)
    private BigDecimal totalGuardado;

    @Enumerated(EnumType.STRING)
    private TipoCaja tipoCaja;

    public String getNombreConSaldo() {
        return String.format("%s (%s $%.2f)", nombre, moneda, totalGuardado);
    }
}
