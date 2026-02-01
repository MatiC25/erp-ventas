package com.iconnect.erp_ventas.DTOS;

import com.iconnect.erp_ventas.domain.core.Moneda;
import com.iconnect.erp_ventas.domain.finanzas.Caja;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class PagoDTO {
    private Moneda moneda;
    private BigDecimal monto;
    private Caja caja; // Ahora guardamos la Caja Real

    public String getMonedaNombre() { return moneda.name(); }
}