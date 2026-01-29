package com.iconnect.erp_ventas.repository;

import com.iconnect.erp_ventas.domain.inventario.Producto;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Buscar por código de barras (para la pistola lectora)
    Optional<Producto> findByUPC(String codigoBarras);

}