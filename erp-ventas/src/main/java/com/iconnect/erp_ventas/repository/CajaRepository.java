package com.iconnect.erp_ventas.repository;

import com.iconnect.erp_ventas.domain.core.Moneda;
import com.iconnect.erp_ventas.domain.finanzas.Caja;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CajaRepository extends JpaRepository<Caja, UUID> {

    /*
     Buscar las cajas a partir de las monedas, sirve para la dropdown en cascada
     */
    List<Caja> findByMoneda(Moneda moneda);
}
