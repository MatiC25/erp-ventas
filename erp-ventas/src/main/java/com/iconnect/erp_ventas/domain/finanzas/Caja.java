package com.iconnect.erp_ventas.domain.finanzas;

import com.iconnect.erp_ventas.domain.core.Moneda;
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

    private Moneda moneda;

    private BigDecimal totalGuardado;
}
