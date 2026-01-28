package com.iconnect.erp_ventas.repository;

import com.iconnect.erp_ventas.domain.ventas.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Long> {

    // Para reportes: Todas las ventas entre dos fechas
    List<Venta> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);

    // Para ver el historial de un cliente específico
    List<Venta> findByClienteId(Long clienteId);
}